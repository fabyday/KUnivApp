package com.kangwon.a356.kangwonunivapp.database;


import com.kangwon.a356.kangwonunivapp.database.dataadapter.MessageAdapter;
import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 시간표에 사용될 강의에 대한 정보이다.
 * 실제 화면에 나타낼 정보에 대한 입력값을 가지는 클래스이다.
 *
 * @author 노지현
 * @see TimeTableInfo
 */
public class ClassInfo {

    public static final String CLASSNAME = "classname";
    public static final String INSTRUCTOR = "instructor";

    private int num;
    private String className; // 강의 이름
    private String instructor;
    private ArrayList<TimeSpaceInfo> timeSpaceInfo;
    private Message messageListener;


    public ClassInfo(String className, String instructor, int number) {
        this.className = className;
        this.instructor = instructor;
        timeSpaceInfo = new ArrayList<>();
        this.num = number;
    }

    public void addTimeSpaceInfo(TimeSpaceInfo info) {
        timeSpaceInfo.add(info);
    }

    public TimeSpaceInfo[] getTimeInfo() {
        return timeSpaceInfo.toArray(new TimeSpaceInfo[0]);
    }

    public String getClassName() {
        return className;
    }

    public String getInstructor() {
        return instructor;
    }


    /**
     * 오버라이딩한 함수로, 객체가 존재하는지를 찾을 때 사용. 이 객체를 사용하기 위해서는 어댑터를 등록해주어야 한다.
     * 만일 존재할 경우, 등록된 어댑터에 객체가 존재하는 리스트의 인덱스를 반환한다.
     *
     * @param obj ClassInfo 타입을 넣어주어야 한다.
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClassInfo) {
            if (((ClassInfo) obj).className == this.className)
                if (((ClassInfo) obj).instructor == this.instructor) {
                    LinkedHashMap<String, String> msg = new LinkedHashMap();
                    msg.put("type", Integer.toString(num));
                    if (messageListener != null)
                        messageListener.receive(new MessageObject(msg));
                    return true;
                }
        }
        return false;
    }


    public void add(Message msg) {
        messageListener = msg;
    }
}
