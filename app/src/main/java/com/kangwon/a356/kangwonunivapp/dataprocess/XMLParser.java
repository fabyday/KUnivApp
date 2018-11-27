package com.kangwon.a356.kangwonunivapp.dataprocess;


import android.util.Base64;
import android.util.Xml;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.XmlTreeBuilder;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * 받아온 HTML을 파싱해주는 역할을 한다.
 * @author 노지현
 */

public class XMLParser {




    public static String makeBodyMessage(LinkedHashMap data){



        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();


        try{
            xmlSerializer.setOutput(writer);

            //Start Document
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            //Open Tag <file>
                xmlSerializer.startTag("", "Root");
                xmlSerializer.attribute("", "xmlns", "http://www.tobesoft.com/platform/dataset");

                    xmlSerializer.startTag("", "Parameters");

                            xmlSerializer.startTag("", "parameter");
                            xmlSerializer.attribute("", "id", "fsp_action");
                            xmlSerializer.text("DefaultAction");
                        xmlSerializer.endTag("", "parameter");

                    xmlSerializer.endTag("", "Parameters");

                    xmlSerializer.startTag("", "Dataset");
                    xmlSerializer.attribute("", "id", "fsp_ds_cmd");

                        xmlSerializer.startTag("", "ColumnInfo");
                        //안에 데이터 넣어주는 부분 필요

                        xmlSerializer.endTag("", "ColumnInfo");



                        xmlSerializer.startTag("", "Rows");
                        //데이터 들어가야함.

                        xmlSerializer.endTag("", "Rows");
                    xmlSerializer.endTag("", "Dataset");
                xmlSerializer.endTag("", "Root");//end tag <file>

            xmlSerializer.endDocument();


        }catch(IOException e)
        {
            e.printStackTrace();
        }

        return writer.toString();

    }

    public void makeXml(XmlSerializer serializer, LinkedHashMap data)
    {
        Iterator<String> iterator = data.keySet().iterator();
        try {

            while (iterator.hasNext()) {
                String key = iterator.next();
                serializer.startTag("", "");
                serializer.attribute("", key, (String)data.get(key));

            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    public  static String[][] HeaderToString(Header[] headers){

        String[][] str=new String[headers.length][]; //헤더를 받아오기 위한 레그드 배열
        for(int i =0; i<headers.length; i++)
        {
            HeaderElement[] t =headers[i].getElements();
            str[i]=new String[t.length];
            System.out.println(headers[i]);
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


    public static String bodyStringJsoup(String string)
    {
        Document document = Jsoup.parse(string);
        Elements e = document.select("mainframe_childframe_form_divPOP_login_edt_IDInputElement");
        String inputValue = e.attr("value");
        return inputValue;

    }


    /**
     * LinkedHashMap에 넣은 순서대로 헤더값을 생성하느다.
     * @param data LinkedHashMap으로 이루어진 Header
     * @return
     */
    public static Header[] makeHeader(LinkedHashMap data)
    {
        Iterator<String> iterator= data.keySet().iterator();
        ArrayList<BasicHeader> headerArrayList = new ArrayList<>();

        while(iterator.hasNext())
        {
            String key=iterator.next();
            headerArrayList.add(new BasicHeader(key, (String)data.get(key)));
        }

        Header[] headers = headerArrayList.toArray(new Header[0]);

        headerAnalyze(headers);//분석기

        return headers;



    }


    public static void headerAnalyze(Header[] headers)
    {
        for(int i =0; i<headers.length; i++)
        {
            System.out.println(headers[i]);
        }
    }


}
