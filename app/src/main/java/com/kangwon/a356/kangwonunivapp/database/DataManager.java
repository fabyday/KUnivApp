package com.kangwon.a356.kangwonunivapp.database;


import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;
import com.kangwon.a356.kangwonunivapp.dataprocess.AbstractManager;

import java.util.Iterator;
import java.util.Queue;

/**
 * database 패키지 전반의 데이터 관리를 해준다.
 *
 * @author 노지현
 */

public class DataManager extends AbstractManager {

    private static DataManager dataManager = null;
    private TimeTableInfo[] timeTableInfo;
    private UserInfo userInfo;
    private Queue dataQueue;
    public static final int AS_STUDENT = 0;
    public static final int AS_INSTRUCTOR = 1;
    public static final int NUMBER_OF_TABLE = 2;

    Thread dThread = null;

    private DataManager(Queue queue) {
        timeTableInfo = new TimeTableInfo[NUMBER_OF_TABLE];
        userInfo = new UserInfo();
        this.dataQueue = queue;
        timeTableInfo[AS_STUDENT] = new TimeTableInfo(MessageObject.STUDENT_TIMETABLE_TYPE);
        timeTableInfo[AS_INSTRUCTOR] = new TimeTableInfo(MessageObject.INSTRUCTOR_TIME_TABLE_TYPE);

        timeTableInfo[AS_STUDENT].setUserInfo(userInfo);
        timeTableInfo[AS_INSTRUCTOR].setUserInfo(userInfo);


        if (dThread == null)
            dThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true) {


                        while (!dataQueue.isEmpty()) {
                            Message sender = null;
                            MessageObject msg = (MessageObject) dataQueue.poll();
                            if (msg.equals(MessageObject.LOGIN_TYPE)) {
                                userInfo.receive(msg);
                                sender = userInfo;
                            } else if (msg.equals(MessageObject.STUDENT_TIMETABLE_TYPE)) {
                                timeTableInfo[AS_STUDENT].receive(msg);
                                sender = timeTableInfo[AS_STUDENT];
                            } else if (msg.equals(MessageObject.INSTRUCTOR_TIME_TABLE_TYPE)) {
                                timeTableInfo[AS_INSTRUCTOR].receive(msg);
                                sender = timeTableInfo[AS_INSTRUCTOR];
                            }


                            if (msg.getRequsetStatus() == MessageObject.REQUEST_FOR_ALL) {//완료 됨을 프로세스 매니저에게 알림
                                if (sender != null)
                                    callMessage(sender.makeQueryMessage());
                                else
                                    callMessage(null);

                            }
                        }
                        try {
                            synchronized (dataQueue) {
                                dataQueue.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        dThread.start();

    }


    public static DataManager getInstance(Queue queue) {
        if (dataManager == null)
            return (dataManager = new DataManager(queue));
        return dataManager;
    }


    /**
     * 스레드가 최초로 생성되고 수행되는 함수. 없을 경우만 쓰레드가 생성된다.
     */
    public void inputMessage() {


        synchronized (dataQueue) {
            dataQueue.notify();
        }

    }


    /**
     * 리스너를 등록한 상위 객체에게 작업이 완료되면 메시지를 전달한다.
     *
     * @param msg 초기화가 완료된 다른 데이터들로 부터 추가적인 작업들의 메시지.(ex. 로그인 준비가 끝났으니 메시지를 보내라)
     */
    @Override
    public void callMessage(MessageObject msg) {
        Iterator iter = super.getIterator();
        while (iter.hasNext()) {
            if (msg == null)
                ((Message) iter.next()).receive();
            else
                ((Message) iter.next()).receive(msg);
        }

    }


}



