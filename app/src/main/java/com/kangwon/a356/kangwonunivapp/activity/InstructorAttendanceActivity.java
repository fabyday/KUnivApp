package com.kangwon.a356.kangwonunivapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.kangwon.a356.kangwonunivapp.R;

public class InstructorAttendanceActivity extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //ActivityTools.makeFullScreen(this);
        View view =  inflater.inflate(R.layout.instructorattendance_layout,container,false);
        return view;
    }
    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        TableRow[] tableRow = new TableRow[15]; // 출결인증 테이블의 각 행
        tableRow[0] =  (TableRow)getView().findViewById(R.id.TableRow1);
        tableRow[1] =  (TableRow)getView().findViewById(R.id.TableRow2);
        tableRow[2] =  (TableRow)getView().findViewById(R.id.TableRow3);
        tableRow[3] =  (TableRow)getView().findViewById(R.id.TableRow4);
        tableRow[4] =  (TableRow)getView().findViewById(R.id.TableRow5);
        tableRow[5] =  (TableRow)getView().findViewById(R.id.TableRow6);
        tableRow[6] =  (TableRow)getView().findViewById(R.id.TableRow7);
        tableRow[7] =  (TableRow)getView().findViewById(R.id.TableRow8);
        tableRow[8] =  (TableRow)getView().findViewById(R.id.TableRow9);
        tableRow[9] =  (TableRow)getView().findViewById(R.id.TableRow10);
        tableRow[10] =  (TableRow)getView().findViewById(R.id.TableRow11);
        tableRow[11] =  (TableRow)getView().findViewById(R.id.TableRow12);
        tableRow[12] =  (TableRow)getView().findViewById(R.id.TableRow13);
        tableRow[13] =  (TableRow)getView().findViewById(R.id.TableRow14);
        tableRow[14] =  (TableRow)getView().findViewById(R.id.TableRow15);

        for(int i = 0; i < 15; i++)
            tableRow[i].setOnClickListener(tableRowClick);
    }

    // 출결인증 테이블의 각 행 클릭시
    TableRow.OnClickListener tableRowClick = new TableRow.OnClickListener() {
        @Override
        public void onClick(View v) {
            int Num = (int) (Math.random() * 9999) + 1 ;  // 출석인증 랜덤 숫자
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // 교수용 출석 인증 번호 생성하는 Dialog 생성
            builder.setTitle("출석 인증")
                    .setMessage(String.valueOf(Num))
                    .setPositiveButton("시작", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("중지", null);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                { // 시작 버튼 눌려도 Dialog 꺼지지 않게 하기 위해
                    if(false)
                        alertDialog.dismiss();
                }
            });
        }
    };
}
