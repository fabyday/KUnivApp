package com.kangwon.a356.kangwonunivapp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.kangwon.a356.kangwonunivapp.R;

import java.util.ArrayList;

public class InstructorLsitActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.instructorlist_layout,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        Button AddBtn = getView().findViewById(R.id.AddCourse);
        final ListView iListView = getView().findViewById(R.id.InstructorListView);
        final ArrayList<String> list = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
        iListView.setAdapter(adapter);
//추가버튼 동작
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                builder.setView(inflater.inflate(R.layout.addcoursedialog_layout,null))
                            .setTitle("강의 추가")
                            .setPositiveButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    View view = inflater.inflate(R.layout.addcoursedialog_layout, null);
                                    EditText courseName = (EditText)view.findViewById(R.id.CourseName);
                                    TextView test = (TextView)view.findViewById(R.id.test);
                                    list.add(courseName.getText().toString());
                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("취소", null)
                            .create()
                            .show();
            }
        });

        iListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("강의 삭제")
                        .setMessage("강의를 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        list.remove(position);
                                        adapter.notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("취소",null)
                        .create()
                        .show();
                return true;
            }
        });

        iListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new InstructorAttendanceActivity()).commit();
            }
        });

    }
}

