package com.kangwon.a356.kangwonunivapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.kangwon.a356.kangwonunivapp.R;
import com.kangwon.a356.kangwonunivapp.activity.commonactivity.TabBar;

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

///////////////////////테스트용/////////////////////////////////////

        ImageButton button1 = (ImageButton) findViewById(R.id.imageButton);
        ImageButton button2  = (ImageButton) findViewById(R.id.imagebutton2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TimetableActivity.class);
                startActivity(i);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AttendanceActivity.class);
                startActivity(i);
            }
        });
/////////////////////////////////////////////////


        final TabBar tabBar = (TabBar)findViewById(R.id.tabbar);

        tabBar.addLayerInfo(0, 3, new String[] {"home", "favorite", "setting"}, null);
        tabBar.addLayerInfo(0, 3, new String[] {"empty", "empty", "empty"}, null);
        tabBar.editListener(0, new View.OnClickListener[]{null, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tabBar.getChildVisivility(1) == View.GONE)
                    tabBar.setChildVisivility(1, View.VISIBLE);
                else
                    tabBar.setChildVisivility(1, View.GONE);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               
            }
        }});
        tabBar.init();




    }






}
