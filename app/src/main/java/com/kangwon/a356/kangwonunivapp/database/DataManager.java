package com.kangwon.a356.kangwonunivapp.database;


import android.util.Log;

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


                        Log.i("DataThread", "스레드 시작");

                        while (!dataQueue.isEmpty()) {
                            Message sender = null;
                            Log.i("DataThread", " 시작 전 큐 사이즈 : " + dataQueue.size());
                            MessageObject msg = (MessageObject) dataQueue.poll();
                            Log.i("DataThread", " 시작 후 큐 사이즈 : " + dataQueue.size());
                            String type = msg.getType();
                            switch (type) {
                                case MessageObject.LOGIN_TYPE:
                                    userInfo.receive(msg);
                                    sender = userInfo;
                                    break;
                                case MessageObject.SIGNIN_TYPE:
                                    break;
                                case MessageObject.STUDENT_TIMETABLE_TYPE:
                                    timeTableInfo[AS_STUDENT].receive(msg);
                                    sender = timeTableInfo[AS_STUDENT];
                                    break;
                                case MessageObject.JOIN_LECTURE:

                                    break;
                                case MessageObject.ALL_LIST:

                                    break;
                                case MessageObject.CHECK_ATTANDANCE:

                                    break;
                                case MessageObject.INSTRUCTOR_TIME_TABLE_TYPE:
                                    timeTableInfo[AS_INSTRUCTOR].receive(msg);
                                    sender = timeTableInfo[AS_INSTRUCTOR];
                                    break;
                                case MessageObject.OPEN_ATTANDANCE:

                                    break;
                                case MessageObject.CLOSE_ATTANDANCE:

                                    break;
                                case MessageObject.OPEN_LECTURE:


                                    break;
                                case MessageObject.DEL_LECTURE:


                                    break;
                                default:

                            }
                                    try{
                                        callMessage(sender.makeQueryMessage(msg));
                                        Log.i("DataThread", " 콜백");
                                    }catch(Exception e)
                                    {
                                        e.printStackTrace();
                                    }


                        }

                        try {
                            synchronized (dataQueue) {
                                Log.i("DataThread", "스레드 대기 상태");
                                dataQueue.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        dThread.setName("DataThread");
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
            Log.i("ProcessThread", "데이터매니저에게 요청");
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
        Log.i("ProcessThread", "프로세스에 처리 완료 요청");
        while (iter.hasNext()) {
            ((Message) iter.next()).receive(msg);
        }

    }


}



