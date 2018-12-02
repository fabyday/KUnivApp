package com.kangwon.a356.kangwonunivapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kangwon.a356.kangwonunivapp.R;

/**
 * @author 노지현
 * @version 1
 * 이 클래스는 출석체크와 출석 체크와 출석 리스트를 보여주는 액티비티이다.
 * res/layout/attendance_layout.xml을 사용한다.
 */

public class AttendanceActivity extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //ActivityTools.makeFullScreen(this);
        View view = inflater.inflate(R.layout.attendance_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        EditText AttenddanceNumber = (EditText) getView().findViewById(R.id.attenddanceNumber); // 인증 번호 넣는 에디트 텍스트
        Button Confirm = (Button) getView().findViewById(R.id.confirm); // 확인 버튼

        final String AttNum = AttenddanceNumber.getText().toString(); // 에디트 텍스트 -> String

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // * 교수의 인증 번호랑 비교


                Toast.makeText(getActivity(), "인증 되었습니다.", Toast.LENGTH_LONG).show();
            }
        });
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
