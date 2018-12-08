package com.kangwon.a356.kangwonunivapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kangwon.a356.kangwonunivapp.R;
import com.kangwon.a356.kangwonunivapp.activity.commonactivity.MessageListenable;
import com.kangwon.a356.kangwonunivapp.database.ClassInfo;
import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.database.TimeSpaceInfo;
import com.kangwon.a356.kangwonunivapp.database.TimeTableInfo;
import com.kangwon.a356.kangwonunivapp.database.layoutdataset.ListViewAdapter;
import com.kangwon.a356.kangwonunivapp.dataprocess.ProcessManager;

import java.util.ArrayList;

/**
 * 이 클래스는 res/layout/studentlsit_layout.xml 를 다룬다.
 * 학생들이 수강 신청한 강의가 리스트뷰 형식으로 나타난다.
 */

public class StudentListActivity extends Fragment {


    ListView sListView;
    ListViewAdapter adapter;


    TimeTableInfo timetableInfo;
    ClassInfo[] classInfo;
    public static final int ALL_LIST = 2;


    MessageListenable callback = new MessageListenable() {
        @Override
        public void update(MessageObject msg) {


            timetableInfo = (TimeTableInfo) msg.getProcessedData();
            classInfo = timetableInfo.getClassInfo();
            adapter.addItem(timetableInfo.getClassInfo());
            for (int i = 0; i < classInfo.length; i++)
                System.out.print("메시지 내용은?!" + classInfo[i]);
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.studentlsit_layout, container, false);
        ((MainActivity) getActivity()).add(callback);
        System.out.println("리스너 등록 완료!");
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        sListView = getView().findViewById(R.id.StudentListView); // studentlsit_layout.xml의 리스트뷰
        final View header = getLayoutInflater().inflate(R.layout.listview_header, null, false);
        sListView.addHeaderView(header, null, false);
        adapter = new ListViewAdapter();
        sListView.setAdapter(adapter);
        System.out.println("리슨 준비 완료");


        // * 학생이 수강 신청한 강의가 list에 있어야 함


        sListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // 리스트뷰 아이템 클릭시 출결인증 화면으로
                int pos = position-1;
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  // 강의 추가 Dialog 생성
                ListView listView = new ListView(getActivity());

                System.out.println("postion" + position);
                TimeSpaceInfo[] timeSpaceInfos = classInfo[pos].getTimeInfo();

                ArrayList<String> infos = new ArrayList<>();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.fix_simple_list, infos);
                listView.setAdapter(adapter);
                String[] page = new String[3 + timeSpaceInfos.length];
                infos.add("[ 강의명 : " + classInfo[pos].getClassName() + " ]");
                infos.add("[ 강의자 : " + classInfo[pos].getInstructor() + " ]");
                infos.add("[ 기간 : " + classInfo[pos].getStartDate() + " ~ " + classInfo[position].getEndDate() + "]");
                for (int i = 0; i < timeSpaceInfos.length; i++) {

                    infos.add(timeSpaceInfos[i].toString());
                }

                builder.setView(listView);
                builder.setTitle("강의 정보");
                builder.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("취소", null);
                builder.create();
                builder.show();
            }
        });
    }


    @Override
    public void onDestroy() {
        System.out.println("파괴됩니다.");
        ((MainActivity) getActivity()).add(null);
        super.onDestroy();
    }
}
