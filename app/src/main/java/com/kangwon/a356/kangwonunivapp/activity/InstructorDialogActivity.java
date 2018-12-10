package com.kangwon.a356.kangwonunivapp.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.kangwon.a356.kangwonunivapp.R;
import com.kangwon.a356.kangwonunivapp.database.ClassInfo;
import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.database.TimeSpaceInfo;
import com.kangwon.a356.kangwonunivapp.dataprocess.ProcessManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * res/layout/instructordialog.xml 을 다룬다.
 * 교수가 강의명과 강의시작시간, 강의종료시간을 입력한다.
 */

public class InstructorDialogActivity extends Fragment {

    CheckBox[] checkBoxes;
    EditText[] startTime;
    EditText[] endTime;
    EditText[] classPlace;

    InstructorLsitActivity instructorLsitActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.instructordialog, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final EditText CourseName = (EditText) getView().findViewById(R.id.CourseName);
        final EditText StartDate = (EditText) getView().findViewById(R.id.startDate);
        final EditText EndDate = (EditText) getView().findViewById(R.id.endDate);

        StartDate.setInputType(0);
        EndDate.setInputType(0);
        StartDate.setOnClickListener(dateEditOnClick);
        EndDate.setOnClickListener(dateEditOnClick);


        startTime = new EditText[7];
        endTime = new EditText[7];
        classPlace = new EditText[7];


        checkBoxes = new CheckBox[7];
        checkBoxes[0] = (CheckBox) getView().findViewById(R.id.SunCBox);
        checkBoxes[1] = (CheckBox) getView().findViewById(R.id.MonCBox);
        checkBoxes[2] = (CheckBox) getView().findViewById(R.id.TueCBox);
        checkBoxes[3] = (CheckBox) getView().findViewById(R.id.WedCBox);
        checkBoxes[4] = (CheckBox) getView().findViewById(R.id.ThrCBox);
        checkBoxes[5] = (CheckBox) getView().findViewById(R.id.FriCBox);
        checkBoxes[6] = (CheckBox) getView().findViewById(R.id.SatCBox);


        Button YES = (Button) getView().findViewById(R.id.Yes);   //확인 버튼
        Button NO = (Button) getView().findViewById(R.id.No);     //취소 버튼


        YES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int length = checkBoxes.length;
                ArrayList<LinkedHashMap> msgObject = new ArrayList<>();
                for (int i = 0; i < length; i++) {
                    if (checkBoxes[i].isChecked()) {
                        LinkedHashMap<String, String> msg = new LinkedHashMap<>();
                        msg.put(MessageObject.TYPE, MessageObject.OPEN_LECTURE);
                        msg.put(ClassInfo.CLASSNAME, CourseName.getText().toString());
                        msg.put(TimeSpaceInfo.CLASSNAME_TYPE, classPlace[i].getText().toString()); //야 이거 안만들었네 골때리게 ㅅㅂ ///////
                        msg.put(TimeSpaceInfo.DAY_TYPE, TimeSpaceInfo.DAY_STRING_TO_INTEGER.toStringDay(i));
                        msg.put(ClassInfo.START_DATE, StartDate.getText().toString());
                        msg.put(ClassInfo.END_DATE, EndDate.getText().toString());
                        msg.put(TimeSpaceInfo.START_TYPE, startTime[i].getText().toString());
                        msg.put(TimeSpaceInfo.END_TYPE, endTime[i].getText().toString());

                        msgObject.add(msg);
                    }
                }
                ProcessManager.getInstance().commitRequest(msgObject, ((MainActivity) getActivity()).handler); // 강의 콜링.
                FragmentManager f= getActivity().getSupportFragmentManager();

