package com.kangwon.a356.kangwonunivapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kangwon.a356.kangwonunivapp.R;

public class InstructorAttendanceActivity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //ActivityTools.makeFullScreen(this);
        View view =  inflater.inflate(R.layout.instructorattendance_layout,container,false);
        return view;
    }
    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
