package com.kangwon.a356.kangwonunivapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.kangwon.a356.kangwonunivapp.R;
import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.dataprocess.ProcessManager;

import java.util.ArrayList;

/**
 * 이 클래스는 res/layout/studentlsit_layout.xml 를 다룬다.
 * 학생들이 수강 신청한 강의가 리스트뷰 형식으로 나타난다.
 */

public class StudentListActivity extends Fragment {

    final Handler handler = new Handler(){//TODO-구현 필요
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.studentlsit_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView sListView = getView().findViewById(R.id.StudentListView); // studentlsit_layout.xml의 리스트뷰
        final ArrayList<String> list = new ArrayList<String>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.fix_simple_list, list);
        sListView.setAdapter(adapter);


        ProcessManager.getInstance().updateRequest(MessageObject.ALL_LIST, handler);
        // * 학생이 수강 신청한 강의가 list에 있어야 함

   
        sListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // 리스트뷰 아이템 클릭시 출결인증 화면으로
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AttendanceActivity()).commit();
            }
        });
    }
}
