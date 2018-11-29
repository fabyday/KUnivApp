package com.kangwon.a356.kangwonunivapp.network;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.util.Base64;
import android.util.Log;

import com.kangwon.a356.kangwonunivapp.dataprocess.XMLParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.liquidplayer.javascript.JSContext;
import org.liquidplayer.service.LiquidView;
import org.liquidplayer.service.MicroService;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Future;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * https 요청 응답을 처리해 주는 클래스이다.
 * @author 노지현
 */
public class HttpsConnectionHelper{


    AsyncHttpClient httpClient;
    PersistentCookieStore myCookies=null;
    HttpConnection.Response response;

    public HttpsConnectionHelper(){
        httpClient = new AsyncHttpClient();

    }

    /**
     * @deprecated 사용되지 않음. connect를 사용할 것.
     */
    public void connect(Context context)
    {



        System.out.println("-----------------");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{


                    //로그인 페이지 접속
                    String url = "https://m.kangwon.ac.kr/knu/KNUMobile_v13/";
                    Connection.Response response = Jsoup.connect(url).followRedirects(true).execute();
                    Document d = Jsoup.parse(response.body());
                   Elements links  = d.select("[src]");
                    Iterator iter =links.iterator();

                    ArrayList<String> relHref = new ArrayList<>();
                    while(iter.hasNext())
                    {

                        Element link = (Element)iter.next();

                        relHref.add(link.attr("src"));

                    }


                    String[] a = relHref.toArray(new String[0]);
                    for(int i = 1; i<a.length; i++)
                    {
                        a[i].substring(1);
                    }

                    Connection.Response response2 = Jsoup.connect(a[0]).execute();

                    Connection.Response response3=null;
                    for(int i = 1; i<a.length; i++)
                    {
                        response3 = Jsoup.connect(url+a[i]).execute();

                    }

                    String temp = response2.body();

                    JSContext context1 = new JSContext();
                    context1.evaluateScript(d.toString());

                    System.out.println(context1.toString());




                }catch(IOException e)
                {
                    e.printStackTrace();
                }


            }
        }).start();


    }


    public void connect1(final Context context)
    {

        LinkedHashMap<String, String> data = new LinkedHashMap<>();
        data.put("Accept", "application/xml, text/xml, */*");
        data.put("Accept-Encoding", "gzip, deflate, br");
        data.put("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        data.put("Connection", "keep-alive");
        data.put("Content-Length", "2156");
        data.put("Content-Type", "text/plain;charset=UTF-8");
        data.put("Cookie", "_ga=GA1.3.650976355.1522233266; WMONID=DRlLOy9_rsK");
        data.put("Host", "m.kangwon.ac.kr");
        data.put("Origin", "https://m.kangwon.ac.kr" );
        data.put("Referer", "https://m.kangwon.ac.kr/knu/KNUMobile_v13/");
        data.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
        data.put("X-Requested-With", "XMLHttpRequest");

        StringEntity  s;
        String test = XMLParser.makeBodyMessage(data);
        Header[] headers = XMLParser.makeHeader(data);




        try {
            s = new StringEntity(new String(Base64.decode("PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPFJvb3QgeG1sbnM9Imh0dHA6Ly93d3cudG9iZXNvZnQuY29tL3BsYXRmb3JtL2RhdGFzZXQiPgoJPFBhcmFtZXRlcnM+CgkJPFBhcmFtZXRlciBpZD0iZnNwX2FjdGlvbiI+eERlZmF1bHRBY3Rpb248L1BhcmFtZXRlcj4KCQk8UGFyYW1ldGVyIGlkPSJmc3BfY21kIj5leGVjdXRlPC9QYXJhbWV0ZXI+CgkJPFBhcmFtZXRlciBpZD0iZnNwX2xvZ0lkIj5hbGw8L1BhcmFtZXRlcj4KCQk8UGFyYW1ldGVyIGlkPSJJRCI+MjAxNDEzMzgxPC9QYXJhbWV0ZXI+CgkJPFBhcmFtZXRlciBpZD0iUFciPjI5ODk5PC9QYXJhbWV0ZXI+Cgk8L1BhcmFtZXRlcnM+Cgk8RGF0YXNldCBpZD0iZnNwX2RzX2NtZCI+CgkJPENvbHVtbkluZm8+CgkJCTxDb2x1bW4gaWQ9IlRYX05BTUUiIHR5cGU9InN0cmluZyIgc2l6ZT0iMTAwIiAvPgoJCQk8Q29sdW1uIGlkPSJUWVBFIiB0eXBlPSJzdHJpbmciIHNpemU9IjEwIiAvPgoJCQk8Q29sdW1uIGlkPSJTUUxfSUQiIHR5cGU9InN0cmluZyIgc2l6ZT0iMjAwIiAvPgoJCQk8Q29sdW1uIGlkPSJLRVlfU1FMX0lEIiB0eXBlPSJzdHJpbmciIHNpemU9IjIwMCIgLz4KCQkJPENvbHVtbiBpZD0iS0VZX0lOQ1JFTUVOVCIgdHlwZT0iaW50IiBzaXplPSIxMCIgLz4KCQkJPENvbHVtbiBpZD0iQ0FMTEJBQ0tfU1FMX0lEIiB0eXBlPSJzdHJpbmciIHNpemU9IjIwMCIgLz4KCQkJPENvbHVtbiBpZD0iSU5TRVJUX1NRTF9JRCIgdHlwZT0ic3RyaW5nIiBzaXplPSIyMDAiIC8+CgkJCTxDb2x1bW4gaWQ9IlVQREFURV9TUUxfSUQiIHR5cGU9InN0cmluZyIgc2l6ZT0iMjAwIiAvPgoJCQk8Q29sdW1uIGlkPSJERUxFVEVfU1FMX0lEIiB0eXBlPSJzdHJpbmciIHNpemU9IjIwMCIgLz4KCQkJPENvbHVtbiBpZD0iU0FWRV9GTEFHX0NPTFVNTiIgdHlwZT0ic3RyaW5nIiBzaXplPSIyMDAiIC8+CgkJCTxDb2x1bW4gaWQ9IlVTRV9JTlBVVCIgdHlwZT0ic3RyaW5nIiBzaXplPSIxIiAvPgoJCQk8Q29sdW1uIGlkPSJVU0VfT1JERVIiIHR5cGU9InN0cmluZyIgc2l6ZT0iMSIgLz4KCQkJPENvbHVtbiBpZD0iS0VZX1pFUk9fTEVOIiB0eXBlPSJpbnQiIHNpemU9IjEwIiAvPgoJCQk8Q29sdW1uIGlkPSJCSVpfTkFNRSIgdHlwZT0ic3RyaW5nIiBzaXplPSIxMDAiIC8+CgkJCTxDb2x1bW4gaWQ9IlBBR0VfTk8iIHR5cGU9ImludCIgc2l6ZT0iMTAiIC8+CgkJCTxDb2x1bW4gaWQ9IlBBR0VfU0laRSIgdHlwZT0iaW50IiBzaXplPSIxMCIgLz4KCQkJPENvbHVtbiBpZD0iUkVBRF9BTEwiIHR5cGU9InN0cmluZyIgc2l6ZT0iMSIgLz4KCQkJPENvbHVtbiBpZD0iRVhFQ19UWVBFIiB0eXBlPSJzdHJpbmciIHNpemU9IjIiIC8+CgkJCTxDb2x1bW4gaWQ9IkVYRUMiIHR5cGU9InN0cmluZyIgc2l6ZT0iMSIgLz4KCQkJPENvbHVtbiBpZD0iRkFJTCIgdHlwZT0ic3RyaW5nIiBzaXplPSIxIiAvPgoJCQk8Q29sdW1uIGlkPSJGQUlMX01TRyIgdHlwZT0ic3RyaW5nIiBzaXplPSIyMDAiIC8+CgkJCTxDb2x1bW4gaWQ9IkVYRUNfQ05UIiB0eXBlPSJpbnQiIHNpemU9IjEiIC8+CgkJCTxDb2x1bW4gaWQ9Ik1TRyIgdHlwZT0ic3RyaW5nIiBzaXplPSIyMDAiIC8+CgkJPC9Db2x1bW5JbmZvPgoJCTxSb3dzPgoJCQk8Um93PgoJCQkJPENvbCBpZD0iVFlQRSI+TjwvQ29sPgoJCQkJPENvbCBpZD0iU1FMX0lEIj5rbnVtb2JpbGUvY29tOkNPTTAyPC9Db2w+CgkJCQk8Q29sIGlkPSJLRVlfU1FMX0lEIiAvPgoJCQkJPENvbCBpZD0iS0VZX0lOQ1JFTUVOVCI+MDwvQ29sPgoJCQkJPENvbCBpZD0iQ0FMTEJBQ0tfU1FMX0lEIiAvPgoJCQkJPENvbCBpZD0iSU5TRVJUX1NRTF9JRCIgLz4KCQkJCTxDb2wgaWQ9IlVQREFURV9TUUxfSUQiIC8+CgkJCQk8Q29sIGlkPSJERUxFVEVfU1FMX0lEIiAvPgoJCQkJPENvbCBpZD0iU0FWRV9GTEFHX0NPTFVNTiI+ZHNfbGlzdDwvQ29sPgoJCQkJPENvbCBpZD0iS0VZX1pFUk9fTEVOIj4wPC9Db2w+CgkJCQk8Q29sIGlkPSJCSVpfTkFNRSIgLz4KCQkJCTxDb2wgaWQ9IlBBR0VfTk8iIC8+CgkJCQk8Q29sIGlkPSJQQUdFX1NJWkUiIC8+CgkJCQk8Q29sIGlkPSJFWEVDX1RZUEUiPkI8L0NvbD4KCQkJCTxDb2wgaWQ9IkVYRUNfQ05UIiAvPgoJCQk8L1Jvdz4KCQk8L1Jvd3M+Cgk8L0RhdGFzZXQ+CjwvUm9vdD4=",0)));
            //myCookies = new PersistentCookieStore();
            //httpClient.setCookieStore();


            httpClient.get("https://m.kangwon.ac.kr/knu/KNUMobile_v13/", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    XMLParser.HeaderToString(headers);
                    XMLParser.bodyToString(responseBody);


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }});

            }catch(UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }




    }






}
