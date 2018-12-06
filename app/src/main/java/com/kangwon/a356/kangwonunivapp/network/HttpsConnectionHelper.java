package com.kangwon.a356.kangwonunivapp.network;

import android.util.Log;

import com.google.gson.JsonArray;
import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.database.dataadapter.MessageAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * https 요청 응답을 처리해 주는 클래스이다.
 *
 * @author 노지현
 */

public class HttpsConnectionHelper {


    private static final String serverURL = "http://bugtail.iptime.org:8080";
    HttpURLConnection urlCon = null;
    MessageAdapter adapter = null;

    public HttpsConnectionHelper(MessageAdapter adapter) {
        addAdapter(adapter);
    }


    /**
     * MessageObject를 받아 웹서버에 메시지를 보낸다.
     *
     * @param msg
     */
    public void connect(MessageObject msg) {
        try {

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllX509TrustManager()}, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String string, SSLSession ssls) {
                    return true;
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        BufferedInputStream bis = null;

        try {

            URL url = new URL(serverURL + msg.toGETMessage());
            urlCon = (HttpURLConnection) url.openConnection();


            urlCon.setRequestMethod("GET");
            urlCon.setRequestProperty("Content-Type", "application/json");
            urlCon.setDoInput(true);

            bis = new BufferedInputStream(urlCon.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(bis, "UTF-8"));
            String line;
            String page = "";
            while ((line = reader.readLine()) != null)
                page += line;
            bis.close();

            Log.i("log", page);
            MessageObject lastMessage = makeMessage(msg.getType(), page); //메시지를 처리하고 마지막으로 핸들러를 달아준다.
            lastMessage.setHandler(msg.getHandler());
            adapter.receive(lastMessage);
            Log.i("HttpsConnectionHelper", "메시지 처리 완료");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("Exception", "malformedURLException");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Exception", "IOException");
        } finally {
            urlCon.disconnect();
            Log.i("Exception", "disconnected");
        }

    }

    private MessageObject makeMessage(String type, String page) {
        MessageObject recvMsg = null;
        try {

                recvMsg = new MessageObject(new JSONArray(new String(page)));
                recvMsg.setType(type);
                switch (type) {
                case MessageObject.LOGIN_TYPE:
                    setNEMessage(recvMsg);
                    recvMsg.setMessageQueueType(MessageObject.DATA_MANAGER);
                    recvMsg.setRequestStatus(MessageObject.RESPONSE_FOR_REQUEST);
                    break;
                case MessageObject.SIGNIN_TYPE:
                    setNEMessage(recvMsg);
                    recvMsg.setMessageQueueType(MessageObject.DATA_MANAGER);
                    recvMsg.setRequestStatus(MessageObject.RESPONSE_HINT);
                    break;
                case MessageObject.STUDENT_TIMETABLE_TYPE:
                    recvMsg.setNEM(NetworkManager.SUCCESS);
                    recvMsg.setMessageQueueType(MessageObject.DATA_MANAGER);
                    recvMsg.setRequestStatus(MessageObject.RESPONSE_FOR_REQUEST);
                    break;
                case MessageObject.JOIN_LECTURE:
                    setNEMessage(recvMsg);
                    recvMsg.setMessageQueueType(MessageObject.DATA_MANAGER);
                    recvMsg.setRequestStatus(MessageObject.RESPONSE_FOR_REQUEST);
                    break;
                case MessageObject.ALL_LIST:
                    recvMsg.setNEM(NetworkManager.SUCCESS);
                    recvMsg.setMessageQueueType(MessageObject.PROCESS_MANAGER);
                    break;
                case MessageObject.CHECK_ATTANDANCE:
                    setNEMessage(recvMsg);
                    recvMsg.setMessageQueueType(MessageObject.PROCESS_MANAGER);
                    break;
                case MessageObject.INSTRUCTOR_TIME_TABLE_TYPE:
                    recvMsg.setNEM(NetworkManager.SUCCESS);
                    recvMsg.setMessageQueueType(MessageObject.DATA_MANAGER);
                    break;
                case MessageObject.OPEN_ATTANDANCE:
                    setNEMessage(recvMsg);
                    recvMsg.setMessageQueueType(MessageObject.PROCESS_MANAGER);
                    break;
                case MessageObject.CLOSE_ATTANDANCE:
                    setNEMessage(recvMsg);
                    recvMsg.setMessageQueueType(MessageObject.PROCESS_MANAGER);
                    break;
                case MessageObject.OPEN_LECTURE:
                    setNEMessage(recvMsg);
                    recvMsg.setMessageQueueType(MessageObject.DATA_MANAGER);
                    break;
                case MessageObject.DEL_LECTURE:
                    setNEMessage(recvMsg);
                    recvMsg.setMessageQueueType(MessageObject.DATA_MANAGER);
                    break;
                default:
                    recvMsg.setNEM(NetworkManager.FAIL);
                    recvMsg.setMessageQueueType(MessageObject.DATA_MANAGER);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            System.out.println("네트워크 큐 타입> " + recvMsg.getMessageQueueType());
            return recvMsg;
        }

    }

    private void setNEMessage(MessageObject recvMsg) {
        System.out.println(recvMsg.getMessage().get(0).get("status"));
        if (recvMsg.getMessage().get(0).get("status").equals("SUCC") )
            recvMsg.setNEM(NetworkManager.SUCCESS);
        else
            recvMsg.setNEM(NetworkManager.FAIL);

    }


    public void addAdapter(MessageAdapter adapter) {
        this.adapter = adapter;
    }


    class TrustAllX509TrustManager implements X509TrustManager {


        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
                                       String authType) {
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
                                       String authType) {
        }

    }


}
