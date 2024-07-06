package com.itime.compoff.service.impl;

import com.itime.compoff.enumeration.EnumCompOffPeriod;
import com.itime.compoff.enumeration.EnumCompOffTransactionStatus;
import com.itime.compoff.enumeration.EnumCompOffUsageStatus;
import com.itime.compoff.enumeration.EnumStatus;
import com.itime.compoff.primary.entity.CompOffTransaction;
import com.itime.compoff.primary.entity.LeaveSummary;
import com.itime.compoff.primary.repository.CompOffTransactionRepo;
import com.itime.compoff.primary.repository.LeaveSummaryRepo;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import com.itime.compoff.validation.BusinessValidationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CompOffTransactionServiceImplTest {

        @Mock
        private CompOffTransactionRepo compOffTransactionRepo;

        @Mock
        private LeaveSummaryRepo leaveSummaryRepo;

        @Mock
        private BusinessValidationService businessValidationService;

        @InjectMocks
        private CompOffTransactionServiceImpl compOffTransactionService;

        @Test
        void testUpdateCompOffApproval_Approved() throws Exception {
            long transactionId = 1L;
            String status = "APPROVED";
            String compOffFor = EnumCompOffPeriod.FULL_DAY.name();
            String comment = "CompOff approved";

            CompOffTransaction compOffTransaction = new CompOffTransaction();
            compOffTransaction.setId(transactionId);
            compOffTransaction.setStatus(EnumStatus.ACTIVE);
            compOffTransaction.setTransactionStatus(EnumCompOffTransactionStatus.PENDING);
            compOffTransaction.setEmployeeId(10L);

            when(compOffTransactionRepo.findTop1ByIdAndStatus(transactionId, EnumStatus.ACTIVE)).thenReturn(Optional.of(compOffTransaction));
            when(businessValidationService.findEmployeeDetail(compOffTransaction.getEmployeeId())).thenReturn(new EmployeeDetail());

            // Call the method
            compOffTransactionService.updateCompOffApproval(Long.toString(transactionId), status, compOffFor, comment);

            // Verify changes
            verify(compOffTransactionRepo).save(compOffTransaction);
            assertEquals(EnumCompOffTransactionStatus.APPROVED, compOffTransaction.getTransactionStatus());
            assertEquals(EnumCompOffUsageStatus.AVAILABLE, compOffTransaction.getCompOffUsageStatus());
            assertNotNull(compOffTransaction.getApproverRemarks());
            assertNotNull(compOffTransaction.getLastUpdatedDt());
            assertEquals(compOffFor, compOffTransaction.getApprovedFor().name());
            verify(leaveSummaryRepo).save(any(LeaveSummary.class));
        }

        @Test
        void testUpdateCompOffApproval_TransactionNotFound() throws Exception {
            long transactionId = 1L;
            String status = "APPROVED";
            String compOffFor = EnumCompOffPeriod.FULL_DAY.name();
            String comment = "CompOff approved";

            when(compOffTransactionRepo.findTop1ByIdAndStatus(transactionId, EnumStatus.ACTIVE)).thenReturn(Optional.empty());

            compOffTransactionService.updateCompOffApproval(Long.toString(transactionId), status, compOffFor, comment);
        }

        @Test
        void testUpdateCompOffApproval_AlreadyApproved() throws Exception {
            long transactionId = 1L;
            String status = "APPROVED";
            String compOffFor = EnumCompOffPeriod.FULL_DAY.name();
            String comment = "CompOff approved";

            CompOffTransaction compOffTransaction = new CompOffTransaction();
            compOffTransaction.setId(transactionId);
            compOffTransaction.setStatus(EnumStatus.ACTIVE);
            compOffTransaction.setTransactionStatus(EnumCompOffTransactionStatus.APPROVED);
            compOffTransaction.setEmployeeId(10L);

            when(compOffTransactionRepo.findTop1ByIdAndStatus(transactionId, EnumStatus.ACTIVE)).thenReturn(Optional.of(compOffTransaction));
            when(businessValidationService.findEmployeeDetail(compOffTransaction.getEmployeeId())).thenReturn(new EmployeeDetail());

            compOffTransactionService.updateCompOffApproval(Long.toString(transactionId), status, compOffFor, comment);
        }

        @Test
        void testUpdateCompOffApproval_InvalidCompOffFor() throws Exception {
            long transactionId = 1L;
            String status = "APPROVED";
            String compOffFor = "INVALID";
            String comment = "CompOff approved";

            compOffTransactionService.updateCompOffApproval(Long.toString(transactionId), status, compOffFor, comment);
        }
    }


