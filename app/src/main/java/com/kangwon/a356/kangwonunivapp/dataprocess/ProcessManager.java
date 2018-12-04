package com.kangwon.a356.kangwonunivapp.dataprocess;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.kangwon.a356.kangwonunivapp.database.DataManager;
import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.database.UserInfo;
import com.kangwon.a356.kangwonunivapp.database.dataadapter.MessageAdapter;
import com.kangwon.a356.kangwonunivapp.network.NetworkManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;


/**
 * 이 클래스는 전체적인 백그라운드 작업 진행을 총괄한다.
 * 앱에 단 한개의 클래스만이 존재할 수 있는 싱글톤 스타일의 클래스이다.
 * 프로세스 매니저와 그 하위 클래스들의 모든 스레드는 큐가 비면 깊은 잠에 빠진다.
 *
 * @author 노지현
 * @version 1
 */
public class ProcessManager {
    private static ProcessManager processManager = null;
    Handler handler;


    private DataManager dataManager;
    private NetworkManager networkManager;
    private MessageAdapter[] adapters;

    Queue<MessageObject> networkManagerQueue;
    Queue<MessageObject> procoessMangerQueue;
    Queue<MessageObject> dataManagerQueue;
    Thread pThread;

    public static final int NUMBER_OF_ADPTER = 2;
    public static final int NETWORK_ADPTER = 0;
    public static final int DATA_ADPTER = 1;


    /**
     * 자신 아래의 매니저를 생성하고 매니저와의 콜백 메소드들을 연결한다.
     */
    private ProcessManager(final Handler handler) {
        this.handler = handler;
        networkManagerQueue = new LinkedList<>();
        dataManagerQueue = new LinkedList<>();
        procoessMangerQueue = new LinkedList<>();

        dataManager = DataManager.getInstance(dataManagerQueue);
        networkManager = NetworkManager.getInstance(networkManagerQueue);
        adapters = new MessageAdapter[NUMBER_OF_ADPTER];

        adapters[DATA_ADPTER] = new MessageAdapter() {
            @Override
            public void receive(MessageObject msg) {
                if(msg != null)
                    addItemToQueue(msg);
                requestProcess();
            }
        };
        adapters[NETWORK_ADPTER] = new MessageAdapter() {

            @Override
            public void receive(MessageObject msg) {
                addItemToQueue(msg);
                requestProcess();
            }
        };

        //완료를 알려줄 어댑터
        dataManager.add(adapters[DATA_ADPTER]);
        networkManager.add(adapters[NETWORK_ADPTER]);


        pThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    Log.i("ProcessThread", "스레드 시작");
                    while (!procoessMangerQueue.isEmpty()) {
                        Log.i("ProcessThread", "시작전 큐 사이즈 : "+procoessMangerQueue.size());
                        MessageObject msg = procoessMangerQueue.poll();
                        Log.i("ProcessThread", "시작후 큐 사이즈 : "+procoessMangerQueue.size());
                        int flag = msg.getMessageQueueType();
                        if ( flag == MessageObject.PROCESS_MANAGER) {
                            Log.i("ProcessThread", "유저로 전달 : "+msg.toGETMessage());
                            Message msgUsedByHandler = new Message();
                            msgUsedByHandler.obj = msg;
                            handler.sendMessage(msgUsedByHandler); //메시지를 외부로 보내준다.
                        }
                        else if(flag == MessageObject.DATA_MANAGER)
                        {
                            Log.i("ProcessThread", "데이터매니저로 전달 : "+msg.toGETMessage());
                            dataManagerQueue.offer(msg);
                            dataManager.inputMessage();
                        }
                        else if(flag == MessageObject.NETWORK_MANAGER) {
                            Log.i("ProcessThread", "네트워크 매니저로 전달"+msg.toGETMessage());
                            networkManagerQueue.offer(msg);
                            networkManager.connect();
                        }
                        Log.i("ProcessThread", "메시지 완료");
                    }

                    try {
                        synchronized (procoessMangerQueue) {
                            Log.i("ProcessThread", "Runnable");
                            procoessMangerQueue.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Log.i("ProcessThread", "Re Running");

                }
            }
        });
        pThread.setName("Process-Threasd");

        pThread.start();// 스레드는 큐가 빌 경우 잠에 빠진다.
    }


    /**
     * 앱에 유일하게 존재하는 ProcessManger Instance를 반환한다.
     *
     * @return ProcessManager 객체 반환.
     */
    public static ProcessManager getInstance(Handler handler) {
        if (processManager == null)
            processManager = new ProcessManager(handler);
        return processManager;
    }


    /**
     * 최초 로그인시 필요한 메소드이다. 이후부터는 토큰 혹은 구현에 따라 세션에 의해 로그인이 유지될 것임.
     *
     * @param id
     * @param password
     */
    public void login(String id, String password) {

        ArrayList<LinkedHashMap> data = new ArrayList<>();
        LinkedHashMap<String, String> msg = new LinkedHashMap<>();

        msg.put(MessageObject.TYPE, MessageObject.LOGIN_TYPE);
        msg.put(UserInfo.ID, id);
        msg.put(UserInfo.PASSWORD, password);
        data.add(msg);

        MessageObject msgData = new MessageObject(data);
        msgData.setRequestStatus(MessageObject.REQUEST_FOR_ALL);
        msgData.setMessageQueueType(MessageObject.DATA_MANAGER);
        addItemToQueue(msgData);
        requestProcess();
    }


    /**
     * 회원가입을 위한 메소드. 웹서버에 회원가입 질의한다.
     *
     * @param id       String 타입의 아이디
     * @param name     String 타입의 이름
     * @param password String 타입의 비밀번호
     */
    public void signin(String id, String name, String password) {

        ArrayList<LinkedHashMap> data = new ArrayList<>();
        LinkedHashMap<String, String> msg = new LinkedHashMap<>();

        msg.put(MessageObject.TYPE, MessageObject.SIGNIN_TYPE);
        msg.put(UserInfo.ID, id);
        msg.put(UserInfo.NAME, name);
        msg.put(UserInfo.PASSWORD, password);
        data.add(msg);

        MessageObject msgData = new MessageObject(data);
        msgData.setRequestStatus(MessageObject.REQUEST_FOR_ALL);

        msgData.setMessageQueueType(MessageObject.NETWORK_MANAGER);
        addItemToQueue(msgData);
        requestProcess();
    }

    /**
     * 핸들러를 통해 외부의 데이터와 연결한다.
     * 리스트를 업데이트한다.
     *
     * @param type 메시지의 타입을 정의한다. 테이블이냐, 아니면 리스트냐 등.
     */
    public void updateRequest(String type) {

        ArrayList<LinkedHashMap> data = new ArrayList<>();
        LinkedHashMap<String, String> msg = new LinkedHashMap<>();
        msg.put(MessageObject.TYPE, type);
        data.add(msg);

        MessageObject msgData = new MessageObject(data);

        msgData.setRequestStatus(MessageObject.REQUEST_FOR_ALL);

        msgData.setMessageQueueType(MessageObject.DATA_MANAGER);

        addItemToQueue(msgData);
        requestProcess();
    }

    /**
     * 이 request는 procoessQueue의 동기화를 담당하는 메소드이다.
     *
     * @param msg
     */
    private void addItemToQueue(MessageObject msg) {
        synchronized (procoessMangerQueue) {
            procoessMangerQueue.offer(msg);
            Log.i("ProcessThread", "큐에 추가 ");
        }
    }

    private void requestProcess() {
        synchronized (procoessMangerQueue) {
            procoessMangerQueue.notify();
            Log.i("ProcessThread", "요청");
        }
    }


}
