package com.itime.compoff.controller;


import com.itime.compoff.exception.CommonException;
import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.service.CompOffTransactionService;
import com.itime.compoff.utils.AppConstants;
import com.itime.compoff.utils.ResourcesUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/compoff")
@SecurityRequirement(name = "bearerAuth")
public class CompOffTransactionController {

    private static final Logger log = LoggerFactory.getLogger(CompOffTransactionController.class);

    @Autowired
    CompOffTransactionService compOffTransactionService;

    @PostMapping(value = "/apply", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus saveCompOffTransaction(@RequestBody CompOffApplyRequest compOffApplyRequest) throws CommonException {

        log.info("CompOff request created....");
        return compOffTransactionService.createCompOffTransaction(compOffApplyRequest);

    }

    @GetMapping(value = "/approval", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCompOffApproval(@RequestParam(name = "transactionId", required = true) String transactionId,
                                                        @RequestParam(name = "status", required = true) String status,
                                                        @RequestParam(name = "compOffFor", required = false) String compOffFor) throws CommonException {

        log.trace("CompOff status updated....");
        compOffTransactionService.updateCompOffApproval(transactionId, status, compOffFor);

        return ResponseEntity.ok(ResourcesUtils.stringFormater(AppConstants.COMP_OFF_STATUS_UPDATED, status.toLowerCase()));

    }

    @CrossOrigin
    @GetMapping(value = "/notify-expiring")
    public ResponseEntity<String> notifyExpiringCompOffs() {

        log.trace("CompOff Expiring Job Started....");
        compOffTransactionService.checkForExpiringCompOffs();

        return ResponseEntity.ok("CompOff expiry notification job triggered successfully.");
    }
}
