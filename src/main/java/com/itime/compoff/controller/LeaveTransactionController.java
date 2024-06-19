package com.itime.compoff.controller;

import com.itime.compoff.exception.CommonException;
import com.itime.compoff.model.ErrorDescription;
import com.itime.compoff.model.LeaveApplyRequest;
import com.itime.compoff.model.LeaveResponse;
import com.itime.compoff.service.LeaveTransactionService;
import com.itime.compoff.utils.AppConstants;
import com.itime.compoff.utils.ResourcesUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave")
public class LeaveTransactionController {

    private static final Logger log = LoggerFactory.getLogger(LeaveTransactionController.class);

    @Autowired
    LeaveTransactionService leaveTransactionService;

    @Operation(tags = "Leave", summary = "Apply for leave")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDescription.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDescription.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDescription.class)))})

    @PostMapping(value = "/apply", consumes = MediaType.APPLICATION_JSON_VALUE)
    public LeaveResponse applyLeaveTransaction(@RequestHeader(name = "createdByEmpId", required = false) String createdByEmpId,
                                               @Valid @RequestBody LeaveApplyRequest leaveApplyRequest) throws CommonException {

        log.info("Processing leave apply request......");
        return leaveTransactionService.saveLeave(leaveApplyRequest, createdByEmpId);

    }

    @Operation(tags = "Leave", summary = "Approve/Reject Leave request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDescription.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDescription.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDescription.class)))})

    @GetMapping(value = "/approval", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateLeaveStatus(@RequestParam(name = "transactionId") String transactionId,
                                                    @RequestParam(name = "transactionStatus") String transactionStatus,
                                                    @RequestParam(name = "employeeId", required = false) String employeeId,
                                                    @RequestParam(name = "comment") String comment) throws CommonException {


        log.trace("Updating Leave Status....");
        leaveTransactionService.updateLeaveTransaction(transactionId, transactionStatus, employeeId, comment);

        return ResponseEntity.ok(ResourcesUtils.stringFormater(AppConstants.LEAVE_STATUS_UPDATED, transactionStatus.toLowerCase()));

    }

}
