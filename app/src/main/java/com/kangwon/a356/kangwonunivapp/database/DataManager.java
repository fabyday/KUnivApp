package com.kangwon.a356.kangwonunivapp.database;

import com.kangwon.a356.kangwonunivapp.database.TimeTableInfo;


/**
 * database 패키지 전반의 데이터 관리를 해준다.
 * @author 노지현
 */

public class DataManager {

    private static DataManager dataManager = null;
    public TimeTableInfo timeTableInfo;
    public studentInfo stdInfo;

    private DataManager()
    {
        timeTableInfo = new TimeTableInfo();
        stdInfo = new studentInfo();
    }


    public static DataManager getInstance()
    {
        if(dataManager == null)
            return (dataManager=new DataManager());
        return dataManager;
    }












}
