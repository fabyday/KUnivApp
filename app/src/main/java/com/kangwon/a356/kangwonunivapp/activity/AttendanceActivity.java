package com.kangwon.a356.kangwonunivapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kangwon.a356.kangwonunivapp.R;
import com.kangwon.a356.kangwonunivapp.activity.commonactivity.TabBar;

import java.sql.Time;

/**
 * @author 노지현
 * @version 1
 * 이 클래스는 출석체크와 출석 체크와 출석 리스트를 보여주는 액티비티이다.
 * res/layout/attendance_layout.xml을 사용한다.
 */
public class AttendanceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_layout);
        ActivityTools.makeFullScreen(this);
        final TabBar tabBar = (TabBar)findViewById(R.id.tabbar);
        tabBar.addLayerInfo(0, 3, new String[] {"home", "favorite", "setting"}, null);
        tabBar.addLayerInfo(0, 3, new String[] {"need", "bad", "set"}, null);
        tabBar.editListener(0, new View.OnClickListener[]{new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AttendanceActivity.this, MainActivity.class);
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
}
