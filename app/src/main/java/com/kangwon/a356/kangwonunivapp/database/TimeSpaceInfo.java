package com.kangwon.a356.kangwonunivapp.database;

import android.widget.TextView;

import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TimeSpaceInfo implements Comparable<TimeSpaceInfo>{
    public static final String DAY_TYPE = "day"; //날짜
    public static final String CLASSNAME_TYPE = "classplace"; //장소
    public static final String START_TYPE = "starttime"; //시작시간
    public static final String END_TYPE = "endtime"; //종료 시간


    public static final DayType DAY_STRING_TO_INTEGER = new DayType();



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

    public String getStartTime() {
        return TIME.format(startTime);
    }

    public String getEndTime() {
        return TIME.format(endTime);
    }



    public Date getStartTimeDate()
    {
        return startTime;
    }
    public Date getEndTimeDate()
    {
        return endTime;
    }

    public long getStartTimeInteger()
    {
        return startTime.getTime();
    }
    public long getEndTimeInteger()
    {
        return endTime.getTime();
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


    @Override
    public String toString() {
        return "시간 :" + getStartTime()+" ~ "+getEndTime()+"-" + "[ 장소 : " + classRoom+" ]";
    }


    public static class DayType
    {
        public HashMap<String, Integer> day;
        public HashMap<Integer, String> dayInteger;

        public DayType()
        {
            day = new HashMap<>();
            day.put("SUN", 0);day.put("MON", 1);
            day.put("TUE", 2);day.put("WED", 3);
            day.put("THU", 4);day.put("FRI", 5);
            day.put("SAT", 6);


            dayInteger = new HashMap<>();
            dayInteger.put(0, "SUN"); dayInteger.put(1, "MON");
            dayInteger.put(2, "TUE"); dayInteger.put(3, "WED");
            dayInteger.put(4, "THU"); dayInteger.put(5, "FRI");
            dayInteger.put(6, "SAT");

        }

        public int toInteger(String day)
        {
            return this.day.get(day).intValue();
        }

        public String toStringDay(int i){ return this.dayInteger.get(new Integer(i));};
    }


    @Override
    public int compareTo(TimeSpaceInfo timeSpaceInfo) {
        int flag = this.startTime.compareTo(timeSpaceInfo.getStartTimeDate());
        return flag;
    }
}
