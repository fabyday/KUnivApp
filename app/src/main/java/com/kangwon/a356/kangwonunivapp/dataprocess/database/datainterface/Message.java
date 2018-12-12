package com.kangwon.a356.kangwonunivapp.dataprocess.database.datainterface;

import com.kangwon.a356.kangwonunivapp.dataprocess.database.MessageObject;

public interface Message {

     MessageObject makeQueryMessage(MessageObject refMsg);
     void receive(MessageObject msg);

}
