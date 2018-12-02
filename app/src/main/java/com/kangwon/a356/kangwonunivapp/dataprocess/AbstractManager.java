package com.kangwon.a356.kangwonunivapp.dataprocess;

import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * process 매니저 아래에 있는 모든 매니저 클래스들의 추상 클래스.
 */
public abstract class AbstractManager {

    private ArrayList<Message> messageQueue;

    public AbstractManager() {
        messageQueue = new ArrayList<>();
    }

    public void add(Message message) {
        messageQueue.add(message);
    }

    public Iterator getIterator() {
        return messageQueue.iterator();
    }

    /**
     *
     * @param msg 메시지를 받으면 콜백방식으로 다음을 수행하게 한다.
     */
    public abstract void callMessage(MessageObject msg);


}
