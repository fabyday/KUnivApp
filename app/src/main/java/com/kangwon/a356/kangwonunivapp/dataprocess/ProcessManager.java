package com.kangwon.a356.kangwonunivapp.dataprocess;


import android.content.Context;
import android.provider.ContactsContract;

import com.kangwon.a356.kangwonunivapp.database.DataManager;
import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.database.dataadapter.MessageAdapter;
import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;
import com.kangwon.a356.kangwonunivapp.network.NetworkExecuteMessage;
import com.kangwon.a356.kangwonunivapp.network.NetworkManager;

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
                pThread.start();
            }
        };

        adapters[NETWORK_ADPTER] = new MessageAdapter(){

            @Override
            public void receive(MessageObject msg) {
                //networkManager.connect(msg);
                networkManagerQueue.add(msg);
                pThread.start();
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


    public void login(String id, String password){

        //dataManager.inputMessage("UserInfo",new String[]{id, password});

    }

    public void signin(String id, String name, String password)
    {

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
