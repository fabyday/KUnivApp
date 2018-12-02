package com.kangwon.a356.kangwonunivapp.database.dataadapter;

import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;

public class MessageAdapter implements Message {
    @Override
    public MessageObject makeQueryMessage() {
        return null;
    }

    @Override
    public void receive(MessageObject msg) {

    }

    @Override
    public void receive() {

    }
}
