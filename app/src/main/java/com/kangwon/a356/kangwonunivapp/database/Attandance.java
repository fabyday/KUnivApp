package com.kangwon.a356.kangwonunivapp.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Attandance implements Comparable<Attandance>{

    public static String CHECK_DATE = "checkdate";
    public static String CHECK_TIME = "time";
    public static String STUDENT_ID = "student";
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private String student ;
    private Date  date;
    private Date time;


    public Attandance(String studentName, String date, String time)
    {
      this.student=studentName;
      try {
          this.date = dateFormat.parse(date);
          this.time = dateFormat.parse(time);
      }catch(ParseException e)
      {
          e.printStackTrace();
      }
    }

    public Date getDate() {
        return date;
    }

    public Date getTime() {
        return time;
    }

    public String getStudent() {
        return student;
    }

    @Override
    public String toString() {
        return student+" "+date+" "+time;
    }

    @Override
    public int compareTo(Attandance o) {
        int flag = this.date.compareTo(o.getDate());
        if( flag== 0)
        {
            this.time.compareTo(o.getTime());
        }
        return flag;
    }
}
