package com.kangwon.a356.kangwonunivapp.database;


import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * 시간표에 사용될 강의에 대한 정보이다.
 * 실제 화면에 나타낼 정보에 대한 입력값을 가지는 클래스이다.
 *
 * @author 노지현
 * @see TimeTableInfo
 */
public class ClassInfo {

    public static final String CLASSNAME = "classname"; //강좌 이름
    public static final String INSTRUCTOR = "instructor"; //강의자 이름
    public static final String START_DATE = "startdate";
    public static final String END_DATE = "enddate";

    private String className; // 강의 이름
    private String instructor;
    private ArrayList<TimeSpaceInfo> timeSpaceInfo;
    private ArrayList<Attandance> attandanceLists; //출석


    public static SimpleDateFormat DATE = new SimpleDateFormat("YYYY-MM-DD");


    private Date startDate; //시작일
    private Date endDate; //종료일


    public ClassInfo(String className, String instructor)
    {
        this.className = className;
        this.instructor = instructor;

    }

    public ClassInfo(String className, String instructor, String startDate, String endDate) {
        this(className, instructor);
        try {
            this.startDate = DATE.parse(startDate);
            this.endDate = DATE.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timeSpaceInfo = new ArrayList<>();
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void addTimeSpaceInfo(TimeSpaceInfo info) {
        if(timeSpaceInfo.indexOf(info) == -1)
            timeSpaceInfo.add(info);
    }

    /**
     * @return 이 수업의 시간표를 얻을 때 사용
     */
    public TimeSpaceInfo[] getTimeInfo() {
        return timeSpaceInfo.toArray(new TimeSpaceInfo[0]);
    }

    public String getClassName() {
        return className;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setAttandanceList(MessageObject msg) {
        this.attandanceLists = new ArrayList<>();

        ArrayList msgArray = msg.getMessage();
        int size = msgArray.size();
        for(int i = 0 ; i<size; i++){
            LinkedHashMap mapMsg =(LinkedHashMap) msgArray.get(i);
            Attandance attandance = new Attandance(
                    (String)mapMsg.get(Attandance.STUDENT_ID),
                    (String)mapMsg.get(Attandance.CHECK_DATE),
                    (String)mapMsg.get(Attandance.CHECK_TIME));

            if(attandanceLists.indexOf(attandance) == -1)
                attandanceLists.add(attandance);
        }

    }


    /**
     * 오버라이딩한 함수로, 객체가 존재하는지를 찾을 때 사용. 이 객체를 사용하기 위해서는 어댑터를 등록해주어야 한다.
     * 만일 존재할 경우, 등록된 어댑터에 객체가 존재하는 리스트의 인덱스를 반환한다.
     *
     * @param obj ClassInfo 타입을 넣어주어야 한다.
     * @return
     */
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
        ClassInfo info = (ClassInfo)obj;
        if (className.equals(info.getClassName()))
            if (instructor.equals(info.getInstructor())) {
                return true;
            }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;
        hashCode = prime * hashCode + ((className == null) ? 0 : className.hashCode());
        hashCode = prime * hashCode + instructor.hashCode();
        return hashCode;
    }



    @Override
    public String toString() {
        return "className : " + className + "instructor : " + instructor;
    }
}
