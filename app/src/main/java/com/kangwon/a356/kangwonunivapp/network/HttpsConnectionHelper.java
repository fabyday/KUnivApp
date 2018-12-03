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

    public static int CONNECTION_IO_EXCEPTION = 0;
    public static int PURE_IO_EXCEPTION =1 ;
    int flag;

    private static final String serverURL = "http://bugtail.iptime.org";
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
            System.out.println("msg.toGETMessage()"+msg.toGETMessage());
            flag = HttpsConnectionHelper.CONNECTION_IO_EXCEPTION;
            urlCon = (HttpURLConnection) url.openConnection();


            urlCon.setRequestMethod("GET");
            urlCon.setRequestProperty("Content-Type", "application/json");
            urlCon.setDoInput(true);

            if (urlCon.getResponseCode() != 200)
                System.out.println("연결 실패");
            else
                System.out.println("연결 성공");


            flag = HttpsConnectionHelper.PURE_IO_EXCEPTION;
            bis = new BufferedInputStream(urlCon.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(bis, "UTF-8"));
            String line;
            String page = "";
            while ((line = reader.readLine()) != null)
                page += line;
            bis.close();


            MessageObject recvMsg = new MessageObject(new JSONArray(new String(page)));

            String type = msg.getType();
            switch(type)
            {
                case MessageObject.LOGIN_TYPE:
                    recvMsg.setNEM(NetworkManager.LOGIN_SUCCESS);
                    recvMsg.setMessageQueueType(MessageObject.DATA_MANAGER);
                    break;
                case MessageObject.SIGNIN_TYPE:
                    recvMsg.setNEM(NetworkManager.SIGNIN_SUCCESS);
                    recvMsg.setMessageQueueType(MessageObject.JUST_PROCESS_MANAGER);
                    break;
                case MessageObject.STUDENT_TIMETABLE_TYPE:
                    recvMsg.setMessageQueueType(MessageObject.FROM_PROCESS_MANAGER_TO_DATA_MANAGER);
                    break;
                case MessageObject.INSTRUCTOR_TIME_TABLE_TYPE:
                    recvMsg.setMessageQueueType(MessageObject.FROM_PROCESS_MANAGER_TO_DATA_MANAGER);
                    break;
                case MessageObject.CHECK_ATTANDANCE:
                    recvMsg.setMessageQueueType(MessageObject.FROM_PROCESS_MANAGER_TO_DATA_MANAGER);
                    break;
                case MessageObject.OPEN_ATTANDANCE:
                    recvMsg.setMessageQueueType(MessageObject.FROM_PROCESS_MANAGER_TO_DATA_MANAGER);
                    break;
                case MessageObject.OPEN_LECTURE:
                    recvMsg.setMessageQueueType(MessageObject.FROM_PROCESS_MANAGER_TO_DATA_MANAGER);
                    break;
                case MessageObject.JOIN_LECTURE:
                    recvMsg.setMessageQueueType(MessageObject.FROM_PROCESS_MANAGER_TO_DATA_MANAGER);
                    break;

            }

            /*
            if (msg.equals(MessageObject.LOGIN_TYPE))//받은 메시지에 무엇이 성공했는지 포함해 준다.
            {
                recvMsg.setNEM(NetworkManager.LOGIN_SUCCESS);
            }
            else if (msg.equals(MessageObject.SIGNIN_TYPE))
            {
                recvMsg.setNEM(NetworkManager.SIGNIN_SUCCESS);
            }
            else if(msg.equals(MessageObject.STUDENT_TIMETABLE_TYPE))
            {

            }
            else if(msg.equals(MessageObject.INSTRUCTOR_TIME_TABLE_TYPE))
            */


            adapter.receive(recvMsg);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {

            if(flag == CONNECTION_IO_EXCEPTION)
                System.out.println("CONNECTION NULL");
            if(flag == PURE_IO_EXCEPTION)
                System.out.println("String not found");


        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(!(flag == CONNECTION_IO_EXCEPTION))
                urlCon.disconnect();
        }

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
