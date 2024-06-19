package com.itime.compoff.job;

import com.itime.compoff.enumeration.EnumTrueFalse;
import com.itime.compoff.primary.entity.CompOffTransaction;
import com.itime.compoff.primary.repository.CompOffTransactionRepo;
import com.itime.compoff.secondary.repository.EmployeeDetailRepo;
import com.itime.compoff.utils.AppConstants;
import com.itime.compoff.utils.DateTimeUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DailyJob {
    private static final Logger log = LoggerFactory.getLogger(DailyJob.class);

    @Autowired
    CompOffTransactionRepo compOffTransactionRepo;

    @Value("${spring.scheduling.compoff.expiring.job.active}")
    String cronStatus;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "${spring.scheduling.compoff.expiring.job}")
    public void dailyRun() {

        if (cronStatus.equalsIgnoreCase(EnumTrueFalse.TRUE.getStatus())) {
            this.checkForExpiringCompOffs();
        }
    }

    //Comp-Off Expiry Cron Run daily at midnight
    public void checkForExpiringCompOffs() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveDaysLater = now.plusDays(5);

        List<CompOffTransaction> expiringCompOffs = compOffTransactionRepo.findAllByExpiryDateBetween(
                Timestamp.valueOf(fiveDaysLater.toLocalDate().atStartOfDay()),
                Timestamp.valueOf(fiveDaysLater.toLocalDate().plusDays(1).atStartOfDay()));

        expiringCompOffs.forEach(this::sendExpiryNotification);
    }

    private void sendExpiryNotification(CompOffTransaction compOff) {

        String subject = "CompOff Expiry Notification";
        String text = "Dear Employee,\n\nThis is a notification that your compensatory off requested on " + compOff.getRequestedDt()
                + " is expiring soon.\n\nWork hours: " + compOff.getWorkHours() + "\nReason: " + compOff.getReason()
                + "\n\nPlease take necessary action.";

        String htmlContent = String.format(AppConstants.COMPOFF_EXPIRY_NOTIFICATION_EMAIL_TEMPLATE,
                DateTimeUtils.formatTimestampToDateString(compOff.getRequestedDt()),
                compOff.getWorkHours(),
                compOff.getReason()
        );

        try {
            sendEmail(AppConstants.EMAIL, subject, text, htmlContent);
        } catch (MessagingException e) {
            log.info(e.toString());
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
