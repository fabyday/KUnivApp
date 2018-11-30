package com.kangwon.a356.kangwonunivapp.database.datainterface;

import android.accessibilityservice.GestureDescription;

public interface Message {

    public String makeQueryMessage();
    public void receive(String msg);
}
