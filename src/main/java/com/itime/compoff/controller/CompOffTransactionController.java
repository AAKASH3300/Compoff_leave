package com.itime.compoff.controller;


import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.service.CompOffTransactionService;
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
@RequestMapping("/compoff")
public class CompOffTransactionController {

    private static final Logger log = LoggerFactory.getLogger(CompOffTransactionController.class);

    @Autowired
    CompOffTransactionService compOffTransactionService;

    @PostMapping(value = "/apply", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus saveCompOffTransaction(@RequestBody CompOffApplyRequest compOffApplyRequest) {

        log.info("CompOff request created....");
        return compOffTransactionService.createCompOffTransaction(compOffApplyRequest);

    }
}
