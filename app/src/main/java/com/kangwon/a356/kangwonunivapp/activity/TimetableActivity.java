package com.kangwon.a356.kangwonunivapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
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
public class TimetableActivity extends Fragment {
/*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.timetable_layout,container,false);
       return view;

    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        //시간표에 있는 모든 버튼 선언
        Button Mon9 = (Button)getView().findViewById(R.id.Mon9);
        Button Tue9 = (Button)getView().findViewById(R.id.Tue9);
        Button Wed9 = (Button)getView().findViewById(R.id.Wed9);
        Button Thr9 = (Button)getView().findViewById(R.id.Thu9);
        Button Fri9 = (Button)getView().findViewById(R.id.Fri9);

        Button Mon10 = (Button)getView().findViewById(R.id.Mon10);
        Button Tue10 = (Button)getView().findViewById(R.id.Tue10);
        Button Wed10 = (Button)getView().findViewById(R.id.Wed10);
        Button Thr10 = (Button)getView().findViewById(R.id.Thu10);
        Button Fri10 = (Button)getView().findViewById(R.id.Fri10);

        Button Mon11 = (Button)getView().findViewById(R.id.Mon11);
        Button Tue11 = (Button)getView().findViewById(R.id.Tue11);
        Button Wed11 = (Button)getView().findViewById(R.id.Wed11);
        Button Thr11 = (Button)getView().findViewById(R.id.Thu11);
        Button Fri11 = (Button)getView().findViewById(R.id.Fri11);

        Button Mon12 = (Button)getView().findViewById(R.id.Mon12);
        Button Tue12 = (Button)getView().findViewById(R.id.Tue12);
        Button Wed12 = (Button)getView().findViewById(R.id.Wed12);
        Button Thr12 = (Button)getView().findViewById(R.id.Thu12);
        Button Fri12 = (Button)getView().findViewById(R.id.Fri12);

        Button Mon13 = (Button)getView().findViewById(R.id.Mon13);
        Button Tue13 = (Button)getView().findViewById(R.id.Tue13);
        Button Wed13 = (Button)getView().findViewById(R.id.Wed13);
        Button Thr13 = (Button)getView().findViewById(R.id.Thu13);
        Button Fri13 = (Button)getView().findViewById(R.id.Fri13);

        Button Mon14 = (Button)getView().findViewById(R.id.Mon14);
        Button Tue14 = (Button)getView().findViewById(R.id.Tue14);
        Button Wed14 = (Button)getView().findViewById(R.id.Wed14);
        Button Thr14 = (Button)getView().findViewById(R.id.Thu14);
        Button Fri14 = (Button)getView().findViewById(R.id.Fri14);

        Button Mon15 = (Button)getView().findViewById(R.id.Mon15);
        Button Tue15 = (Button)getView().findViewById(R.id.Tue15);
        Button Wed15 = (Button)getView().findViewById(R.id.Wed15);
        Button Thr15 = (Button)getView().findViewById(R.id.Thu15);
        Button Fri15 = (Button)getView().findViewById(R.id.Fri15);

        Button Mon16 = (Button)getView().findViewById(R.id.Mon16);
        Button Tue16 = (Button)getView().findViewById(R.id.Tue16);
        Button Wed16 = (Button)getView().findViewById(R.id.Wed16);
        Button Thr16 = (Button)getView().findViewById(R.id.Thu16);
        Button Fri16 = (Button)getView().findViewById(R.id.Fri16);

        Button Mon17 = (Button)getView().findViewById(R.id.Mon17);
        Button Tue17 = (Button)getView().findViewById(R.id.Tue17);
        Button Wed17 = (Button)getView().findViewById(R.id.Wed17);
        Button Thr17 = (Button)getView().findViewById(R.id.Thu17);
        Button Fri17 = (Button)getView().findViewById(R.id.Fri17);

        Button Mon18 = (Button)getView().findViewById(R.id.Mon18);
        Button Tue18 = (Button)getView().findViewById(R.id.Tue18);
        Button Wed18 = (Button)getView().findViewById(R.id.Wed18);
        Button Thr18 = (Button)getView().findViewById(R.id.Thu18);
        Button Fri18 = (Button)getView().findViewById(R.id.Fri18);


        //시간표 작성
        Mon9.setVisibility(View.VISIBLE);
        Mon9.setText("소프트웨어 공학");
        Mon9.setBackgroundColor(Color.rgb(11,22,33));
        Mon9.setOnClickListener(mClickListener);

        Mon10.setVisibility(View.VISIBLE);
        Mon10.setText("네트워크");
        Mon10.setBackgroundColor(Color.rgb(44,55,66));
        Mon10.setOnClickListener(mClickListener);

        Mon14.setVisibility(View.VISIBLE);
        Mon14.setText("PL");
        Mon14.setBackgroundColor(Color.rgb(77,88,99));
        Mon14.setOnClickListener(mClickListener);
        Mon15.setVisibility(View.VISIBLE);
        Mon15.setText("PL");
        Mon15.setBackgroundColor(Color.rgb(77,88,99));
        Mon15.setOnClickListener(mClickListener);

        Tue10.setVisibility(View.VISIBLE);
        Tue10.setText("데이터베이스");
        Tue10.setBackgroundColor(Color.rgb(99,88,77));
        Tue10.setOnClickListener(mClickListener);
        Tue11.setVisibility(View.VISIBLE);
        Tue11.setText("데이터베이스");
        Tue11.setBackgroundColor(Color.rgb(99,88,77));
        Tue11.setOnClickListener(mClickListener);

        Tue14.setVisibility(View.VISIBLE);
        Tue14.setText("컴퓨터시스템공학");
        Tue14.setBackgroundColor(Color.rgb(66,55,44));
        Tue14.setOnClickListener(mClickListener);
        Tue15.setVisibility(View.VISIBLE);
        Tue15.setText("컴퓨터시스템공학");
        Tue15.setBackgroundColor(Color.rgb(66,55,44));
        Tue15.setOnClickListener(mClickListener);

        Wed13.setVisibility(View.VISIBLE);
        Wed13.setText("데이터베이스");
        Wed13.setBackgroundColor(Color.rgb(99,88,77));
        Wed13.setOnClickListener(mClickListener);

        Wed16.setVisibility(View.VISIBLE);
        Wed16.setText("컴퓨터시스템공학");
        Wed16.setBackgroundColor(Color.rgb(66,55,44));
        Wed16.setOnClickListener(mClickListener);

        Wed17.setVisibility(View.VISIBLE);
        Wed17.setText("직업선택과 꿈설계");
        Wed17.setBackgroundColor(Color.rgb(33,22,11));
        Wed17.setOnClickListener(mClickListener);

        Thr9.setVisibility(View.VISIBLE);
        Thr9.setText("소프트웨어 공학");
        Thr9.setBackgroundColor(Color.rgb(11,22,33));
        Thr9.setOnClickListener(mClickListener);

        Thr10.setVisibility(View.VISIBLE);
        Thr10.setText("네트워크");
        Thr10.setBackgroundColor(Color.rgb(44,55,66));
        Thr10.setOnClickListener(mClickListener);

        Thr14.setVisibility(View.VISIBLE);
        Thr14.setText("PL");
        Thr14.setBackgroundColor(Color.rgb(77,88,99));
        Thr14.setOnClickListener(mClickListener);
        Thr15.setVisibility(View.VISIBLE);
        Thr15.setText("PL");
        Thr15.setBackgroundColor(Color.rgb(77,88,99));
        Thr15.setOnClickListener(mClickListener);


/*
        final TabBar tabBar =(TabBar)getView().findViewById(R.id.tabbar);

        tabBar.addLayerInfo(0, 3, new String[] {"home", "favorite", "setting"}, null);
        tabBar.addLayerInfo(0, 3, new String[] {"need", "bad", "set"}, null);
        tabBar.editListener(0, new View.OnClickListener[]{new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MainActivity.class);
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
        tabBar.init(); */
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



        Button.OnClickListener mClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView Course = getView().findViewById(R.id.course);
            Button btn = getView().findViewById(v.getId());
            Course.setText(btn.getText().toString());
            RelativeLayout Sliding = (RelativeLayout)getView().findViewById(R.id.Sliding) ;
            Sliding.callOnClick();

        }
    };
}



