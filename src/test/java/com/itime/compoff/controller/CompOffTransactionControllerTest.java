package com.itime.compoff.controller;

import com.itime.compoff.exception.CommonException;
import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.service.CompOffTransactionService;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompOffTransactionControllerTest {

    @Mock
    private CompOffTransactionService mockCompOffTransactionService;

    private CompOffTransactionController compOffTransactionControllerUnderTest;

    @Before
    public void setUp() {
        compOffTransactionControllerUnderTest = new CompOffTransactionController();
        compOffTransactionControllerUnderTest.compOffTransactionService = mockCompOffTransactionService;
    }

    @Test
    public void testSaveCompOffTransaction() throws Exception {

        final CompOffApplyRequest compOffApplyRequest = new CompOffApplyRequest();
        compOffApplyRequest.setEmployeeId(0L);
        compOffApplyRequest.setRequestedDate("requestedDate");
        compOffApplyRequest.setPunchIn("punchIn");
        compOffApplyRequest.setPunchOut("punchOut");
        compOffApplyRequest.setWorkHours("workHours");

        when(mockCompOffTransactionService.createCompOffTransaction(any(CompOffApplyRequest.class)))
                .thenReturn(HttpStatus.OK);

        final HttpStatus result = compOffTransactionControllerUnderTest.saveCompOffTransaction(compOffApplyRequest);

        assertEquals(HttpStatus.OK, result);
    }

    @Test
    public void testSaveCompOffTransaction_CompOffTransactionServiceThrowsCommonException() throws Exception {

        final CompOffApplyRequest compOffApplyRequest = new CompOffApplyRequest();
        compOffApplyRequest.setEmployeeId(1L);
        compOffApplyRequest.setRequestedDate("2024-07-08 00:00:00");
        compOffApplyRequest.setPunchIn("11:00:00");
        compOffApplyRequest.setPunchOut("19:00:00");
        compOffApplyRequest.setWorkHours("8");

        when(mockCompOffTransactionService.createCompOffTransaction(any(CompOffApplyRequest.class)))
                .thenThrow(CommonException.class);

        assertThrows(CommonException.class,
                () -> compOffTransactionControllerUnderTest.saveCompOffTransaction(compOffApplyRequest));
    }

    @Test
    public void testUpdateCompOffApproval() throws Exception {

        final ResponseEntity<String> expectedResult = new ResponseEntity<>("Comp Off status updated", HttpStatus.OK);

        final ResponseEntity<String> result = compOffTransactionControllerUnderTest.updateCompOffApproval(
                "20", "APPROVED", "compOffFor", "comment");

        assertEquals(expectedResult, result);
        verify(mockCompOffTransactionService).updateCompOffApproval("20", "APPROVED", "compOffFor",
                "comment");
    }

    @Test
    public void testUpdateCompOffApproval_CompOffTransactionServiceThrowsCommonException() throws Exception {

        doThrow(CommonException.class).when(mockCompOffTransactionService).updateCompOffApproval("20",
                "APPROVED", "compOffFor", "comment");

        assertThrows(CommonException.class,
                () -> compOffTransactionControllerUnderTest.updateCompOffApproval("20", "APPROVED",
                        "compOffFor", "comment"));
    }
}
