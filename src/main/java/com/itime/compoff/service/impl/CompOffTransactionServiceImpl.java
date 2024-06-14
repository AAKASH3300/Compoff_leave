package com.itime.compoff.service.impl;

import com.itime.compoff.enumeration.EnumCompOffPeriod;
import com.itime.compoff.enumeration.EnumCompOffTransactionStatus;
import com.itime.compoff.enumeration.EnumCompOffUsageStatus;
import com.itime.compoff.enumeration.EnumStatus;
import com.itime.compoff.exception.CommonException;
import com.itime.compoff.exception.DataNotFoundException;
import com.itime.compoff.exception.ErrorMessages;
import com.itime.compoff.mapper.CompOffMapper;
import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.primary.entity.CompOffTransaction;
import com.itime.compoff.primary.entity.LeaveSummary;
import com.itime.compoff.primary.entity.LeaveType;
import com.itime.compoff.primary.repository.CompOffTransactionRepo;
import com.itime.compoff.primary.repository.LeaveApprovalRepo;
import com.itime.compoff.primary.repository.LeaveSummaryRepo;
import com.itime.compoff.primary.repository.LeaveTypeRepo;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import com.itime.compoff.service.CompOffTransactionService;
import com.itime.compoff.utils.AppConstants;
import com.itime.compoff.utils.DateTimeUtils;
import com.itime.compoff.validation.BusinessValidationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

@Component
public class CompOffTransactionServiceImpl implements CompOffTransactionService {

    @Autowired
    CompOffTransactionRepo compOffTransactionRepo;

    @Autowired
    CompOffMapper compOffMapper;

    @Autowired
    BusinessValidationService businessValidationService;

    @Autowired
    LeaveApprovalRepo leaveApprovalRepo;
    @Autowired
    LeaveSummaryRepo leaveSummaryRepo;
    @Autowired
    LeaveTypeRepo leaveTypeRepo;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Transactional(rollbackOn = CommonException.class)
    public HttpStatus createCompOffTransaction(CompOffApplyRequest compOffApplyRequest) throws CommonException {

        EmployeeDetail employeeDetails = businessValidationService.getEmployee(compOffApplyRequest.getEmployeeId().toString());

        Timestamp requestDate = DateTimeUtils.convertFromJsonSqlDateOnly(compOffApplyRequest.getRequestedDate());

        businessValidationService.duplicateCompOffValidation(employeeDetails, compOffApplyRequest);

        CompOffTransaction compOffTransaction = compOffMapper.mapApplyRequestToEntity(employeeDetails, compOffApplyRequest);

        compOffTransactionRepo.save(compOffTransaction);

        return HttpStatus.OK;
    }

    @Override
    public void updateCompOffApproval(String transactionId, String status, String compOffFor) throws CommonException {

//        if (EnumCompOffPeriod.valuesOf(compOffFor) == null) {
//            throw new CommonException(ErrorMessages.INVALID_COMPOFF_REQUEST_FOR, HttpStatus.BAD_REQUEST.value());
//        }
//
//        CompOffTransaction compOffTransaction = compOffTransactionRepo.findById(Long.valueOf(transactionId))
//                .orElseThrow(() -> new DataNotFoundException(ErrorMessages.RECORD_NOT_FOUND,
//                        HttpStatus.BAD_REQUEST.value()));
//
//        if (compOffTransaction.getTransactionStatus().equals(EnumCompOffTransactionStatus.PENDING)) {
//            compOffTransaction.setTransactionStatus(EnumCompOffTransactionStatus.compOffStatusValue(status.toUpperCase()));
//            compOffTransaction.setCompOffUsageStatus(compOffTransaction.getTransactionStatus().equals(EnumCompOffTransactionStatus.APPROVED) ?
//                    EnumCompOffUsageStatus.AVAILABLE : EnumCompOffUsageStatus.INVALID);
//            compOffTransaction.setApproverRemarks("comment");
//            compOffTransaction.setLastUpdatedDt(DateTimeUtils.getCurrentTimeStamp());
//            compOffTransaction.setLastUpdatedBy(compOffTransaction.getApproverId().getFirstName());
//            if (compOffFor != null) {
//                businessValidationService.validateWorkHours(null, null, EnumCompOffPeriod.valuesOf(compOffFor),
//                        commonService.findTimeDifference(compOffTransaction.getPunchInTime(), compOffTransaction.getPunchOutTime()));
//                compOffTransaction.setApprovedFor(EnumCompOffPeriod.valuesOf(compOffFor.toUpperCase()));
//            }
//            compOffTransactionRepo.save(compOffTransaction);
//            if (compOffTransaction.getTransactionStatus().equals(EnumCompOffTransactionStatus.APPROVED)) {
//                this.updateCompOffLeaveSummary(compOffTransaction);
//
//            } else {
//                throw AppConstants.COMP_OFF_STATUS_UPDATED;
//            }
//        }

    }


