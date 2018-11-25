package com.kangwon.a356.kangwonunivapp.dataprocess;


import android.util.Xml;

import org.jsoup.parser.XmlTreeBuilder;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;

/**
 * 받아온 HTML을 파싱해주는 역할을 한다.
 * @author 노지현
 */

public class XMLParser {

    public static String makeMessage(){
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


            xmlSerializer.startTag("", "parameter");
            xmlSerializer.attribute("", "id", "fsp_cmd");
            xmlSerializer.text("execute");
            xmlSerializer.endTag("", "parameter");

            xmlSerializer.startTag("", "parameter");
            xmlSerializer.attribute("", "id", "ID");
            xmlSerializer.text("201413381");
            xmlSerializer.endTag("", "parameter");


            xmlSerializer.startTag("", "parameter");
            xmlSerializer.attribute("", "id", "PW");
            xmlSerializer.text("29899");
            xmlSerializer.endTag("", "parameter");

            xmlSerializer.endTag("", "Parameters");

            xmlSerializer.endTag("", "Root");//end tag <file>

            xmlSerializer.endDocument();


        }catch(IOException e)
        {
            e.printStackTrace();
        }

        return writer.toString();

    }



}
