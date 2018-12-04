package com.kangwon.a356.kangwonunivapp.database.datainterface;

import com.kangwon.a356.kangwonunivapp.database.MessageObject;

public interface Message {

     MessageObject makeQueryMessage(MessageObject refMsg);
     void receive(MessageObject msg);

}
