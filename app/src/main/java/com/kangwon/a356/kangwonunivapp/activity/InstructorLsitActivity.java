package com.kangwon.a356.kangwonunivapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.kangwon.a356.kangwonunivapp.R;

import java.util.ArrayList;

/**
 * res/layout/instructorlist_layout.xml 을 다룬다.
 * 교수가 강의를 생성하고 생성한 강의를 리스트뷰 형식으로 보여주고 강의를 삭제할 수 도 있다.
 */

public class InstructorLsitActivity extends Fragment {

    public Handler handler=  new Handler(){ //TODO
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
        View view = inflater.inflate(R.layout.instructorlist_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button AddBtn = getView().findViewById(R.id.AddCourse); // 강의 추가 버튼
        final ListView iListView = getView().findViewById(R.id.InstructorListView);  // instructorlist_layout.xml의 리스트뷰
        final ArrayList<String> list = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.fix_simple_list, list);
        iListView.setAdapter(adapter);


//강의 추가 버튼 동작
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  // 강의 추가 Dialog 생성
                final EditText courseName = new EditText(getActivity()); // 강의명 입력받는 에디트 텍스트
                courseName.setHint("강의명을 입력해주세요.");
                builder.setTitle("강의 추가")
                        .setView(courseName)
                        .setPositiveButton("추가", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.add(courseName.getText().toString());
                                adapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("취소", null)
                        .create()
                        .show();
            }
        });

//리스트 아이템 롱 클릭시
        iListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // 강의 삭제 Dialog 생성
                builder.setTitle("강의 삭제")
                        .setMessage("강의를 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(position);
                                adapter.notifyDataSetChanged();
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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new InstructorAttendanceActivity()).commit();
            }
        });
    }
}

