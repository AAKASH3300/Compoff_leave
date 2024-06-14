package com.itime.compoff.controller;

import com.itime.compoff.exception.CommonException;
import com.itime.compoff.model.LeaveApplyRequest;
import com.itime.compoff.model.LeaveResponse;
import com.itime.compoff.service.LeaveTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave")
public class LeaveTransactionController {

    private static final Logger log = LoggerFactory.getLogger(LeaveTransactionController.class);

    @Autowired
    LeaveTransactionService leaveTransactionService;

    @PostMapping(value = "/apply", consumes = MediaType.APPLICATION_JSON_VALUE)
    public LeaveResponse applyLeaveTransaction(@RequestHeader(name = "createdByEmpId", required = false) String createdByEmpId,
                                               @RequestPart(name = "applyLeaverequest") LeaveApplyRequest applyLeaverequest) throws CommonException {

        log.info("Sending leave apply request to service layer");
        return  leaveTransactionService.saveLeave(applyLeaverequest,createdByEmpId);

    }
}
