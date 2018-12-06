package com.kangwon.a356.kangwonunivapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.kangwon.a356.kangwonunivapp.R;
import com.kangwon.a356.kangwonunivapp.activity.commonactivity.TabBar;

/**
 * @author 노지현
 * @version 1
 * 이 클래스는 메인 화면을 담당하는 activity이다.
 * res/layout/activity_main.xml을 사용한다.
 */
public class MainActivity extends AppCompatActivity {


    FragmentManager fmng;
    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityTools.makeFullScreen(this);


///////////////////////테스트용/////////////////////////////////////

        ImageButton button1 = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton button2 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton button3 = (ImageButton) findViewById(R.id.imageButton3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fmng = getSupportFragmentManager();
                transaction =fmng.beginTransaction();
                transaction.add(R.id.frameLayout, new TimetableActivity());
                transaction.addToBackStack(null);
                transaction.commit();
               /* Intent i = new Intent(MainActivity.this, TimetableActivity.class);
                startActivity(i);*/
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fmng = getSupportFragmentManager();
                transaction =fmng.beginTransaction();
                transaction.add(R.id.frameLayout, new StudentListActivity());
                transaction.addToBackStack(null);
                transaction.commit();
                /*Intent i = new Intent(MainActivity.this, AttendanceActivity.class);
                startActivity(i);*/
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fmng = getSupportFragmentManager();
                transaction =fmng.beginTransaction();
                transaction.add(R.id.frameLayout, new InstructorLsitActivity());
                transaction.addToBackStack(null);
                transaction.commit();
               /* Intent i = new Intent(MainActivity.this, TimetableActivity.class);
                startActivity(i);*/
            }
        });
/////////////////////////////////////////////////


        final TabBar tabBar = (TabBar) findViewById(R.id.tabbar);

        tabBar.addLayerInfo(0, 3, new String[]{"홈", "즐겨찾기", "로그아웃"}, null);
        tabBar.addLayerInfo(0, 3, new String[]{"empty", "empty", "empty"}, null);
        tabBar.editListener(0, new View.OnClickListener[]{new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fmng.popBackStack();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tabBar.getChildVisivility(1) == View.GONE)
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

    //뒤로 가기 버튼 기능 제거
    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this, "뒤로 가기 버튼 사용 불가", Toast.LENGTH_SHORT).show();
    }

}
