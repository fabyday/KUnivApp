package com.kangwon.a356.kangwonunivapp.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


/**
 * @author 노지현
 * @version 1
 * @see ClassInfo
 * 시간표 정보는 ClassInfo 인스턴스를 가지는 ArrayList를 내부적으로 가지며 필요에 따라 외부로 ClassInfo를 보내준다.
 * ClassInfo에 대한 초기화는 로그인이 완료 된 이후에 진행되며, TimeTableInfo 클래스에 의해서 초기화된다.
 * 초기화 조건
 * 만일 내부 데이터베이스에 존재할 경우, TimeTable을 내부 데이터베이스를 통해서 초기화시키며
 * 만일 내부 데이터베이스에 존재하지 않을 경우, TimeTable을 외부 서버의 데이터베이스를 통해서 초기화 시킨다.
 *
 * 추가사항
 *
 */


public class TimeTableInfo {

    ArrayList<ClassInfo> timeTable;
    int size; //현재 들어있는 갯수.
    public TimeTableInfo() {
        timeTable = new ArrayList<>();
        size = 0;
    }



    /**
     *
     * @return 시간표 ArrayList의 Iterator를 반환함.
     * 이를 통해서 타임테이블에 관한 것을 반복적으로 그릴 수 있음.
     */
    public Iterator<ClassInfo> getTimeTable() {
        return timeTable.iterator();
    }

    /**
     *
     * @param index 인덱스
     * @return 지정한 인덱스의 값을 반환한다.
     * 지정한 인덱스의 값을 반환하고, 이후에 값을 지운다.
     */
    public ClassInfo removeClassInfo(int index) {

        return timeTable.remove(index);
    }

    /**
     *
     * @param index 원하는 인덱스
     * @return 지정한 인덱스의 값
     * 인덱스의 값을 뽑으며, remove와 다르게 삭제하지 않음.
     */
    public ClassInfo getClassInfo(int index)
    {
        return timeTable.get(index);
    }

    /**
     *
     * @param classInfo 외부에서 생성된 classInfo를 추가한다.
     */
    public void addTimeTable(ClassInfo classInfo)
    {
        timeTable.add(classInfo);
        size++;
    }


    /**
     *
     * @param className 수업 이름
     * @param instructor 강의자 이름
     * @param date 수업 시작, 종료시간, 및 날짜
     * 내부적으로 ClassInfo를 생성하여 시간표에 집어 넣음.
     */
    public void addTimeTable(String className, String instructor , TimeInfo[] date)
    {

        timeTable.add(new ClassInfo(className, instructor, date));
        size++;
    }


    public void addTimeTable(String data)
    {
        Iterator iter = timeTable.iterator();

        while(iter.hasNext());
        {
            if(iter.next().equals(data));
        }


    }
}
