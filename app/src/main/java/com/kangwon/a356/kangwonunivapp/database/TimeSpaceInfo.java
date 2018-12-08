package com.kangwon.a356.kangwonunivapp.database;

import android.widget.TextView;

import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

public class TimeSpaceInfo {
    public static final String DAY_TYPE = "day"; //날짜
    public static final String CLASSNAME_TYPE = "classplace"; //장소
    public static final String START_TYPE = "starttime"; //시작시간
    public static final String END_TYPE = "endtime"; //종료 시간


    public static SimpleDateFormat TIME = new SimpleDateFormat("kk:mm");

    private String classRoom;

    private String day;
    private Date startTime;
    private Date endTime;


    public TimeSpaceInfo(String day, String classRoom, String startTime, String endTime) {
        this.classRoom = classRoom;
        this.day=day;
        try {
            this.startTime = TIME.parse(startTime);
            this.endTime = TIME.parse(endTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getDay() {
        return day;
    }

    public String getClassRoom() {
        return classRoom;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            System.out.println("Object Same");
            return true;
        }
        TimeSpaceInfo info = (TimeSpaceInfo)obj;
        if (day.equals(info.getDay()))
            if (startTime.equals(info.getStartTime())) {
                if(endTime.equals(info.getEndTime()))
                return true;
            }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;
        hashCode = prime * hashCode + ((day == null) ? 0 : day.hashCode());
        hashCode = prime * hashCode + startTime.hashCode();
        hashCode = prime * hashCode + endTime.hashCode();
        return hashCode;
    }
}
