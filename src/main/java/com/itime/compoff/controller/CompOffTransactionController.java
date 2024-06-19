package com.itime.compoff.controller;


import com.itime.compoff.exception.CommonException;
import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.model.ErrorDescription;
import com.itime.compoff.service.CompOffTransactionService;
import com.itime.compoff.utils.AppConstants;
import com.itime.compoff.utils.ResourcesUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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

    @Operation(tags = "CompOff", summary = "Apply Request for CompOff")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDescription.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDescription.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDescription.class)))})

    @PostMapping(value = "/apply", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus saveCompOffTransaction(@Valid @RequestBody CompOffApplyRequest compOffApplyRequest) throws CommonException {

        log.info("Processing CompOff request....");
        return compOffTransactionService.createCompOffTransaction(compOffApplyRequest);

    }

    @Operation(tags = "CompOff", summary = "Approve/Reject CompOff request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDescription.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDescription.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDescription.class)))})

    @GetMapping(value = "/approval", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCompOffApproval(@RequestParam(name = "transactionId") String transactionId,
                                                        @RequestParam(name = "transactionStatus") String transactionStatus,
                                                        @RequestParam(name = "compOffFor", required = false) String compOffFor,
                                                        @RequestParam(name = "comment", required = false) String comment) throws CommonException {


        log.trace("CompOff transactionStatus updated....");
        compOffTransactionService.updateCompOffApproval(transactionId, transactionStatus, compOffFor, comment);

        return ResponseEntity.ok(ResourcesUtils.stringFormater(AppConstants.COMP_OFF_STATUS_UPDATED, transactionStatus.toLowerCase()));

    }


}
