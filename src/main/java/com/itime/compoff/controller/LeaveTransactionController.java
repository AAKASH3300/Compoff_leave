package com.itime.compoff.controller;

import com.itime.compoff.exception.CommonException;
import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.model.LeaveApplyRequest;
import com.itime.compoff.service.CompOffTransactionService;
import com.itime.compoff.service.LeaveTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leave")
public class LeaveTransactionController {

    private static final Logger log = LoggerFactory.getLogger(LeaveTransactionController.class);

    @Autowired
    LeaveTransactionService leaveTransactionService;

    @PostMapping(value = "/apply", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus applyLeaveTransaction(@RequestBody LeaveApplyRequest leaveApplyRequest) throws CommonException {

        log.info("CompOff request created....");
        return leaveTransactionService.applyLeave(leaveApplyRequest);

    }
}
