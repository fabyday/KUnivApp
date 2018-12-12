package com.kangwon.a356.kangwonunivapp.dataprocess.database.dataadapter;

import com.kangwon.a356.kangwonunivapp.dataprocess.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.dataprocess.database.datainterface.Message;

public class MessageAdapter implements Message {
    @Override
    public MessageObject makeQueryMessage(MessageObject refMsg) {
        return null;
    }

    @Override
    public void receive(MessageObject msg) {

    }

}
