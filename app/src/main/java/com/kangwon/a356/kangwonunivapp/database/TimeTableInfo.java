package com.kangwon.a356.kangwonunivapp.database;

import java.util.ArrayList;


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
    public TimeTableInfo()
    {
        timeTable = new ArrayList<>();
    }

}
