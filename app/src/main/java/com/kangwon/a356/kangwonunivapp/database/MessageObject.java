package com.kangwon.a356.kangwonunivapp.database;

import com.google.gson.JsonParser;
import com.kangwon.a356.kangwonunivapp.dataprocess.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * 메시지 객체이다. 내부에서 사용할 메시지와 외부에서 사용할 메시지를 구분하지 않고 사용 가능하게 해준다.
 * 모든 객체들은 이 메시지 객체를 사용하여 통신한다.
 * @see com.kangwon.a356.kangwonunivapp.database.datainterface.Message
 * @see com.kangwon.a356.kangwonunivapp.database.dataadapter.MessageAdapter
 */
public class MessageObject {
    public static final String LOGIN_TYPE= "login";
    public static final String STUDENT_TIMETABLE_TYPE= "studenttimetable";
    public static final String INSTRUCTOR_TIME_TABLE_TYPE= "instructortimetable";;

    private String type;
    private ArrayList<LinkedHashMap> message;

    public MessageObject(LinkedHashMap[] msg)
    {
        message =new ArrayList<>();
        for(int i = 0; i<msg.length; i++)
            message.add(msg[i]);
        type=(String)msg[0].get("type");
    }

    public MessageObject(ArrayList<LinkedHashMap> msg)
    {
        message = msg;
       type =(String)msg.get(0).get("type");

    }

    public MessageObject(JSONObject msg)
    {
        this( JSONParser.toArrayList(msg));
    }

    public MessageObject(JSONArray msg)
    {
        this(JSONParser.toArrayList(msg));
    }

    public MessageObject(LinkedHashMap msg)
    {
        message =new ArrayList<>();
        message.add(msg);
        type =(String)msg.get("type");
    }


    public String getType()
    {
        return type;
    }


    /**
     * 메시지를 ArrayList를 반환해준다.
     * @return
     */
    public ArrayList<LinkedHashMap> getMessage() {
        return message;
    }

    /**
     * 메시지를 GET 방식의 인자로 만들어준다.
     * @return GET타입의 인자. URL에 붙여서 보낼 수 있다.
     */
    public String toGETMessage()
    {
        String getMsg="/"+type+"?";
        LinkedHashMap msg = message.get(0);
        Iterator iter = msg.keySet().iterator();

        iter.next(); //첫 번째 거는 버린다. 이미 type에 존재하기 때문.
        while(iter.hasNext())
        {
            String key =(String)iter.next();
            getMsg += key + "=" + msg.get(key)+"&";
        }
        return getMsg;
    }

    /**
     * 메시지가 어떤 타입의 메시지인지 확인해 준다.
     * @param obj Message객체를 받을시 같은 타입인지 확인해준다.
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(this.type.equals(obj))
            return true;
        return false;
    }
}
