package com.kangwon.a356.kangwonunivapp.database;

import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;

import java.util.LinkedHashMap;

public class UserInfo implements Message {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String TOKEN = "token";

    private String id = null;
    private String name = null;
    private String passwd = null;
    private Byte Token = null;

    /**
     * 자동으로 데이터베이스에 존재하는 id와 passwd를 찾아 초기화한다.
     * 없을 경우 모든 id와 passwd는 null 값을 가진다.
     *
     *
     * 사용자의 입력 : String id, String pw
     * 사용자에게 보내는 메시지 NetworkExecuteMessage msg = SUCC or FAIL
     */
    public UserInfo() {

        id = null;
        passwd = null;
        name = null;
    }


    /**
     * @param id 학번 이름
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param passwd 비밀번호 이름.
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return id가 존재할 경우 id를 반환한다.
     * @throws InformationNotFoundException 정보를 찾을 수 없을때 발생하는 예외.
     *                                      이 메소드는 id를 반환한다. 이는 자동 로그인에 사용된다.
     */
    public String getId() throws InformationNotFoundException {
        if (id == null)
            throw new InformationNotFoundException("there is no ID data");
        return id;
    }


    /**
     * @return passwd가 존재할 경우 password를 반환한다.
     * @throws InformationNotFoundException 이는 자동 로그인에 사용된다.
     */
    public String getPasswd() throws InformationNotFoundException {
        if (passwd == null)
            throw new InformationNotFoundException("there is no passwd data.");
        return passwd;
    }


    /**
     * 로그인 메시지를 만들어 준다.
     *
     * @return
     * @param refMsg
     */
    @Override
    public MessageObject makeQueryMessage(MessageObject refMsg) {
        LinkedHashMap data = new LinkedHashMap();
        data.put(MessageObject.TYPE, MessageObject.LOGIN_TYPE);
        data.put(UserInfo.ID, id);
        data.put(UserInfo.PASSWORD, passwd);

        MessageObject msgData = null;
        int flag = refMsg.getRequsetStatus();
        switch (flag) {
            case MessageObject.REQUEST_FOR_ALL: case MessageObject.JUST_REQUEST_HINT:case MessageObject.NOT_REQUEST_QUERY://사용자의 메시지 요청은 메시지를 네트워크로 메시지를 요청해준다.
                msgData= new MessageObject(data);
                msgData.setMessageQueueType(MessageObject.NETWORK_MANAGER);
                msgData.setRequestStatus(MessageObject.REQUEST_FOR_ALL);
                break;
            case MessageObject.RESPONSE_HINT:case MessageObject.RESPONSE_FOR_REQUEST : //네트워크에서 부터 온 것은 전부 프로세스 매니저로 자신을 첨부해서 보낸다.
                msgData= new MessageObject();
                msgData.setProcessedData(refMsg.getNEM());
                msgData.setRequestStatus(MessageObject.RESPONSE_HINT);
                msgData.setMessageQueueType(MessageObject.PROCESS_MANAGER);
                break;
        }

        return msgData;
    }


    @Override
    public void receive(MessageObject msg) {
        LinkedHashMap msgData = msg.getMessage().get(0);
        this.id = (String) msgData.get(UserInfo.ID);
        this.passwd = (String) msgData.get(UserInfo.PASSWORD);
    }
}
