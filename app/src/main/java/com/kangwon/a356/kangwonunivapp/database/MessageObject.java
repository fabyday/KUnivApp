package com.kangwon.a356.kangwonunivapp.database;

import android.os.Handler;

import com.kangwon.a356.kangwonunivapp.dataprocess.JSONParser;
import com.kangwon.a356.kangwonunivapp.network.NetworkExecuteMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;


/**
 * 메시지 객체이다. 내부에서 사용할 메시지와 외부에서 사용할 메시지를 구분하지 않고 사용 가능하게 해준다.
 * 모든 객체들은 이 메시지 객체를 사용하여 통신한다.
 *
 * @see com.kangwon.a356.kangwonunivapp.database.datainterface.Message
 * @see com.kangwon.a356.kangwonunivapp.database.dataadapter.MessageAdapter
 */
public class MessageObject {

    public static final String TYPE = "type"; //DataManager가 데이터를 분배하는데 쓰임

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //DataManager -> NetworkManager : 각 데이터 객체들이 필요한 메시지의 요청을 위한 JSON 의 값이다.
    //NetworkManager -> DataManager : DataManager가 어떤 객체로 메시지를 전달하지를 정하는 값. type를 key로 가진다.
    public static final String LOGIN_TYPE = "login";
    public static final String SIGNIN_TYPE = "signin";

    ///학생용
    public static final String STUDENT_TIMETABLE_TYPE = "studenttimetable";
    public static final String CHECK_ATTANDANCE = "checkattandance";
    public static final String JOIN_LECTURE = "joinlecture";
    public static final String ALL_LIST = "alllist";
    public static final String STUDENT_ATTANDANCE_LIST = "attandancelist";
    //강사용
    public static final String INSTRUCTOR_TIME_TABLE_TYPE = "instructortimetable"; //시간표
    public static final String OPEN_ATTANDANCE = "openattandance"; //출석부 열기
    public static final String CLOSE_ATTANDANCE = "closeattandance"; //출석부 닫기
    public static final String OPEN_LECTURE = "openlecture"; //강의 생성
    public static final String DEL_LECTURE = "closelecture"; //강의 삭제
    public static final String INSTRUCTOR_ATTANDANCE_LIST = "instructorattandancelist";
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//////////////////////////////일단은 안쓴다.////////////////////////////////////////
    //유저가 실제 update() 등의 메소드 등을 통해 호출 할시에 사용된다.
    /*
    public static final String REQUEST_UPDATE_STUDNET_LIST = "studentlist";
    public static final String REQUEST_UPDATE_STUDNET_TABLE = "studenttimetable";
    public static final String REQUEST_UPDATE_INSTRUCTOR_LIST = "instructorlist";
    public static final String REQUEST_UPDATE_INSTRUCTOR_TABLE = "instructortimetable";
    */
///////////////////////////////////////////////////////////////////////////////////////////////

    //메시지가 어떤 곳으로 전달되어야 되는지 정의된다. MessageQueueType 에 사용
    public static final int DATA_MANAGER = 1; // 데이터 매니저에게 전달.
    public static final int NETWORK_MANAGER = 2; // 네트워크 매니저에게 전달
    public static final int PROCESS_MANAGER = 4; //사용자에게만 전달


    //사용자가 쿼리를 원하는가를 정하는 변수 requestStatus를 위해 정의되어 있다.
    //유저가 데이터 매니저에게, 데이터 매니저가 네트워크 매니저에게 요청시 사용
    public static final int NOT_REQUEST_QUERY = 1; // 아무런 것도 리퀘스트 하지 않는다.
    public static final int REQUEST_FOR_ALL = 2; // 모든 정보를 준다.
    public static final int JUST_REQUEST_HINT = 4; //NetworkExcuteMessage를 이용한 로그인과 같은 것은 단순하게 로그인이 잘 되었다만 알면 된다.(OK, ERROR 같은 단순한 정보 외의 어떠한 실질적 정보도 포함하지 않는다.)

    ////네트워크 매니저가 메시지를 유저와 데이터 매니저에 보낼때 사용한다.
    public static final int RESPONSE_HINT = 8;
    public static final int RESPONSE_FOR_REQUEST = 16;


    // 일종의 헤더 메시지
    private String type; //메시지에도 존재하나 ArrayList로 랩핑되어 있어 빠르게 메시지를 전달하기 위해 사용된다.
    private int MessageQueueType; //메시지가 가야하는 큐의 타입
    private int requsetStatus = 0; //requestStatus는 저장을 하고 데이터를 이용해서 쿼리문을 원한다는 유무이다. ProcessManager에서 쓰임.
    private NetworkExecuteMessage tag = null; //서버로의 질의 이후, 메시지를 보낼 때 질의가 잘 수행되었는지에 대한 정보를 담는다.

    private ArrayList<LinkedHashMap> message; // MAP 타입의 실질적인 메시지이며 MessageObject 객체에 감싸 보낸다.
    private Object processedData; //유저가 사용할 정보 객체. 필요에 따라 형 변환해서 사용
    private Handler handler;

    public MessageObject() {
    }

    public MessageObject(LinkedHashMap[] msg) {
        message = new ArrayList<>();
        for (int i = 0; i < msg.length; i++)
            message.add(msg[i]);
        type = (String) msg[0].get("type");
    }

    public MessageObject(ArrayList<LinkedHashMap> msg) {
        message = msg;
        type = (String) msg.get(0).get("type");

    }

    public MessageObject(JSONObject msg) {
        this(JSONParser.toArrayList(msg));
    }

    public MessageObject(JSONArray msg) {
        this(JSONParser.toArrayList(msg));
    }

    public MessageObject(LinkedHashMap msg) {
        message = new ArrayList<>();
        message.add(msg);
        type = (String) msg.get("type");
    }


    /**
     * @return 메시지 타입을 리턴한다.
     */
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setNEM(NetworkExecuteMessage tag) {
        this.tag = tag;
    }

    public NetworkExecuteMessage getNEM() {
        return tag;
    }

    /**
     * 메시지를 ArrayList를 반환해준다.
     *
     * @return
     */
    public ArrayList<LinkedHashMap> getMessage() {
        return message;
    }
    public void setMessage(ArrayList<LinkedHashMap> message){this.message = message;}

    public void setRequestStatus(int requsetStatus) {
        this.requsetStatus = requsetStatus;
    }

    public int getRequsetStatus() {
        return requsetStatus;
    }

    /**
     * 메시지를 GET 방식의 인자로 만들어준다.
     *
     * @return GET타입의 인자. URL에 붙여서 보낼 수 있다.
     */
    public String toGETMessage() {
        String getMsg = "/" + type + "?";
        LinkedHashMap msg = message.get(0);
        Iterator iter = msg.keySet().iterator();

        iter.next(); //첫 번째 거는 버린다. 이미 type에 존재하기 때문.
        while (iter.hasNext()) {
            String key = (String) iter.next();
            getMsg += key + "=" + msg.get(key) + "&";
        }

        return getMsg.substring(0, getMsg.length() - 1);
    }

    public int getMessageQueueType() {
        return MessageQueueType;
    }

    public void setMessageQueueType(int messageQueueType) {
        this.MessageQueueType = messageQueueType;
    }


    public void setProcessedData(Object processedData) {
        this.processedData = processedData;
    }

    public Object getProcessedData() {
        return processedData;
    }

    /**
     * 메시지가 어떤 타입의 메시지인지 확인해 준다.
     *
     * @param obj Message객체를 받을시 같은 타입인지 확인해준다.
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this.type.equals(obj))
            return true;
        return false;

    }


    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Handler getHandler() {
        return handler;
    }

}
