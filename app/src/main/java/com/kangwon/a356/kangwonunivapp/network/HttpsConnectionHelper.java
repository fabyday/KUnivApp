package com.kangwon.a356.kangwonunivapp.network;


import android.content.Context;
import android.content.res.AssetManager;
import android.provider.DocumentsContract;
import android.util.Log;

import com.kangwon.a356.kangwonunivapp.dataprocess.XMLParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpClientConnection;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * https 요청 응답을 처리해 주는 클래스이다.
 * @author 노지현
 */
public class HttpsConnectionHelper {

    AsyncHttpClient httpClient;
    PersistentCookieStore myCookies=null;
    HttpConnection.Response response;

    public HttpsConnectionHelper(){
        httpClient = new AsyncHttpClient();

    }

    /**
     * @deprecated 사용되지 않음. connect를 사용할 것.
     */
    public void connect1()
    {
        try{


            //로그인 페이지 접속
            response = (HttpConnection.Response) Jsoup.connect("https://m.kangwon.ac.kr")
                    //.timeout(30000)
                    //.header("Aceept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8" )
                    //.header("Accept-Encoding", "gzip, deflate, br")
                    //.header("Connection", "keep-alive")
                    .header("Content-Length", "2156")
                    //.header("Content-Type", " text/plain;charset=UTF-8")
                    //.header("Origin", "https://m.kangwon.ac.kr")
                    //.header("Referer", "https://m.kangwon.ac.kr/knu/KNUMobile_v13/")
                    .method(Connection.Method.GET)
                    .execute();

            //로그인 페이지에서 얻은 쿠키
            Map<String, String> loginTryCookies = response.cookies();

            //로그인 전송하는 토큰
            Document loginPageDocument = response.parse();


            String ofp = loginPageDocument.select("input.ofp").val();
            String nfp = loginPageDocument.select("input.nfp").val();


            String userAgent = "Mozilla/5.0 (Linux; U; Android 2.1-update1; ko-kr; Nexus One Build/ERE27) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17";


            Map<String, String> data = new HashMap<>();
            data.put("fsp_action", "defaultAction");
            data.put("fsp_cmd", "execute");
            data.put("fsp_logId", "all");
            data.put("ID", "201413381");
            data.put("PW", "29899");
            data.put("ofp", ofp); // 로그인 페이지에서 얻은 토큰들
            data.put("nfp", nfp);


            response = (HttpConnection.Response) Jsoup.connect("https://m.kangwon.ac.kr")
                    .timeout(3000)
                    .userAgent(userAgent)
                    .header("Aceept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8" )
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Connection", "keep-alive")
                    .header("Content-Length", "2156")
                    .header("Content-Type", " text/plain;charset=UTF-8")
                    .header("Origin", "https://m.kangwon.ac.kr")
                    .header("Referer", "https://m.kangwon.ac.kr/knu/KNUMobile_v13/")
                    .cookies(loginTryCookies)
                    .data(data)
                    .method(Connection.Method.POST)
                    .execute();
            Map<String, String> loginCookie = response.cookies();

            System.out.println("성공적");
            Log.i("HttpHelper", "clear connect");


        }catch(IOException e)
        {
            e.printStackTrace();
        }



    }


    public void connect()
    {
        System.out.println(XMLParser.makeMessage());
        Map<String, String> data = new HashMap<>();
        data.put("fsp_action", "defaultAction");
        data.put("fsp_cmd", "execute");
        data.put("fsp_logId", "all");
        data.put("ID", "201413381");
        data.put("PW", "29899");
        RequestParams params = new RequestParams(data);

        httpClient.setBasicAuth("201413381", "29899");
        httpClient.post("https://m.kangwon.ac.kr/knu/KNUMobile_v13/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Parser.HeaderToString(headers);
                Parser.bodyToString(responseBody);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }


    /**
     * Parsing을 위한 내부 클래스
     */
    static class Parser{

        public  static String[][] HeaderToString(Header[] headers){
            String[][] str=new String[headers.length][]; //헤더를 받아오기 위한 레그드 배열
            for(int i =0; i<headers.length; i++)
            {
                HeaderElement[] t =headers[i].getElements();
                str[i]=new String[t.length];

                str[i][0] = t[0].getName();
                for(int j=1; j<t.length; j++)
                    str[i][j]=t[j].getValue();
            }
            return str;
        }


        /**
         * body를 String 형식으로 변환시켜 준다.
         * @param responseBody Http 연결에서 받아온 바디.
         * @return Byte형식의 Body를 String으로 변환한 것을 리턴한다.
         */
        public static String bodyToString(byte[] responseBody)
        {
            String str = new String(responseBody);
            System.out.println(str);
            return str;
        }
    }


}
