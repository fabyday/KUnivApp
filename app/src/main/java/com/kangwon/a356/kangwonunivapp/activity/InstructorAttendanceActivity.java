package com.kangwon.a356.kangwonunivapp.activity;

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
import android.widget.TableRow;
import android.widget.TextView;

import com.kangwon.a356.kangwonunivapp.R;

/**
 * res/layout/instructorattendance_layout.xml 을 다룬다.
 * 강의가 언제 있는지 나타내는 테이블과 테이블의 한 행을 누르면 출석인증번호가 있는 Dialog 가 나오게 한다.
 */
public class InstructorAttendanceActivity extends Fragment {


    public static final int INSTURCTOR_ATTANDANCE = 8;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //ActivityTools.makeFullScreen(this);
        View view = inflater.inflate(R.layout.instructorattendance_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        TableRow[] tableRow = new TableRow[15]; // 출결인증 테이블의 각 행
        tableRow[0] = (TableRow) getView().findViewById(R.id.TableRow1);
        tableRow[1] = (TableRow) getView().findViewById(R.id.TableRow2);
        tableRow[2] = (TableRow) getView().findViewById(R.id.TableRow3);
        tableRow[3] = (TableRow) getView().findViewById(R.id.TableRow4);
        tableRow[4] = (TableRow) getView().findViewById(R.id.TableRow5);
        tableRow[5] = (TableRow) getView().findViewById(R.id.TableRow6);
        tableRow[6] = (TableRow) getView().findViewById(R.id.TableRow7);
        tableRow[7] = (TableRow) getView().findViewById(R.id.TableRow8);
        tableRow[8] = (TableRow) getView().findViewById(R.id.TableRow9);
        tableRow[9] = (TableRow) getView().findViewById(R.id.TableRow10);
        tableRow[10] = (TableRow) getView().findViewById(R.id.TableRow11);
        tableRow[11] = (TableRow) getView().findViewById(R.id.TableRow12);
        tableRow[12] = (TableRow) getView().findViewById(R.id.TableRow13);
        tableRow[13] = (TableRow) getView().findViewById(R.id.TableRow14);
        tableRow[14] = (TableRow) getView().findViewById(R.id.TableRow15);

        for (int i = 0; i < 15; i++)
            tableRow[i].setOnClickListener(tableRowClick);
    }

    // 출결인증 테이블의 각 행 클릭시
    TableRow.OnClickListener tableRowClick = new TableRow.OnClickListener() {
        @Override
        public void onClick(View v) {

            //test
            int RanNum = (int) (Math.random() * 9999) + 1;  // 출석인증 랜덤 숫자
            String InsAttNum = String.valueOf(RanNum);

            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // 교수용 출석 인증 번호 생성하는 Dialog 생성
            builder.setTitle("출석 인증")
                    .setMessage(String.valueOf(InsAttNum)) // * Message의 내용 수정 필요. 학생 출석인증 화면에서 비교를 위해
                    .setPositiveButton("시작", null) //아래에서 클릭시 내용 구현
                    .setNegativeButton("중지", null); //아래에서 클릭시 내용 구현
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {// 시작 버튼 누를 때
                @Override
                public void onClick(View v) {
                    if (false) { // 시작 버튼 눌려도 Dialog 꺼지지 않게 하기 위해
                        alertDialog.dismiss();
                    }
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false); // 시작 버튼 누를 시 중지 버튼 누를 때까지 시작 버튼 누르지 못하게 하기
                }
            });

            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {// 중지 버튼 누를 때
                @Override
                public void onClick(View v) { // 중지 버튼 누르면 시작 버튼 활성화 또는 Dialog 종료
                    Boolean dialogEnd = false; // 교수용 출석 인증 번호 생성하는 Dialog 종료 할지 여부
                    if (alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled() == false) { // 시작버튼 비활성화시
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    } else {
                        dialogEnd = true;
                    }
                    if (dialogEnd) // 교수용 출석 인증 번호 생성하는 Dialog 종료할지 말지 판단
                        alertDialog.dismiss();
                }
            });
        }
    };
}
