package com.kangwon.a356.kangwonunivapp.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.Calendar;
import java.util.Date;

/**
 * res/layout/instructordialog.xml 을 다룬다.
 * 교수가 강의명과 강의시작시간, 강의종료시간을 입력한다.
 */

public class InstructorDialogActivity extends Fragment {
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
        EditText CourseName = (EditText) getView().findViewById(R.id.CourseName);
        EditText StartDate = (EditText) getView().findViewById(R.id.startDate);
        EditText EndDate = (EditText) getView().findViewById(R.id.endDate);

        StartDate.setInputType(0);
        EndDate.setInputType(0);
        StartDate.setOnClickListener(dateEditOnClick);
        EndDate.setOnClickListener(dateEditOnClick);


        final CheckBox SunCBox = (CheckBox) getView().findViewById(R.id.SunCBox);
        final CheckBox MonCBox = (CheckBox) getView().findViewById(R.id.MonCBox);
        final CheckBox TueCBox = (CheckBox) getView().findViewById(R.id.TueCBox);
        final CheckBox WedCBox = (CheckBox) getView().findViewById(R.id.WedCBox);
        final CheckBox ThrCBox = (CheckBox) getView().findViewById(R.id.ThrCBox);
        final CheckBox FriCBox = (CheckBox) getView().findViewById(R.id.FriCBox);
        final CheckBox SatCBox = (CheckBox) getView().findViewById(R.id.SatCBox);

        Button YES = (Button) getView().findViewById(R.id.Yes);   //확인 버튼
        Button NO = (Button) getView().findViewById(R.id.No);     //취소 버튼


