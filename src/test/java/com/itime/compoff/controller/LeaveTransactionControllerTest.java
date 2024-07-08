package com.itime.compoff.controller;

import com.itime.compoff.exception.CommonException;
import com.itime.compoff.model.LeaveApplyRequest;
import com.itime.compoff.model.LeaveResponse;
import com.itime.compoff.service.LeaveTransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LeaveTransactionControllerTest {

    @Mock
    private LeaveTransactionService mockLeaveTransactionService;

    private LeaveTransactionController leaveTransactionControllerUnderTest;

    @Before
    public void setUp() {
        leaveTransactionControllerUnderTest = new LeaveTransactionController();
        leaveTransactionControllerUnderTest.leaveTransactionService = mockLeaveTransactionService;
    }

    @Test
    public void testApplyLeaveTransaction() throws Exception {
        final LeaveApplyRequest leaveApplyRequest = new LeaveApplyRequest();
        leaveApplyRequest.setEmployeeId("employeeId");
        leaveApplyRequest.setLeaveTypeId("leaveTypeId");
        leaveApplyRequest.setStartDt("startDt");
        leaveApplyRequest.setEndDt("endDt");
        leaveApplyRequest.setNoOfDays("noOfDays");

        final LeaveResponse leaveResponse = new LeaveResponse();
        leaveResponse.setEmployeeId("employeeId");
        leaveResponse.setLeaveTypeId("leaveTypeId");
        leaveResponse.setStartDt("startDt");
        leaveResponse.setEndDt("endDt");
        leaveResponse.setNoOfDays("noOfDays");
        when(mockLeaveTransactionService.saveLeave(any(LeaveApplyRequest.class), eq("createdByEmpId")))
                .thenReturn(leaveResponse);

        leaveTransactionControllerUnderTest.applyLeaveTransaction("createdByEmpId",
                leaveApplyRequest);

    }

    @Test
    public void testApplyLeaveTransaction_LeaveTransactionServiceThrowsCommonException() throws Exception {
        final LeaveApplyRequest leaveApplyRequest = new LeaveApplyRequest();
        leaveApplyRequest.setEmployeeId("employeeId");
        leaveApplyRequest.setLeaveTypeId("leaveTypeId");
        leaveApplyRequest.setStartDt("startDt");
        leaveApplyRequest.setEndDt("endDt");
        leaveApplyRequest.setNoOfDays("noOfDays");

        when(mockLeaveTransactionService.saveLeave(any(LeaveApplyRequest.class), eq("createdByEmpId")))
                .thenThrow(CommonException.class);

        assertThrows(CommonException.class,
                () -> leaveTransactionControllerUnderTest.applyLeaveTransaction("createdByEmpId", leaveApplyRequest));
    }

    @Test
    public void testUpdateLeaveStatus() throws Exception {
        final ResponseEntity<String> expectedResult = new ResponseEntity<>("Leave Status Updated", HttpStatus.OK);

        final ResponseEntity<String> result = leaveTransactionControllerUnderTest.updateLeaveStatus("transactionId",
                "transactionStatus", "employeeId", "comment");

        assertEquals(expectedResult, result);
        verify(mockLeaveTransactionService).updateLeaveTransaction("transactionId", "transactionStatus", "employeeId",
                "comment");
    }

    @Test
    public void testUpdateLeaveStatus_LeaveTransactionServiceThrowsCommonException() throws Exception {
        doThrow(CommonException.class).when(mockLeaveTransactionService).updateLeaveTransaction("transactionId",
                "transactionStatus", "employeeId", "comment");

        assertThrows(CommonException.class,
                () -> leaveTransactionControllerUnderTest.updateLeaveStatus("transactionId", "transactionStatus",
                        "employeeId", "comment"));
    }
}
