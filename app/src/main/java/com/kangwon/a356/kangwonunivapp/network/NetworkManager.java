package com.kangwon.a356.kangwonunivapp.network;


import android.content.Context;

/**
 *
 * 네트워크 들에 대한 전반적인 관리 클래스
 * @author 노지현
 */
public class NetworkManager {

    private static NetworkManager nManager=null;
    HttpsConnectionHelper networkHelper;

    private NetworkManager() {
        networkHelper = new HttpsConnectionHelper();
    }

    public static NetworkManager getInstance()
    {
        if(nManager == null)
            return new NetworkManager();
        return nManager;
    }


    public void connect()
    {

             networkHelper.connect();

    }


}
