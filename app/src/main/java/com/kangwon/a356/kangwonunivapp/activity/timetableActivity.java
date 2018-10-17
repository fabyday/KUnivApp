package com.kangwon.a356.kangwonunivapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kangwon.a356.kangwonunivapp.R;


/**
 * @author 노지현
 * @version 1
 * 이 클래스는 로그인을 담당하는 클래스이며
 * res/layout/timetable_layout.xml의 레이아웃들을 다룬다.
 */
public class timetableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_layout);
        ActivityTools.makeFullScreen(this);

    }


}