        YES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        NO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new InstructorLsitActivity()).commit();
            }
        });

        // 체크박스 클릭할 시
        SunCBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SunCBox.isChecked()) {
                    LinearLayout Sun = (LinearLayout) getView().findViewById(R.id.Sun);
                    Sun.setVisibility(Sun.VISIBLE);
                    EditText SunStart = (EditText) getView().findViewById(R.id.SunStart);
                    SunStart.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    SunStart.setOnClickListener(timeEditOnClick);
                    EditText SunEnd = (EditText) getView().findViewById(R.id.SunEnd);
                    SunEnd.setInputType(0);   // EditText 눌려도 키보드 안올라오게
                    SunEnd.setOnClickListener(timeEditOnClick);
                } else if (!(SunCBox.isChecked())) {
                    LinearLayout Sun = (LinearLayout) getView().findViewById(R.id.Sun);
                    Sun.setVisibility(Sun.GONE);
                }
            }
        });
        MonCBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MonCBox.isChecked()) {
                    LinearLayout Mon = (LinearLayout) getView().findViewById(R.id.Mon);
                    Mon.setVisibility(Mon.VISIBLE);
                    EditText MonStart = (EditText) getView().findViewById(R.id.MonStart);
                    MonStart.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    MonStart.setOnClickListener(timeEditOnClick);
                    EditText MonEnd = (EditText) getView().findViewById(R.id.MonEnd);
                    MonEnd.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    MonEnd.setOnClickListener(timeEditOnClick);
                } else if (!(MonCBox.isChecked())) {
                    LinearLayout Mon = (LinearLayout) getView().findViewById(R.id.Mon);
                    Mon.setVisibility(Mon.GONE);
                }
            }
        });

        TueCBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TueCBox.isChecked()) {
                    LinearLayout Tue = (LinearLayout) getView().findViewById(R.id.Tue);
                    Tue.setVisibility(Tue.VISIBLE);
                    EditText TueStart = (EditText) getView().findViewById(R.id.TueStart);
                    TueStart.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    TueStart.setOnClickListener(timeEditOnClick);
                    EditText TueEnd = (EditText) getView().findViewById(R.id.TueEnd);
                    TueEnd.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    TueEnd.setOnClickListener(timeEditOnClick);
                } else if (!(TueCBox.isChecked())) {
                    LinearLayout Tue = (LinearLayout) getView().findViewById(R.id.Tue);
                    Tue.setVisibility(Tue.GONE);
                }
            }
        });
        WedCBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (WedCBox.isChecked()) {
                    LinearLayout Wed = (LinearLayout) getView().findViewById(R.id.Wed);
                    Wed.setVisibility(Wed.VISIBLE);
                    EditText WedStart = (EditText) getView().findViewById(R.id.WedStart);
                    WedStart.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    WedStart.setOnClickListener(timeEditOnClick);
                    EditText WedEnd = (EditText) getView().findViewById(R.id.WedEnd);
                    WedEnd.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    WedEnd.setOnClickListener(timeEditOnClick);
                } else if (!(WedCBox.isChecked())) {
                    LinearLayout Wed = (LinearLayout) getView().findViewById(R.id.Wed);
                    Wed.setVisibility(Wed.GONE);
                }
            }
        });
        ThrCBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThrCBox.isChecked()) {
                    LinearLayout Thr = (LinearLayout) getView().findViewById(R.id.Thr);
                    Thr.setVisibility(Thr.VISIBLE);
                    EditText ThrStart = (EditText) getView().findViewById(R.id.ThrStart);
                    ThrStart.setOnClickListener(timeEditOnClick);
                    ThrStart.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    EditText ThrEnd = (EditText) getView().findViewById(R.id.ThrEnd);
                    ThrEnd.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    ThrEnd.setOnClickListener(timeEditOnClick);
                } else if (!(ThrCBox.isChecked())) {
                    LinearLayout Thr = (LinearLayout) getView().findViewById(R.id.Thr);
                    Thr.setVisibility(Thr.GONE);
                }
            }
        });
        FriCBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FriCBox.isChecked()) {
                    LinearLayout Fri = (LinearLayout) getView().findViewById(R.id.Fri);
                    Fri.setVisibility(Fri.VISIBLE);
                    EditText FriStart = (EditText) getView().findViewById(R.id.FriStart);
                    FriStart.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    FriStart.setOnClickListener(timeEditOnClick);
                    EditText FriEnd = (EditText) getView().findViewById(R.id.FriEnd);
                    FriEnd.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    FriEnd.setOnClickListener(timeEditOnClick);
                } else if (!(FriCBox.isChecked())) {
                    LinearLayout Fri = (LinearLayout) getView().findViewById(R.id.Fri);
                    Fri.setVisibility(Fri.GONE);
                }
            }
        });
        SatCBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SatCBox.isChecked()) {
                    LinearLayout Sat = (LinearLayout) getView().findViewById(R.id.Sat);
                    Sat.setVisibility(Sat.VISIBLE);
                    EditText SatStart = (EditText) getView().findViewById(R.id.SatStart);
                    SatStart.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    SatStart.setOnClickListener(timeEditOnClick);
                    EditText SatEnd = (EditText) getView().findViewById(R.id.SatEnd);
                    SatEnd.setInputType(0); // EditText 눌려도 키보드 안올라오게
                    SatEnd.setOnClickListener(timeEditOnClick);
                } else if (!(SatCBox.isChecked())) {
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
                            SunStart.setText((String.valueOf(hourOfDay)) + ":" + (String.valueOf(minute)));
                            break;
                        case R.id.SunEnd:
                            EditText SunEnd = (EditText) getView().findViewById(R.id.SunEnd);
                            SunEnd.setText((String.valueOf(hourOfDay)) + ":" + (String.valueOf(minute)));
                            break;
                        case R.id.MonStart:
                            EditText MonStart = (EditText) getView().findViewById(R.id.MonStart);
                            MonStart.setText((String.valueOf(hourOfDay)) + ":" + (String.valueOf(minute)));
                            break;
                        case R.id.MonEnd:
                            EditText MonEnd = (EditText) getView().findViewById(R.id.MonEnd);
                            MonEnd.setText((String.valueOf(hourOfDay)) + ":" + (String.valueOf(minute)));
                            break;
                        case R.id.TueStart:
                            EditText TueStart = (EditText) getView().findViewById(R.id.TueStart);
                            TueStart.setText((String.valueOf(hourOfDay)) + ":" + (String.valueOf(minute)));
                            break;
                        case R.id.TueEnd:
                            EditText TueEnd = (EditText) getView().findViewById(R.id.TueEnd);
                            TueEnd.setText((String.valueOf(hourOfDay)) + ":" + (String.valueOf(minute)));
                            break;
                        case R.id.WedStart:
                            EditText WedStart = (EditText) getView().findViewById(R.id.WedStart);
                            WedStart.setText((String.valueOf(hourOfDay)) + ":" + (String.valueOf(minute)));
                            break;
                        case R.id.WedEnd:
                            EditText WedEnd = (EditText) getView().findViewById(R.id.WedEnd);
                            WedEnd.setText((String.valueOf(hourOfDay)) + ":" + (String.valueOf(minute)));
                            break;
                        case R.id.ThrStart:
                            EditText ThrStart = (EditText) getView().findViewById(R.id.ThrStart);
                            ThrStart.setText((String.valueOf(hourOfDay)) + ":" + (String.valueOf(minute)));
                            break;
                        case R.id.ThrEnd:
                            EditText ThrEnd = (EditText) getView().findViewById(R.id.ThrEnd);
                            ThrEnd.setText((String.valueOf(hourOfDay)) + ":" + (String.valueOf(minute)));
                            break;
                        case R.id.FriStart:
                            EditText FriStart = (EditText) getView().findViewById(R.id.FriStart);
                            FriStart.setText((String.valueOf(hourOfDay)) + ":" + (String.valueOf(minute)));
                            break;
                        case R.id.FriEnd:
                            EditText FriEnd = (EditText) getView().findViewById(R.id.FriEnd);
                            FriEnd.setText((String.valueOf(hourOfDay)) + ":" + (String.valueOf(minute)));
                            break;
                        case R.id.SatStart:
                            EditText SatStart = (EditText) getView().findViewById(R.id.SatStart);
                            SatStart.setText((String.valueOf(hourOfDay)) + ":" + (String.valueOf(minute)));
                            break;
                        case R.id.SatEnd:
                            EditText SatEnd = (EditText) getView().findViewById(R.id.SatEnd);
                            SatEnd.setText((String.valueOf(hourOfDay)) + ":" + (String.valueOf(minute)));
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
            },2018,11,01);

            dDialog.show();

            }
        };
    }