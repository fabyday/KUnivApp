package com.kangwon.a356.kangwonunivapp.network;


import android.content.Context;
import android.os.MessageQueue;

import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.database.dataadapter.MessageAdapter;
import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;
import com.kangwon.a356.kangwonunivapp.dataprocess.AbstractManager;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Queue;

/**
 * 네트워크 들에 대한 전반적인 관리 클래스
 *
 * @author 노지현
 */
public class NetworkManager extends AbstractManager {

    public static final NetworkExecuteMessage SIGNIN_SUCCESS = new NetworkExecuteMessage(0, "계정 생성을 완료하였습니다.");
    public static final NetworkExecuteMessage LOGIN_SUCCESS = new NetworkExecuteMessage(1, "로그인에 성공하였습니다.");
    public static final NetworkExecuteMessage SIGNIN_FAILED = new NetworkExecuteMessage(2, "존재하는 계정입니다.");
    public static final NetworkExecuteMessage LOGIN_FAILED = new NetworkExecuteMessage(2, "로그인에 실패하였습니다.");
    public static final NetworkExecuteMessage CHECK_ATTANDANCE_SUCCESS = new NetworkExecuteMessage(3, "출석체크 성공");
    public static final NetworkExecuteMessage CHECK_ATTANDANCE_FAILED = new NetworkExecuteMessage(4, "출석체크 실패");
    public static final NetworkExecuteMessage OPEN_ATTANDANCE_SUCCESS = new NetworkExecuteMessage(5, "출석부가 성공적으로 열렸습니다.");
    public static final NetworkExecuteMessage OPEN_ATTANDANCE_FAILED = new NetworkExecuteMessage(6, "출석부가 성공적으로 열리지 않았습니다.");
    public static final NetworkExecuteMessage GET_TIMETABLE_SUCCESS = new NetworkExecuteMessage(7, "시간표 가져오기 성공");
    public static final NetworkExecuteMessage GET_TIMETABLE_FAILED = new NetworkExecuteMessage(8, "시간표 가져오기 실패");


    private static NetworkManager nManager = null;
    HttpsConnectionHelper networkHelper;
    Queue networkQueue;


    Thread networkThread = null;
    private MessageAdapter helperListener;

    private NetworkManager(Queue queue) {
        networkHelper = new HttpsConnectionHelper(helperListener);
        this.networkQueue = queue;


        helperListener = new MessageAdapter() {
            @Override
            public void receive(MessageObject msg) {

                callMessage(msg);

            }
        };

        networkHelper.addAdapter(helperListener);
        if (networkThread == null)
            networkThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        //큐가 비지 않으면 계속해서 큐를 비운다.
                        while (!networkQueue.isEmpty())
                            networkHelper.connect((MessageObject) networkQueue.poll());

                        try {
                            synchronized (networkQueue) {
                                networkQueue.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        networkThread.start();

    }

    public static NetworkManager getInstance(Queue queue) {
        if (nManager == null)
            return (nManager = new NetworkManager(queue));
        return nManager;
    }


    /**
     * GET 메시지를 주면 networHelper를 통해 메시지를 전달한다.
     */
    public void connect() {

        synchronized (networkQueue) {
            networkQueue.notify();
        }

    }

    @Override
    public void callMessage(MessageObject msg) {
        Iterator iter = super.getIterator();
        while (iter.hasNext()) {
            ((Message) iter.next()).receive(msg);
        }
    }

}
