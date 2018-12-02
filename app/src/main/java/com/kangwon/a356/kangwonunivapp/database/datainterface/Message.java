package com.kangwon.a356.kangwonunivapp.database.datainterface;

import android.accessibilityservice.GestureDescription;

import com.kangwon.a356.kangwonunivapp.database.MessageObject;

import java.util.LinkedHashMap;

public interface Message {

    public MessageObject makeQueryMessage();
    public void receive(MessageObject msg);
    public void receive();

}
