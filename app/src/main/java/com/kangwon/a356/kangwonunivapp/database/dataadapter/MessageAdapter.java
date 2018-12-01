package com.kangwon.a356.kangwonunivapp.database.dataadapter;

import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;

import java.util.LinkedHashMap;

public class MessageAdapter implements Message {
    @Override
    public MessageObject makeQueryMessage() {
        return null;
    }

    @Override
    public MessageObject makeQueryMessage(String[] values) {
        return null;
    }

    @Override
    public void receive(MessageObject msg) {

    }

    @Override
    public void receive() {

    }
}
