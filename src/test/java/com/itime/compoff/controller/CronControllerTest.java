package com.itime.compoff.controller;

import com.itime.compoff.job.DailyJob;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CronControllerTest {

    @Mock
    private DailyJob mockDailyJob;

    private CronController cronControllerUnderTest;

    @Before
    public void setUp() {
        cronControllerUnderTest = new CronController();
        cronControllerUnderTest.dailyJob = mockDailyJob;
    }

    @Test
    public void testNotifyExpiringCompOffs() {

        final ResponseEntity<String> expectedResult = new ResponseEntity<>("CompOff expiry notification job triggered successfully.", HttpStatus.OK);

        final ResponseEntity<String> result = cronControllerUnderTest.notifyExpiringCompOffs();

        assertEquals(expectedResult, result);
        verify(mockDailyJob).checkForExpiringCompOffs();
    }
}
