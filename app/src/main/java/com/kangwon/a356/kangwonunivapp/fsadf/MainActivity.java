package com.kangwon.a356.kangwonunivapp.fsadf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kangwon.a356.kangwonunivapp.R;

/**
 * @author 노지현
 * @version 1
 * 이 클래스는 메인 화면을 담당하는 activity이다.
 * res/layout/activity_main.xml을 사용한다.
 */
public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityTools.makeFullScreen(this);
    }
}
