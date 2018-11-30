package com.kangwon.a356.kangwonunivapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


        Mon9.setOnClickListener(mClickListener);
        GridLayout gridLayout = (GridLayout)getView().findViewById(R.id.gridLayout);

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



        Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            RelativeLayout Sliding = (RelativeLayout)getView().findViewById(R.id.Sliding) ;
            Sliding.callOnClick();

        }
    };
}



