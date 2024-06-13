package com.itime.compoff.service.impl;

import com.itime.compoff.exception.CommonException;
import com.itime.compoff.mapper.CompOffMapper;
import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.primary.entity.CompOffTransaction;
import com.itime.compoff.primary.repository.CompOffTransactionRepo;
import com.itime.compoff.primary.repository.LeaveApprovalRepo;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import com.itime.compoff.service.CompOffTransactionService;
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
    private JavaMailSender mailSender;

    @Override
    @Transactional(rollbackOn = CommonException.class)
    public HttpStatus createCompOffTransaction(CompOffApplyRequest compOffApplyRequest) throws CommonException {

        EmployeeDetail employeeDetails = businessValidationService.getEmployee(compOffApplyRequest.getEmployeeId().toString());

        CompOffTransaction compOffTransaction = compOffMapper.mapApplyRequestToEntity(employeeDetails, compOffApplyRequest);

        compOffTransactionRepo.save(compOffTransaction);

        return HttpStatus.OK;
    }

    @Override
    public void updateCompOffApproval(String transactionId, String status, String compOffFor) {


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
