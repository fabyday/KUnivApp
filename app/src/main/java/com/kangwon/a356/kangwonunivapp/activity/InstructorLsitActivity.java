package com.kangwon.a356.kangwonunivapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.kangwon.a356.kangwonunivapp.R;
import com.kangwon.a356.kangwonunivapp.activity.commonactivity.MessageListenable;
import com.kangwon.a356.kangwonunivapp.dataprocess.database.ClassInfo;
import com.kangwon.a356.kangwonunivapp.dataprocess.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.dataprocess.database.TimeTableInfo;
import com.kangwon.a356.kangwonunivapp.dataprocess.database.layoutdataset.ListViewAdapter;
import com.kangwon.a356.kangwonunivapp.dataprocess.ProcessManager;
import com.kangwon.a356.kangwonunivapp.dataprocess.network.NetworkExecuteMessage;

import java.util.LinkedHashMap;

/**
 * res/layout/instructorlist_layout.xml 을 다룬다.
 * 교수가 강의를 생성하고 생성한 강의를 리스트뷰 형식으로 보여주고 강의를 삭제할 수 도 있다.
 */

public class InstructorLsitActivity extends Fragment implements MessageListenable {

    public static final int INSTRUCTOR_LIST = 4;


    ListView iListView;
    ListViewAdapter adapter;


    TimeTableInfo timetableInfo;
    ClassInfo[] classInfo;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.instructorlist_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button AddBtn = getView().findViewById(R.id.AddCourse); // 강의 추가 버튼
        iListView = getView().findViewById(R.id.InstructorListView);  // instructorlist_layout.xml의 리스트뷰
        final View header = getLayoutInflater().inflate(R.layout.listview_header, null, false);
        iListView.addHeaderView(header, null, false);
        adapter = new ListViewAdapter();
        iListView.setAdapter(adapter);


        ((MainActivity) getActivity()).add(this);
        ((MainActivity) getActivity()).processManager.updateRequest(MessageObject.INSTRUCTOR_TIME_TABLE_TYPE, ((MainActivity) getActivity()).handler);


//강의 추가 버튼 동작
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, new InstructorDialogActivity());
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

//리스트 아이템 롱 클릭시
        iListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int pos = position - 1;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // 강의 삭제 Dialog 생성
                builder.setTitle("강의 삭제")
                        .setMessage("강의를 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //삭제 메시지 전달
                                LinkedHashMap<String, String> msg = new LinkedHashMap<>();
                                msg.put(MessageObject.TYPE, MessageObject.DEL_LECTURE);
                                msg.put(ClassInfo.CLASSNAME, classInfo[pos].getClassName());
                                ProcessManager.getInstance().commitRequest(msg, ((MainActivity) getActivity()).handler);
                                /*adapter.removeItem(position);
                                adapter.notifyDataSetChanged();*/
                            }
                        })
                        .setNegativeButton("취소", null)
                        .create()
                        .show();
                return true;
            }
        });

//리스트 아이템 클릭시
        iListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 교수용 강의 출석인증 화면으로
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frameLayout, new InstructorAttendanceActivity());
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
    }


    @Override
    public void update(MessageObject msg) {
        Object temp = msg.getProcessedData();
        if (temp instanceof NetworkExecuteMessage) {
            try {
                classInfo = timetableInfo.getClassInfo();
                adapter.addItem(classInfo);
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), ((NetworkExecuteMessage) temp).getMessage(), Toast.LENGTH_SHORT).show();
            }catch(Exception e)
            {

            }
        } else {
            timetableInfo = (TimeTableInfo) temp;
            classInfo = timetableInfo.getClassInfo();
            adapter.addItem(classInfo);
            adapter.notifyDataSetChanged();
        }
    }
}

