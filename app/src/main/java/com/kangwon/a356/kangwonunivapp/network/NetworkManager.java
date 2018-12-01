package com.kangwon.a356.kangwonunivapp.network;


import android.content.Context;
import android.os.MessageQueue;

import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.database.dataadapter.MessageAdapter;
import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;
import com.kangwon.a356.kangwonunivapp.dataprocess.AbstractManager;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 *
 * 네트워크 들에 대한 전반적인 관리 클래스
 * @author 노지현
 */
public class NetworkManager extends AbstractManager {

    private static NetworkManager nManager=null;
    HttpsConnectionHelper networkHelper;
    Thread waitThread;
    private MessageAdapter helperListener = new MessageAdapter(){
        @Override
        public void receive(MessageObject msg) {
            final MessageObject fmsg= msg;
            if(waitThread==null)
            waitThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    callMessage(fmsg);
                }
            });

            waitThread.start();
        }
    };

    private NetworkManager() {
        networkHelper = new HttpsConnectionHelper();
    }

    public static NetworkManager getInstance()
    {
        if(nManager == null)
            return new NetworkManager();
        return nManager;
    }


    /**
     * GET 메시지를 주면 networHelper를 통해 메시지를 전달한다.
     * @param msg
     */
    public void connect(MessageObject msg)
    {

             networkHelper.connect(msg);

    }

    @Override
    public void callMessage(MessageObject msg) {
        Iterator iter = super.getIterator();
        while(iter.hasNext())
        {
            ((Message)iter.next()).receive(msg);
        }
    }

}
