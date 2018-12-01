package com.kangwon.a356.kangwonunivapp.dataprocess;


import android.content.Context;
import android.provider.ContactsContract;
import android.widget.LinearLayout;

import com.kangwon.a356.kangwonunivapp.database.DataManager;
import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.database.UserInfo;
import com.kangwon.a356.kangwonunivapp.database.dataadapter.MessageAdapter;
import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;
import com.kangwon.a356.kangwonunivapp.network.NetworkExecuteMessage;
import com.kangwon.a356.kangwonunivapp.network.NetworkManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;


/**
 * 이 클래스는 전체적인 백그라운드 작업 진행을 총괄한다.
 * 앱에 단 한개의 클래스만이 존재할 수 있는 싱글톤 스타일의 클래스이다.
 * @version 1
 */
public class ProcessManager{
    private static ProcessManager processManager=null;

    private DataManager dataManager;
    private NetworkManager networkManager;
    private MessageAdapter[] adapters;

    Queue<MessageObject> networkManagerQueue;
    Queue<MessageObject> dataManagerQueue;
    Thread pThread;

    public static final int SUCCESS = 0;
    public static final int FAILED = 1;

    public static final int NUMBER_OF_ADPTER = 2;
    public static final int NETWORK_ADPTER = 0;
    public static final int DATA_ADPTER = 1;


    /**
     * 자신 아래의 매니저를 생성하고 매니저와의 콜백 메소드들을 연결한다.
     */
    private ProcessManager()
    {
        networkManagerQueue = new SynchronousQueue<>();
        dataManagerQueue = new SynchronousQueue<>();
        pThread = new Thread(new Runnable() {
            @Override //TODO
            public void run() {

            }
        });

        dataManager = DataManager.getInstance();
        networkManager = NetworkManager.getInstance();
        adapters = new MessageAdapter[NUMBER_OF_ADPTER];
 

        adapters[DATA_ADPTER] = new MessageAdapter(){
            @Override
            public void receive(MessageObject msg) {
                //dataManager.inputMessage(msg);
                dataManagerQueue.add(msg);
            }
        };

        adapters[NETWORK_ADPTER] = new MessageAdapter(){

            @Override
            public void receive(MessageObject msg) {
                //networkManager.connect(msg);
                networkManagerQueue.add(msg);
            }

        };


        //완료를 알려줄 어댑터
        dataManager.add(adapters[DATA_ADPTER]);
        networkManager.add(adapters[NETWORK_ADPTER]);
    }


    /**
     * 앱에 유일하게 존재하는 ProcessManger Instance를 반환한다.
     * @return ProcessManager 객체 반환.
     */
    public static ProcessManager getInstance()
    {
        if(processManager == null)
            processManager= new ProcessManager();
        return processManager;
    }


    /**
     * 최초 로그인시 필요한 메소드이다. 이후부터는 토큰 혹은 구현에 따라 세션에 의해 로그인이 유지될 것임.
     * @param id
     * @param password
     */
    public void login(String id, String password){

        ArrayList<LinkedHashMap> data = new ArrayList<>();
        LinkedHashMap<String, String> msg = new LinkedHashMap<>();

        msg.put(MessageObject.TYPE, MessageObject.LOGIN_TYPE);
        msg.put(UserInfo.ID, id);
        msg.put(UserInfo.PASSWORD, password);
        data.add(msg);

        MessageObject msgData = new MessageObject(data);
        msgData.setRequestStatus(MessageObject.REQUEST_QUERY);


        dataManager.inputMessage(msgData);
    }

    /**
     * 회원가입을 위한 메소드. 웹서버에 회원가입 질의한다.
     * @param id
     * @param name
     * @param password
     */
    public void signin(String id, String name, String password)
    {
        ArrayList<LinkedHashMap> data = new ArrayList<>();
        LinkedHashMap<String, String> msg = new LinkedHashMap<>();

        msg.put(MessageObject.TYPE, MessageObject.SIGNIN_TYPE);
        msg.put(UserInfo.ID, id);
        msg.put(UserInfo.NAME, name);
        msg.put(UserInfo.PASSWORD, password);
        data.add(msg);

        MessageObject msgData = new MessageObject(data);
        msgData.setRequestStatus(MessageObject.REQUEST_QUERY);

        networkManager.connect(msgData);
    }

    /**
     * 핸들러를 통해 외부의 데이터와 연결한다.
     * 리스트를 업데이트한다.
     */
    public void updateList(int listType)
    {

    }

    /**
     * 핸들러를 통해 외부의 데이터와 연결한다.
     * 시간표를 업데이트한다.
     */
    public void updateTimetable(int tableType)
    {

    }



}