    public HttpStatus cancelCompOffRequest(long id, String comment) throws CommonException {

        CompOffTransaction compOffTransaction = compOffTransactionRepo.findTop1ByIdAndStatus(id,
                EnumStatus.ACTIVE).orElseThrow(() -> new CommonException(ErrorMessages.INVALID_REQUEST, HttpStatus.BAD_REQUEST.value()));

        if (!compOffTransaction.getTransactionStatus().equals(EnumCompOffTransactionStatus.APPROVED)) {
            throw new CommonException(String.format(ErrorMessages.ALREADY_COMPOFF_UPDATED, compOffTransaction.getTransactionStatus().getCompOffStatus().toLowerCase()), HttpStatus.BAD_REQUEST.value());
        }
        compOffTransaction.setCompOffUsageStatus(EnumCompOffUsageStatus.INVALID);
        compOffTransaction.setTransactionStatus(EnumCompOffTransactionStatus.CANCELLED);
//        compOffTransaction.setLastUpdatedBy(compOffTransaction.getEmployeeId());
        compOffTransaction.setLastUpdatedDt(DateTimeUtils.getCurrentTimeStamp());
        compOffTransaction.setCancellationReason(comment);

        compOffTransactionRepo.save(compOffTransaction);
        this.updateCompOffSummaryOnCancel(compOffTransaction);

        return HttpStatus.OK;
    }

    private void updateCompOffSummaryOnCancel(CompOffTransaction compOffTransaction) {

        LeaveSummary leaveSummary = this.findCompOffLeaveSummary(compOffTransaction.getEmployeeId());
        if (leaveSummary != null) {
            double available = leaveSummary.getLeavesAvailable();
            available = (available - compOffTransaction.getApprovedFor().getDays());
            leaveSummary.setLeavesAvailable(available < 0 ? 0.0 : available);
            leaveSummaryRepo.save(leaveSummary);
        }
    }

    private LeaveSummary findCompOffLeaveSummary(long employeeDetail) {
        return leaveSummaryRepo.findTop1ByEmployeeIdAndLeaveTypeIdAndPeriodStartDtLessThanEqualAndPeriodEndDtGreaterThanEqual(
                employeeDetail, this.findCompOffLeave(employeeDetail), new Timestamp(LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()),
                new Timestamp(LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())).orElse(null);
    }

    private LeaveType findCompOffLeave(long employeeDetail) {

        return leaveTypeRepo.findByEmpIdAndStatus(employeeDetail, EnumStatus.ACTIVE);
    }


    //Comp-Off Expiry Cron Run daily at midnight
    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkForExpiringCompOffs() {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate fiveDaysLater = today.plusDays(5);
        Timestamp todayTimestamp = Timestamp.valueOf(today.atStartOfDay());
        Timestamp fiveDaysLaterTimestamp = Timestamp.valueOf(fiveDaysLater.atStartOfDay());

        List<CompOffTransaction> expiringCompOffs = compOffTransactionRepo.findExpiringCompOffs(todayTimestamp, fiveDaysLaterTimestamp);

        expiringCompOffs.forEach(this::sendExpiryNotification);
    }

    private void sendExpiryNotification(CompOffTransaction compOff) {

        String subject = "CompOff Expiry Notification";
        String text = "Dear Employee,\n\nThis is a notification that your compensatory off requested on " + compOff.getRequestedDt()
                + " is expiring soon.\n\nWork hours: " + compOff.getWorkHours() + "\nReason: " + compOff.getReason()
                + "\n\nPlease take necessary action.";

        String htmlContent = String.format(
                "<html><body>" +
                        "<h1>CompOff Expiry Notification</h1>" +
                        "<p>Dear Employee,</p>" +
                        "<p>This is a notification that your compensatory off requested on %s is expiring soon.</p>" +
                        "<p>Work hours: %s</p>" +
                        "<p>Reason: %s</p>" +
                        "<p>Please take necessary action.</p>" +
                        "</body></html>",
                compOff.getRequestedDt(),
                compOff.getWorkHours(),
                compOff.getReason()
        );

        try {
            sendEmail("0412.aakash@gmail.com", subject, text, htmlContent);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(String to, String subject, String text, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, htmlContent);
        mailSender.send(message);
    }
}
