package com.kangwon.a356.kangwonunivapp.database.datainterface;

import android.accessibilityservice.GestureDescription;

import com.kangwon.a356.kangwonunivapp.database.MessageObject;

import java.util.LinkedHashMap;

public interface Message {

     MessageObject makeQueryMessage();
     void receive(MessageObject msg);

}
