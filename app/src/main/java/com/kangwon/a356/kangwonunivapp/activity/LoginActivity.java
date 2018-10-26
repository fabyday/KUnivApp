package com.kangwon.a356.kangwonunivapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.kangwon.a356.kangwonunivapp.R;

/**
 * @author 노지현
 * @version 1
 * 이 클래스는 로그인을 담당하는 클래스이며
 * res/layout/login_layout.xml의 레이아웃들을 사용한다.
 */



public class LoginActivity extends AppCompatActivity {

    Button menubutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        ActivityTools.makeFullScreen(this);



    }
}
