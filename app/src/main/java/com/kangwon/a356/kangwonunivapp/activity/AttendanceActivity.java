package com.kangwon.a356.kangwonunivapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;

import com.kangwon.a356.kangwonunivapp.R;
import com.kangwon.a356.kangwonunivapp.activity.commonactivity.TabBar;

import java.sql.Time;

/**
 * @author 노지현
 * @version 1
 * 이 클래스는 출석체크와 출석 체크와 출석 리스트를 보여주는 액티비티이다.
 * res/layout/attendance_layout.xml을 사용한다.
 */

public class AttendanceActivity extends Fragment   {
/*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //ActivityTools.makeFullScreen(this);
        View view =  inflater.inflate(R.layout.attendance_layout,container,false);
        return view;
    }
    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {

    }
    /*
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        final TabBar tabBar = (TabBar)getView().findViewById(R.id.tabbar);
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
        tabBar.init();
    }*/
}
