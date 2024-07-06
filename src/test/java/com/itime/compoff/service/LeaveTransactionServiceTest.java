package com.itime.compoff.service;

import com.itime.compoff.exception.CommonException;
import com.itime.compoff.exception.DataNotFoundException;
import com.itime.compoff.model.LeaveApplyRequest;
import com.itime.compoff.model.LeaveResponse;
import com.itime.compoff.primary.entity.LeaveTransaction;
import com.itime.compoff.service.impl.LeaveTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaveTransactionServiceTest {

    @Mock
    private LeaveTransactionService leaveTransactionService;

    @InjectMocks
    private LeaveTransactionServiceImpl leaveTransactionServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveLeave() throws CommonException {
        LeaveApplyRequest request = new LeaveApplyRequest();
        String createdByEmpId = "123";
        LeaveResponse expectedResponse = new LeaveResponse();

        when(leaveTransactionService.saveLeave(request, createdByEmpId)).thenReturn(expectedResponse);

        LeaveResponse actualResponse = leaveTransactionServiceImpl.saveLeave(request, createdByEmpId);

        assertEquals(expectedResponse, actualResponse);
        verify(leaveTransactionService, times(1)).saveLeave(request, createdByEmpId);
    }

    @Test
    public void testUpdateLeaveTransaction() throws CommonException {
        String transactionId = "1";
        String status = "approved";
        String employeeId = "emp123";
        String comment = "Leave approved";

        doNothing().when(leaveTransactionService).updateLeaveTransaction(transactionId, status, employeeId, comment);

        leaveTransactionServiceImpl.updateLeaveTransaction(transactionId, status, employeeId, comment);

        verify(leaveTransactionService, times(1)).updateLeaveTransaction(transactionId, status, employeeId, comment);
    }

    @Test
    public void testUpdateCompoffTransactionOnApproval() throws DataNotFoundException {
        LeaveTransaction leaveTransaction = new LeaveTransaction();

        doNothing().when(leaveTransactionService).updateCompoffTransactionOnApproval(leaveTransaction);

        leaveTransactionServiceImpl.updateCompoffTransactionOnApproval(leaveTransaction);

        verify(leaveTransactionService, times(1)).updateCompoffTransactionOnApproval(leaveTransaction);
    }

}