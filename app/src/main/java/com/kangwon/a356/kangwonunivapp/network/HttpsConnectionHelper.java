package com.kangwon.a356.kangwonunivapp.network;

import com.google.gson.JsonArray;
import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.database.dataadapter.MessageAdapter;
import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * https 요청 응답을 처리해 주는 클래스이다.
 * @author 노지현
 */

public class HttpsConnectionHelper{

    private static final String url= "http://bugtail.iptime.org";
    AsyncHttpClient httpClient;
    MessageAdapter adapter ;
    public HttpsConnectionHelper(MessageAdapter adapter){

        httpClient = new AsyncHttpClient();
        addAdapter(adapter);
    }


    /**
     * MessageObject를 받아 웹서버에 메시지를 보낸다.
     * @param msg
     */
    public void connect(final MessageObject msg)
    {

        httpClient.get(url + msg.toGETMessage(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final MessageObject recvMsg;
                try {
                     recvMsg= new MessageObject(new JSONArray(new String(responseBody)));

                     if(msg.equals(MessageObject.LOGIN_TYPE))//받은 메시지에 무엇이 성공했는지 포함해 준다.
                        recvMsg.setNEM(NetworkManager.LOGIN_SUCCESS);
                     else if(msg.equals(MessageObject.SIGNIN_TYPE))
                        recvMsg.setNEM(NetworkManager.SIGNIN_SUCCESS);
                     adapter.receive(recvMsg);
                }catch(JSONException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }


    public void addAdapter(MessageAdapter adapter)
    {
        this.adapter = adapter;
    }




}
