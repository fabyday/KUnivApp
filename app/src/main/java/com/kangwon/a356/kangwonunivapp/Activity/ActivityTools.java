package com.kangwon.a356.kangwonunivapp.activity;


import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author 노지현
 * @version 1
 * 이 클래스는 activity에 관련된 초기화나 다른 공통적인 수행을
 * 도와주는 정적메소드로 이루어진 클래스이다.
 */



public class ActivityTools {

    /**
     * @author 노지현
     * @version 1
     * @param activity 사용하는 자신의 activity를 넣어준다.
     */

    public static void makeFullScreen(AppCompatActivity activity)
    {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


}
