package com.kangwon.a356.kangwonunivapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kangwon.a356.kangwonunivapp.R;

/**
 * @author 노지현
 * @version 1
 * 이 클래스는 출석체크와 출석 체크와 출석 리스트를 보여주는 액티비티이다.
 * res/layout/attendance_layout.xml을 사용한다.
 */
public class attendanceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_layout);
        ActivityTools.makeFullScreen(this);

    }
}
