package com.kangwon.a356.kangwonunivapp.dataprocess;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class JSONParser {


    /**
     *
     * @param jsonMsgList JSONArray 를 받아서 LinkedHashMap 타입을 가지는 ArrayList를 반환
     * @return
     */
    public static ArrayList<LinkedHashMap> toArrayList(JSONArray jsonMsgList)
    {


        ArrayList<LinkedHashMap> msgList = new ArrayList<>();

        for(int i = 0; i < jsonMsgList.length() ; i++ )
        {
            try {
                LinkedHashMap<String, String> msgObject = new LinkedHashMap<>();
                JSONObject tmpJsonMsg= jsonMsgList.getJSONObject(i);

                Iterator iter = tmpJsonMsg.keys();

                while(iter.hasNext())
                {
                    String key = (String)iter.next();
                    msgObject.put(key,tmpJsonMsg.getString(key));
                }
                msgList.add(msgObject);

            }catch(JSONException e)
            {
                e.printStackTrace();
            }
        }


        return msgList;

    }


    /**
     *
     * @param jsonMsg jsonObject를 파싱해서 Message 객체로 만들어준다.
     * @return
     */

    public static ArrayList<LinkedHashMap> toArrayList(JSONObject jsonMsg)
    {

        ArrayList<LinkedHashMap> msgArray = new ArrayList<>();
        LinkedHashMap<String, String> msg = new LinkedHashMap<>();
        //
        try {
            Iterator iter = jsonMsg.keys();
            while(iter.hasNext())
            {
                String key =(String)iter.next();
                msg.put(key, jsonMsg.getString(key));
            }
            msgArray.add(msg);
        }catch(JSONException e)
        {
            e.printStackTrace();
        }

        return msgArray;

    }


}
