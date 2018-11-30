package com.kangwon.a356.kangwonunivapp.dataprocess;


import android.content.Context;
import android.provider.ContactsContract;

import com.kangwon.a356.kangwonunivapp.database.DataManager;
import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;
import com.kangwon.a356.kangwonunivapp.network.NetworkManager;


/**
 * 이 클래스는 전체적인 백그라운드 작업 진행을 총괄한다.
 * 앱에 단 한개의 클래스만이 존재할 수 있는 싱글톤 스타일의 클래스이다.
 * @version 1
 */
public class ProcessManager {
    private static ProcessManager processManager=null;

    private DataManager dataManager;
    private NetworkManager networkManager;
    private Message[] msg;



    private static Context context;

    private ProcessManager()
    {
        dataManager = DataManager.getInstance();
        networkManager = NetworkManager.getInstance();
    }


    /**
     * 앱에 유일하게 존재하는 ProcessManger Instance를 반환한다.
     * @return ProcessManager 객체 반환.
     */
    public static ProcessManager getInstance()
    {
        if(processManager == null)
            processManager= new ProcessManager();
        return processManager;
    }



    public void connect()
    {
        networkManager.connect(msg);
    }




}
