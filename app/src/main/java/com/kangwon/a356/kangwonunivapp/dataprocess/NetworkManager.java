package com.kangwon.a356.kangwonunivapp.dataprocess;


import android.util.Log;

import com.kangwon.a356.kangwonunivapp.dataprocess.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.dataprocess.database.dataadapter.MessageAdapter;
import com.kangwon.a356.kangwonunivapp.dataprocess.database.datainterface.Message;
import com.kangwon.a356.kangwonunivapp.dataprocess.network.HttpsConnectionHelper;
import com.kangwon.a356.kangwonunivapp.dataprocess.network.NetworkExecuteMessage;

import java.util.Iterator;
import java.util.Queue;

/**
 * 네트워크 들에 대한 전반적인 관리 클래스
 *
 * @author 노지현
 */
public class NetworkManager extends AbstractManager {

    public static final NetworkExecuteMessage SUCCESS = new NetworkExecuteMessage(NetworkExecuteMessage.SUCCESS, "성공적으로 처리되었습니다.");
    public static final NetworkExecuteMessage FAIL = new NetworkExecuteMessage(NetworkExecuteMessage.FAIL, "실패되었습니다.");


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

                System.out.println("네트워크 전달하기요. : "+msg.getNEM().toString());
                callMessage(msg);

            }
        };

        networkHelper.addAdapter(helperListener);
        if (networkThread == null)
            networkThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        Log.i("NetworkManager", "networkThread 동작 시작");
                        //큐가 비지 않으면 계속해서 큐를 비운다.
                        while (!networkQueue.isEmpty()) {
                            Log.i("NetworkManager", "시작 전 큐 사이즈 : " + networkQueue.size());
                            networkHelper.connect((MessageObject) networkQueue.poll());
                            Log.i("NetworkManager", "시작 후 큐 사이즈 : " + networkQueue.size());
                        }

                        try {
                            synchronized (networkQueue) {
                                Log.i("NetworkManager", "networkThread 대기");
                                networkQueue.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        networkThread.setName("NetworkThread");
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
            Log.i("NetworkManager", "NetworkThread가 깨어날 준비를 합니다.");
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
