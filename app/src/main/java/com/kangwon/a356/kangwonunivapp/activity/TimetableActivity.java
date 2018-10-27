package com.kangwon.a356.kangwonunivapp.activity;
import android.graphics.Color;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.kangwon.a356.kangwonunivapp.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.Calendar;

/**
 * @author 노지현
 * @version 1
 * 이 클래스는 로그인을 담당하는 클래스이며
 * res/layout/timetable_layout.xml의 레이아웃들을 다룬다.
 */
public class TimetableActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_layout);
        setTitle("Time Table"); //set title

        Button M9 = (Button)findViewById(R.id.M9);
        M9.setOnClickListener(mClickListener);


    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            RelativeLayout Sliding = (RelativeLayout) findViewById(R.id.Sliding) ;
            Sliding.callOnClick();

        }
    };

}



