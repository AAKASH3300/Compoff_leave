package com.itime.compoff.mapper;

import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import org.junit.Before;
import org.junit.Test;

public class CompOffMapperTest {

    private CompOffMapper compOffMapperUnderTest;

    @Before
    public void setUp() {
        compOffMapperUnderTest = new CompOffMapper();
    }

    @Test
    public void testMapApplyRequestToEntity() {

        final EmployeeDetail employeeDetails = new EmployeeDetail();
        employeeDetails.setId(1L);
        employeeDetails.setEmpId("101");
        employeeDetails.setFirstName("Aakash");
        employeeDetails.setLastName("B");
        employeeDetails.setOfficialEmail("aakash.b@aaludra.com");
        employeeDetails.setApproverId(employeeDetails);

        final CompOffApplyRequest compOffApplyRequest = new CompOffApplyRequest();
        compOffApplyRequest.setRequestedDate("2024-06-23 00:00:00");
        compOffApplyRequest.setPunchIn("11:00:00");
        compOffApplyRequest.setPunchOut("19:00:00");
        compOffApplyRequest.setWorkHours("8");
        compOffApplyRequest.setRequestedFor("FULL_DAY");
        compOffApplyRequest.setReason("reason");

        compOffMapperUnderTest.mapApplyRequestToEntity(employeeDetails,compOffApplyRequest);
    }
}
