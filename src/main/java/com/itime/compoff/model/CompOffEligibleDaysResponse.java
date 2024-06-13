package com.itime.compoff.model;

public class CompOffEligibleDaysResponse {

    private String date;
    private String eligibleType;
    private String punchIn;
    private String punchOut;
    private String workHours;

    public CompOffEligibleDaysResponse() {
    }

    public CompOffEligibleDaysResponse(String date, String eligibleType, String punchIn, String punchOut, String workHours) {
        this.date = date;
        this.eligibleType = eligibleType;
        this.punchIn = punchIn;
        this.punchOut = punchOut;
        this.workHours = workHours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEligibleType() {
        return eligibleType;
    }

    public void setEligibleType(String eligibleType) {
        this.eligibleType = eligibleType;
    }

    public String getPunchIn() {
        return punchIn;
    }

    public void setPunchIn(String punchIn) {
        this.punchIn = punchIn;
    }

    public String getPunchOut() {
        return punchOut;
    }

    public void setPunchOut(String punchOut) {
        this.punchOut = punchOut;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }
}