                ((MainActivity) getActivity()).processManager.updateRequest(MessageObject.INSTRUCTOR_TIME_TABLE_TYPE, ((MainActivity) getActivity()).handler);
                f.popBackStack();
            }
        });
        NO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager f= getActivity().getSupportFragmentManager();
                f.popBackStack();
            }
        });

        // 체크박스 클릭할 시
        checkBoxes[0].setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxes[0].isChecked()) {
                    LinearLayout Sun = (LinearLayout) getView().findViewById(R.id.Sun);
                    Sun.setVisibility(Sun.VISIBLE);
                    EditText SunStart = (EditText) getView().findViewById(R.id.SunStart);
                    SunStart.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    SunStart.setOnClickListener(timeEditOnClick);
                    EditText SunEnd = (EditText) getView().findViewById(R.id.SunEnd);
                    SunEnd.setInputType(0);   // EditText 눌려도 키보드 안올라오게
                    SunEnd.setOnClickListener(timeEditOnClick);
                    classPlace[0] = (EditText)getView().findViewById(R.id.SunPlace);
                    startTime[0] = SunStart;
                    endTime[0] = SunEnd;

                } else if (!(checkBoxes[0].isChecked())) {
                    LinearLayout Sun = (LinearLayout) getView().findViewById(R.id.Sun);
                    Sun.setVisibility(Sun.GONE);
                }
            }
        });
        checkBoxes[1].setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxes[1].isChecked()) {
                    LinearLayout Mon = (LinearLayout) getView().findViewById(R.id.Mon);
                    Mon.setVisibility(Mon.VISIBLE);
                    EditText MonStart = (EditText) getView().findViewById(R.id.MonStart);
                    MonStart.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    MonStart.setOnClickListener(timeEditOnClick);
                    EditText MonEnd = (EditText) getView().findViewById(R.id.MonEnd);
                    MonEnd.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    MonEnd.setOnClickListener(timeEditOnClick);
                    classPlace[1] = (EditText)getView().findViewById(R.id.MonPlace);
                    startTime[1] = MonStart;
                    endTime[1] = MonEnd;
                } else if (!(checkBoxes[1].isChecked())) {
                    LinearLayout Mon = (LinearLayout) getView().findViewById(R.id.Mon);
                    Mon.setVisibility(Mon.GONE);
                }
            }
        });

        checkBoxes[2].setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxes[2].isChecked()) {
                    LinearLayout Tue = (LinearLayout) getView().findViewById(R.id.Tue);
                    Tue.setVisibility(Tue.VISIBLE);
                    EditText TueStart = (EditText) getView().findViewById(R.id.TueStart);
                    TueStart.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    TueStart.setOnClickListener(timeEditOnClick);
                    EditText TueEnd = (EditText) getView().findViewById(R.id.TueEnd);
                    TueEnd.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    TueEnd.setOnClickListener(timeEditOnClick);
                    classPlace[2] = (EditText)getView().findViewById(R.id.TuePlace);
                    startTime[2] = TueStart;
                    endTime[2] = TueEnd;
                } else if (!(checkBoxes[2].isChecked())) {
                    LinearLayout Tue = (LinearLayout) getView().findViewById(R.id.Tue);
                    Tue.setVisibility(Tue.GONE);
                }
            }
        });
        checkBoxes[3].setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxes[3].isChecked()) {
                    LinearLayout Wed = (LinearLayout) getView().findViewById(R.id.Wed);
                    Wed.setVisibility(Wed.VISIBLE);
                    EditText WedStart = (EditText) getView().findViewById(R.id.WedStart);
                    WedStart.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    WedStart.setOnClickListener(timeEditOnClick);
                    EditText WedEnd = (EditText) getView().findViewById(R.id.WedEnd);
                    WedEnd.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    WedEnd.setOnClickListener(timeEditOnClick);
                    classPlace[3] = (EditText)getView().findViewById(R.id.WedPlace);
                    startTime[3] = WedStart;
                    endTime[3] = WedEnd;
                } else if (!(checkBoxes[3].isChecked())) {
                    LinearLayout Wed = (LinearLayout) getView().findViewById(R.id.Wed);
                    Wed.setVisibility(Wed.GONE);
                }
            }
        });
        checkBoxes[4].setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxes[4].isChecked()) {
                    LinearLayout Thr = (LinearLayout) getView().findViewById(R.id.Thr);
                    Thr.setVisibility(Thr.VISIBLE);
                    EditText ThrStart = (EditText) getView().findViewById(R.id.ThrStart);
                    ThrStart.setOnClickListener(timeEditOnClick);
                    ThrStart.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    EditText ThrEnd = (EditText) getView().findViewById(R.id.ThrEnd);
                    ThrEnd.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    ThrEnd.setOnClickListener(timeEditOnClick);
                    classPlace[4] = (EditText)getView().findViewById(R.id.ThrPlace);
                    startTime[4] = ThrStart;
                    endTime[4] = ThrEnd;
                } else if (!(checkBoxes[4].isChecked())) {
                    LinearLayout Thr = (LinearLayout) getView().findViewById(R.id.Thr);
                    Thr.setVisibility(Thr.GONE);
                }
            }
        });
        checkBoxes[5].setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxes[5].isChecked()) {
                    LinearLayout Fri = (LinearLayout) getView().findViewById(R.id.Fri);
                    Fri.setVisibility(Fri.VISIBLE);
                    EditText FriStart = (EditText) getView().findViewById(R.id.FriStart);
                    FriStart.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    FriStart.setOnClickListener(timeEditOnClick);
                    EditText FriEnd = (EditText) getView().findViewById(R.id.FriEnd);
                    FriEnd.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    FriEnd.setOnClickListener(timeEditOnClick);
                    classPlace[5] = (EditText)getView().findViewById(R.id.FriPlace);
                    startTime[5] = FriStart;
                    endTime[5] = FriEnd;
                } else if (!(checkBoxes[5].isChecked())) {
                    LinearLayout Fri = (LinearLayout) getView().findViewById(R.id.Fri);
                    Fri.setVisibility(Fri.GONE);
                }
            }
        });
        checkBoxes[6].setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxes[6].isChecked()) {
                    LinearLayout Sat = (LinearLayout) getView().findViewById(R.id.Sat);
                    Sat.setVisibility(Sat.VISIBLE);
                    EditText SatStart = (EditText) getView().findViewById(R.id.SatStart);
                    SatStart.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    SatStart.setOnClickListener(timeEditOnClick);
                    EditText SatEnd = (EditText) getView().findViewById(R.id.SatEnd);
                    SatEnd.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    SatEnd.setOnClickListener(timeEditOnClick);
                    classPlace[6] = (EditText)getView().findViewById(R.id.SatPlace);
                    startTime[6] = SatStart;
                    endTime[6] = SatEnd;
                } else if (!(checkBoxes[6].isChecked())) {
                    LinearLayout Sat = (LinearLayout) getView().findViewById(R.id.Sat);
                    Sat.setVisibility(Sat.GONE);
                }
            }
        });
    }

    private EditText.OnClickListener timeEditOnClick = new EditText.OnClickListener() { //EditText 클릭하면
        @Override
        public void onClick(final View v) {
            final TimePickerDialog tDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {  //TimePickerDialog 확인 버튼 누르면 돌아가는 메소드
                    switch (v.getId()) {                                            // 클릭했던 EditText에 SetText(hourOfDay +":" + minute) 하는 과정
                        case R.id.SunStart:
                            EditText SunStart = (EditText) getView().findViewById(R.id.SunStart);
                            SunStart.setText(String.format("%02d:%02d",hourOfDay, minute ));

                            break;
                        case R.id.SunEnd:
                            EditText SunEnd = (EditText) getView().findViewById(R.id.SunEnd);
                            SunEnd.setText(String.format("%02d:%02d",hourOfDay, minute ));
                            break;
                        case R.id.MonStart:
                            EditText MonStart = (EditText) getView().findViewById(R.id.MonStart);
                            MonStart.setText(String.format("%02d:%02d",hourOfDay, minute ));
                            break;
                        case R.id.MonEnd:
                            EditText MonEnd = (EditText) getView().findViewById(R.id.MonEnd);
                            MonEnd.setText(String.format("%02d:%02d",hourOfDay, minute ));
                            break;
                        case R.id.TueStart:
                            EditText TueStart = (EditText) getView().findViewById(R.id.TueStart);
                            TueStart.setText(String.format("%02d:%02d",hourOfDay, minute ));
                            break;
                        case R.id.TueEnd:
                            EditText TueEnd = (EditText) getView().findViewById(R.id.TueEnd);
                            TueEnd.setText(String.format("%02d:%02d",hourOfDay, minute ));
                            break;
                        case R.id.WedStart:
                            EditText WedStart = (EditText) getView().findViewById(R.id.WedStart);
                            WedStart.setText(String.format("%02d:%02d",hourOfDay, minute ));
                            break;
                        case R.id.WedEnd:
                            EditText WedEnd = (EditText) getView().findViewById(R.id.WedEnd);
                            WedEnd.setText(String.format("%02d:%02d",hourOfDay, minute ));
                            break;
                        case R.id.ThrStart:
                            EditText ThrStart = (EditText) getView().findViewById(R.id.ThrStart);
                            ThrStart.setText(String.format("%02d:%02d",hourOfDay, minute ));
                            break;
                        case R.id.ThrEnd:
                            EditText ThrEnd = (EditText) getView().findViewById(R.id.ThrEnd);
                            ThrEnd.setText(String.format("%02d:%02d",hourOfDay, minute ));
                            break;
                        case R.id.FriStart:
                            EditText FriStart = (EditText) getView().findViewById(R.id.FriStart);
                            FriStart.setText(String.format("%02d:%02d",hourOfDay, minute ));
                            break;
                        case R.id.FriEnd:
                            EditText FriEnd = (EditText) getView().findViewById(R.id.FriEnd);
                            FriEnd.setText(String.format("%02d:%02d",hourOfDay, minute ));
                            break;
                        case R.id.SatStart:
                            EditText SatStart = (EditText) getView().findViewById(R.id.SatStart);
                            SatStart.setText(String.format("%02d:%02d",hourOfDay, minute ));
                            break;
                        case R.id.SatEnd:
                            EditText SatEnd = (EditText) getView().findViewById(R.id.SatEnd);
                            SatEnd.setText(String.format("%02d:%02d",hourOfDay, minute ));
                            break;
                    }
                }

            }, 00, 00, true);
            tDialog.show();
        }
    };
    private EditText.OnClickListener dateEditOnClick = new EditText.OnClickListener() { //EditText 클릭하면
        @Override
        public void onClick(final View v) {
            DatePickerDialog dDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) { //DatePickerDialog 확인 버튼 누르면 돌아가는 메소드
                    switch (v.getId()) {                                            // 클릭했던 EditText에 SetText(year +"-" + month +"-"+ day) 하는 과정
                        case R.id.startDate:
                            EditText StartDate = (EditText) getView().findViewById(R.id.startDate);
                            StartDate.setText((String.valueOf(year)) + "-" + (String.valueOf(month + 1)) + "-" + (String.valueOf(day)));
                            break;
                        case R.id.endDate:
                            EditText EndDate = (EditText) getView().findViewById(R.id.endDate);
                            EndDate.setText((String.valueOf(year)) + "-" + (String.valueOf(month + 1)) + "-" + (String.valueOf(day)));
                            break;
                    }
                }
            }, 2018, 11, 01);

            dDialog.show();

        }
    };

}