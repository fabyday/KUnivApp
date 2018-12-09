package com.kangwon.a356.kangwonunivapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.kangwon.a356.kangwonunivapp.R;
import com.kangwon.a356.kangwonunivapp.activity.commonactivity.MessageListenable;
import com.kangwon.a356.kangwonunivapp.database.ClassInfo;
import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.database.TimeSpaceInfo;
import com.kangwon.a356.kangwonunivapp.database.TimeTableInfo;
import com.kangwon.a356.kangwonunivapp.database.layoutdataset.ListViewAdapter;
import com.kangwon.a356.kangwonunivapp.dataprocess.ProcessManager;
import com.kangwon.a356.kangwonunivapp.network.NetworkExecuteMessage;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 이 클래스는 res/layout/studentlsit_layout.xml 를 다룬다.
 * 학생들이 수강 신청한 강의가 리스트뷰 형식으로 나타난다.
 */

public class StudentListActivity extends Fragment implements MessageListenable {


    ListView sListView;
    ListViewAdapter adapter;


    TimeTableInfo timetableInfo;
    ClassInfo[] classInfo;
    public static final int ALL_LIST = 2;


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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        sListView = getView().findViewById(R.id.StudentListView); // studentlsit_layout.xml의 리스트뷰
        final View header = getLayoutInflater().inflate(R.layout.listview_header, null, false);
        sListView.addHeaderView(header, null, false);
        adapter = new ListViewAdapter();
        sListView.setAdapter(adapter);
        ((MainActivity) getActivity()).add(this);
        ((MainActivity) getActivity()).processManager.updateRequest(MessageObject.ALL_LIST, ((MainActivity) getActivity()).handler);


        // * 학생이 수강 신청한 강의가 list에 있어야 함


        sListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // 리스트뷰 아이템 클릭시 출결인증 화면으로
                final int pos = position - 1; //classname의 배열적 위치.
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  // 강의 추가 Dialog 생성
                ListView listView = new ListView(getActivity());
                TimeSpaceInfo[] timeSpaceInfos = classInfo[pos].getTimeInfo();
                ArrayList<String> infos = new ArrayList<>();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.fix_simple_list, infos);
                listView.setAdapter(adapter);

                infos.add("강의명 : " + classInfo[pos].getClassName());
                infos.add("강의자 : " + classInfo[pos].getInstructor());
                infos.add("기간 : " + classInfo[pos].getStartDate().toString() + " ~ " + classInfo[pos].getEndDate().toString());
                for (int i = 0; i < timeSpaceInfos.length; i++) {
                    infos.add(timeSpaceInfos[i].toString());
                }

                final ClassInfo[] sendClassInfo = classInfo; //final 처리를 해줘야 인식
                builder.setView(listView).setTitle("강의 정보").
                        setPositiveButton("추가", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LinkedHashMap<String, String> mapMsg = new LinkedHashMap<>();
                                mapMsg.put(MessageObject.TYPE, MessageObject.JOIN_LECTURE);
                                mapMsg.put(ClassInfo.CLASSNAME, sendClassInfo[pos].getClassName());
                                mapMsg.put(ClassInfo.INSTRUCTOR, sendClassInfo[pos].getInstructor());
                                ProcessManager.getInstance().commitRequest(mapMsg, ((MainActivity) getActivity()).handler);
                            }
                        }).setNegativeButton("취소", null).create().show();
            }
        });
    }


    @Override
    public void onDestroy() {
        System.out.println("파괴됩니다.");
        ((MainActivity) getActivity()).add(null);
        super.onDestroy();
    }


    @Override
    public void update(MessageObject msg) {
        Object temp = msg.getProcessedData();
        if (temp instanceof NetworkExecuteMessage) {
            Toast.makeText(getActivity(), ((NetworkExecuteMessage)temp).getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            timetableInfo = (TimeTableInfo) temp;
            classInfo = timetableInfo.getClassInfo();
            adapter.addItem(classInfo);
            adapter.notifyDataSetChanged();
        }
    }
}
