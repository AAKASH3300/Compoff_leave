package com.itime.compoff.controller;

import com.itime.compoff.job.DailyJob;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/cron")
public class CronController {

    private static final Logger log = LoggerFactory.getLogger(CronController.class);

    @Autowired
    DailyJob dailyJob;

    @CrossOrigin
    @Operation(tags = "Cron", summary = "CompOff Expiry Notification Job")
    @GetMapping(value = "/compoff-expiring")
    public ResponseEntity<String> notifyExpiringCompOffs() {

        log.trace("CompOff Expiring Job Started....");
        dailyJob.checkForExpiringCompOffs();

        return ResponseEntity.ok("CompOff expiry notification job triggered successfully.");
    }
}
