package com.kangwon.a356.kangwonunivapp.database;

import com.kangwon.a356.kangwonunivapp.database.dataadapter.MessageAdapter;
import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;


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


public class TimeTableInfo implements Message {



    ArrayList<ClassInfo> timeTable;
    private int problemIndex;


    public TimeTableInfo() {
        timeTable = new ArrayList<>();

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







    @Override
    public MessageObject makeQueryMessage() {
        return null;
    }

    @Override
    public MessageObject makeQueryMessage(String[] values) {
        return null;
    }

    @Override
    public void receive() {}


    /**
     * 데이터를 받으면 초기화한다.
     * @param msg
     */
    @Override
    public void receive(MessageObject msg) {
        ArrayList msgList =  msg.getMessage();
        int size = msgList.size();

        //각 클래스로 보낸 콜백함수.
        MessageAdapter adapter=new MessageAdapter(){
            @Override
            public void receive(MessageObject msg) {
                problemIndex=Integer.parseInt(msg.getType());
            }
        };
        for(int i = 0; i<size; i++)
        {
            LinkedHashMap data = (LinkedHashMap)msgList.get(i);
            TimeSpaceInfo timeSpaceInfo= new TimeSpaceInfo((String)data.get(TimeSpaceInfo.DAY),
                                                            (String)data.get(TimeSpaceInfo.CLASSNAME_TYPE),
                                                            (String)data.get(TimeSpaceInfo.START_TYPE),
                                                            (String)data.get(TimeSpaceInfo.END_TYPE));
            ClassInfo classInfo = new ClassInfo((String)data.get(ClassInfo.CLASSNAME), (String)data.get(ClassInfo.INSTRUCTOR), i);
            classInfo.add(adapter);
            //만일 강사의 과목이 중복된 정보가 들어올 경우 중복된 정보에 TimeSpace 정보만 추가한다.
            if(timeTable.contains(classInfo)) {
                timeTable.get(problemIndex).addTimeSpaceInfo(timeSpaceInfo);
            }else{
                classInfo.addTimeSpaceInfo(timeSpaceInfo);
                timeTable.add(classInfo);
            }
        }
    }

}
