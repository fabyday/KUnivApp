package com.kangwon.a356.kangwonunivapp.database;

import java.util.Date;

public class TimeInfo {
    String day;
    Date startTime;
    Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getDay() {
        return day;
    }
}
