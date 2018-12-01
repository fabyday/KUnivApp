package com.kangwon.a356.kangwonunivapp.database;

import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

public class TimeSpaceInfo {
    public static final String DAY_TYPE = "day";
    public static final String CLASSNAME_TYPE = "classname";
    public static final String START_TYPE = "starttime";
    public static final String END_TYPE = "endtime";


    public static SimpleDateFormat TIME = new SimpleDateFormat("kk:mm");
    public static SimpleDateFormat DAY = new SimpleDateFormat("E");

    private String classRoom;

    private Date day;
    private Date startTime;
    private Date endTime;


    public TimeSpaceInfo(String day, String classRoom, String startTime, String endTime)
    {
        this.classRoom = classRoom;
        try {
            this.startTime = TIME.parse(startTime);
            this.endTime = TIME.parse(endTime);
            this.day = DAY.parse(day);
        }catch(ParseException e)
        {
            e.printStackTrace();
        }
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getDay() {
        return day;
    }

    public String getClassRoom(){return classRoom;}


}
