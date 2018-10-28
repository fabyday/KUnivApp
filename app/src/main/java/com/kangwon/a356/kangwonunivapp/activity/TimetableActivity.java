package com.kangwon.a356.kangwonunivapp.activity;
import android.content.Intent;
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
import com.kangwon.a356.kangwonunivapp.activity.commonactivity.TabBar;
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

        final TabBar tabBar =(TabBar)findViewById(R.id.tabbar);

        tabBar.addLayerInfo(0, 3, new String[] {"home", "favorite", "setting"}, null);
        tabBar.addLayerInfo(0, 3, new String[] {"need", "bad", "set"}, null);
        tabBar.editListener(0, new View.OnClickListener[]{new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TimetableActivity.this, MainActivity.class);
                startActivity(i);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tabBar.getChildVisivility(1) == View.GONE)
                    tabBar.setChildVisivility(1, View.VISIBLE);
                else
                    tabBar.setChildVisivility(1, View.GONE);
            }
        }
                ,null});
        tabBar.init();
    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            RelativeLayout Sliding = (RelativeLayout) findViewById(R.id.Sliding) ;
            Sliding.callOnClick();

        }
    };

}



