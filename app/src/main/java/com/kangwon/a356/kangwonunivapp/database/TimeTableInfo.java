package com.kangwon.a356.kangwonunivapp.database;

import com.kangwon.a356.kangwonunivapp.database.dataadapter.MessageAdapter;
import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;

import java.util.ArrayList;
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
 * <p>
 * 추가사항
 */


public class TimeTableInfo implements Message {


    private ArrayList<ClassInfo> timeTable;
    private String tableType; //학생 테이블인가 강사 테이블인가
    private int problemIndex; //문제가 되는 인덱스를 찾아내는 변수 외부에서 사용 안됨.
    private UserInfo userInfo; //내부에서 쿼리를 만들 때 사용한다.

    public TimeTableInfo(String Type) {
        this.tableType = Type;
        timeTable = new ArrayList<>();
    }


    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }


    /**
     * @param index 원하는 인덱스
     * @return 지정한 인덱스의 값
     * 인덱스의 값을 뽑으며, remove와 다르게 삭제하지 않음.
     */
    public ClassInfo getClassInfo(int index) {
        return timeTable.get(index);
    }


    @Override
    public MessageObject makeQueryMessage(MessageObject refMsg) {


        ArrayList<LinkedHashMap> data = new ArrayList<>();
        LinkedHashMap<String, String> msg = new LinkedHashMap<>();


        try {
            msg.put(MessageObject.TYPE, refMsg.getType());
            msg.put(UserInfo.ID, userInfo.getId());
        } catch (InformationNotFoundException e) {
            e.printStackTrace();
        }
        data.add(msg);
        MessageObject msgData = null;
        int flag = refMsg.getRequsetStatus();
        switch (flag) {
            case MessageObject.REQUEST_FOR_ALL:
            case MessageObject.JUST_REQUEST_HINT:
            case MessageObject.NOT_REQUEST_QUERY://사용자의 메시지 요청은 메시지를 네트워크로 메시지를 요청해준다.
                firstServ(refMsg);
                msgData = new MessageObject(data);
                msgData.setRequestStatus(MessageObject.REQUEST_FOR_ALL);
                msgData.setMessageQueueType(MessageObject.NETWORK_MANAGER);
                break;
            case MessageObject.RESPONSE_FOR_REQUEST: //네트워크에서 부터 온 것은 전부 프로세스 매니저로 자신을 첨부해서 보낸다.
                msgData = new MessageObject(data);
                msgData.setProcessedData(this);
                msgData.setRequestStatus(MessageObject.RESPONSE_FOR_REQUEST);
                msgData.setMessageQueueType(MessageObject.PROCESS_MANAGER);
                break;
            case MessageObject.RESPONSE_HINT: //힌트를 유저에게 전달
                msgData = new MessageObject();
                msgData.setProcessedData(refMsg.getNEM());
                msgData.setRequestStatus(MessageObject.RESPONSE_HINT);
                msgData.setMessageQueueType(MessageObject.PROCESS_MANAGER);
                break;
        }

        return msgData;

    }


    /**
     * 데이터를 받으면 초기화한다.
     *
     * @param msg
     */
    @Override
    public void receive(MessageObject msg) {

        switch (msg.getType()) {
            case MessageObject.ALL_LIST: //시간표 갱신 연산
            case MessageObject.STUDENT_TIMETABLE_TYPE:
            case MessageObject.INSTRUCTOR_TIME_TABLE_TYPE:
                tableUpdate(msg);
                break;
            case MessageObject.STUDENT_ATTANDANCE_LIST: //출석 갱신 연산
            case MessageObject.INSTRUCTOR_ATTANDANCE_LIST:
                AttandanceUpdate(msg);
                break;
            case MessageObject.JOIN_LECTURE:
                break;

        }

    }

    private void AttandanceUpdate(MessageObject msg) {
        ArrayList msgList = msg.getMessage();
        int index;
        int size = msgList.size();
        //각 클래스로 보낸 콜백함수.


        for (int i = 0; i < size; i++) {
            LinkedHashMap data = (LinkedHashMap) msgList.get(i);

            ClassInfo classInfo = new ClassInfo((String) data.get(ClassInfo.CLASSNAME), (String) data.get(ClassInfo.INSTRUCTOR));

            //만일 강사의 과목이 중복된 정보가 들어올 경우 중복된 정보에 TimeSpace 정보만 추가한다.

            if ((index = timeTable.indexOf(classInfo)) != -1) {
                timeTable.get(index).setAttandanceList(msg);
            }
        }
    }


    private void tableUpdate(MessageObject msg) {
        ArrayList msgList = msg.getMessage();
        int index;
        int size = msgList.size();


        //각 클래스로 보낸 콜백함수.


        for (int i = 0; i < size; i++) {
            LinkedHashMap data = (LinkedHashMap) msgList.get(i);
            TimeSpaceInfo timeSpaceInfo = new TimeSpaceInfo((String) data.get(TimeSpaceInfo.DAY),
                    (String) data.get(TimeSpaceInfo.CLASSNAME_TYPE),
                    (String) data.get(TimeSpaceInfo.START_TYPE),
                    (String) data.get(TimeSpaceInfo.END_TYPE));

            ClassInfo classInfo = new ClassInfo((String) data.get(ClassInfo.CLASSNAME),
                    (String) data.get(ClassInfo.INSTRUCTOR),
                    (String) data.get(ClassInfo.START_DATE),
                    (String) data.get(ClassInfo.END_DATE));

            //만일 강사의 과목이 중복된 정보가 들어올 경우 중복된 정보에 TimeSpace 정보만 추가한다.

            if ((index = timeTable.indexOf(classInfo)) == -1) {
                timeTable.add(classInfo);
            } else {
                timeTable.get(index).addTimeSpaceInfo(timeSpaceInfo);
            }
        }
    }


    private void firstServ(MessageObject refMsg) {
        ArrayList msg = refMsg.getMessage();
        int size = msg.size();
        String type = refMsg.getType();
        switch (type) {
            case MessageObject.DEL_LECTURE:
                int index;
                LinkedHashMap hashMsg = (LinkedHashMap) msg.get(size - 1);
                ClassInfo classInfo = new ClassInfo((String) hashMsg.get(ClassInfo.CLASSNAME), (String) hashMsg.get(ClassInfo.INSTRUCTOR));
                if ((index = timeTable.indexOf(classInfo)) != -1)
                    timeTable.remove(index);
            case MessageObject.OPEN_LECTURE:
                tableUpdate(refMsg);
                break;
            case MessageObject.OPEN_ATTANDANCE:case MessageObject.CLOSE_ATTANDANCE:  //알 수 있는 방법이 없어서 무시된다. 어플리케이션 단에서 처리한다.
            case MessageObject.CHECK_ATTANDANCE:
                break;
        }

    }


}
