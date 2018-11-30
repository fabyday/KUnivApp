package com.kangwon.a356.kangwonunivapp.database;


import java.util.ArrayList;

/**
 * 시간표에 사용될 강의에 대한 정보이다.
 * 실제 화면에 나타낼 정보에 대한 입력값을 가지는 클래스이다.
 * @author 노지현
 * @see TimeTableInfo
 *
 */
public class ClassInfo {


    private String className; // 강의 이름
    private String instructor;
    private ArrayList<TimeInfo> timeInfo;

    public ClassInfo(String className, String instructor, TimeInfo[] infos)
    {
        this.className = className;
        this.instructor = instructor;
        timeInfo = new ArrayList<>();
        for(int i=0; i<infos.length; i++)
        {
            timeInfo.add(infos[i]);
        }

    }

    public TimeInfo[] getTimeInfo()
    {
        return timeInfo.toArray(new TimeInfo[0]);
    }

    public String getClassName()
    {
        return className;
    }

    public String getInstructor()
    {
        return instructor;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof String)
            return (className+instructor).equals(obj);
        return false;
    }


}
