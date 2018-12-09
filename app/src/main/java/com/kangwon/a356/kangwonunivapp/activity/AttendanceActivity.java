package com.kangwon.a356.kangwonunivapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kangwon.a356.kangwonunivapp.R;
import com.kangwon.a356.kangwonunivapp.database.Attandance;
import com.kangwon.a356.kangwonunivapp.database.ClassInfo;
import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.dataprocess.ProcessManager;

import java.util.LinkedHashMap;

/**
 * @author 노지현
 * @version 1
 * 이 클래스는 출석체크와 출석 체크와 출석 리스트를 보여주는 액티비티이다.
 * res/layout/attendance_layout.xml을 사용한다.
 */

public class AttendanceActivity extends Fragment {

    public static final int ATTANDANCE_ACTIVITY = 16;
    ClassInfo classInfo;

    /**
     *
     */
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
        final EditText AttenddanceNumber = (EditText) getView().findViewById(R.id.attenddanceNumber); // 인증 번호 넣는 에디트 텍스트
        Button Confirm = (Button) getView().findViewById(R.id.confirm); // 확인 버튼


        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // * 교수의 인증 번호랑 비교

                try {
                    String auth = AttenddanceNumber.getText().toString();
                    Integer.parseInt(auth); //만일 숫자가 아니라면 이 곳에서 NumberFormatException을 발생 시킬 것이다!


                    LinkedHashMap<String, String> msg = new LinkedHashMap<>();
                    msg.put(MessageObject.TYPE, MessageObject.CHECK_ATTANDANCE);
                    msg.put(ClassInfo.CLASSNAME, classInfo.getClassName());
                    msg.put(ClassInfo.INSTRUCTOR, classInfo.getInstructor());
                    msg.put(Attandance.AUTH, auth);
                    ProcessManager.getInstance().commitRequest(msg, ((MainActivity) getActivity()).handler);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "인증 번호는 숫자만 가능합니다.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }


}
