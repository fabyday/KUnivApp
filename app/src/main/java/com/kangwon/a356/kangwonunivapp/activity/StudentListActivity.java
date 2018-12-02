package com.kangwon.a356.kangwonunivapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kangwon.a356.kangwonunivapp.R;

import java.util.ArrayList;

public class StudentListActivity extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.studentlsit_layout,container,false);
        return view;
    }
    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        ListView sListView = getView().findViewById(R.id.StudentListView); // studentlsit_layout.xml의 리스트뷰
        final ArrayList<String> list = new ArrayList<String>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
        sListView.setAdapter(adapter);

        //테스트
        list.add("Please");
        adapter.notifyDataSetChanged();

        sListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // 리스트뷰 아이템 클릭시 출결인증 화면으로
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new AttendanceActivity()).commit();
            }
        });
    }
}
