package com.kangwon.a356.kangwonunivapp.network;

import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

/**
 * https 요청 응답을 처리해 주는 클래스이다.
 * @author 노지현
 */

public class HttpsConnectionHelper{

    private static final String url= "http://bugtail.iptime.org";
    AsyncHttpClient httpClient;

    public HttpsConnectionHelper(){

        httpClient = new AsyncHttpClient();
    }

    /**
     */
    public void connect(MessageObject msg)
    {

        httpClient.get(url + msg.toGETMessage(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }






}
