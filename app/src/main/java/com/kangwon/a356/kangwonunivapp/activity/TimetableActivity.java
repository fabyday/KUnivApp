package com.kangwon.a356.kangwonunivapp.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.github.eunsiljo.timetablelib.data.TimeData;
import com.github.eunsiljo.timetablelib.data.TimeGridData;
import com.github.eunsiljo.timetablelib.data.TimeTableData;
import com.github.eunsiljo.timetablelib.view.TimeTableView;
import com.github.eunsiljo.timetablelib.viewholder.TimeTableItemViewHolder;
import com.kangwon.a356.kangwonunivapp.R;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 노지현
 * @version 1
 * 이 클래스는 로그인을 담당하는 클래스이며
 * res/layout/timetable_layout.xml의 레이아웃들을 다룬다.
 */

public class TimetableActivity extends Fragment {

    //View btnMode; //툴바 2칸 ,7칸 변경 버튼
    TimeTableView timeTable;
    Button attendanceBtn;

   // ArrayList<TimeTableData> mShortSamples = new ArrayList<>();  // 두칸 테이블 샘플을 위해 사용
    // ArrayList<TimeTableData> mLongSamples = new ArrayList<>();  // 7칸 데이블 샘플을 위해 사용

    List<String> mTitles = Arrays.asList("Korean", "English", "Math", "Science", "Physics", "Chemistry", "Biology");   // 강의 샘플
    //List<String> mLongHeaders = Arrays.asList("Plan", "Do");   // 두 칸 테이블를 위한 헤더
    List<String> mShortHeaders = Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
    long mNow = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timetable_layout, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

            initLayout();
            initListener();
            initData();
            attendanceBtn = (Button)getView().findViewById(R.id.attendanceBtn);  // 출석인증 버튼
            attendanceBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AttendanceActivity()).commit();
                }
                });
        }

        private void initLayout() {
           // btnMode = getView().findViewById(R.id.btnMode);
            timeTable = (TimeTableView)getView().findViewById(R.id.timeTable);
            }

        private void initListener() {
/*          툴바 버튼 효과 2칸, 7칸 변경
            btnMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toogleMode();

                    if(v.isActivated()){
                        timeTable.setShowHeader(true);
                        timeTable.setTableMode(TimeTableView.TableMode.SHORT);
                        //timeTable.setTimeTable(getMillis("2017-11-10 00:00:00"), mShortSamples);
                        timeTable.setTimeTable(mNow, getSamples(mNow, mShortHeaders, mTitles));

                    }else{
                        timeTable.setShowHeader(true);
                        timeTable.setTableMode(TimeTableView.TableMode.LONG);
                        //timeTable.setTimeTable(getMillis("2017-11-10 00:00:00"), mLongSamples);
                        timeTable.setTimeTable(mNow, getSamples(mNow, mLongHeaders, mTitles));
                    }
                }
            });
*/
            timeTable.setOnTimeItemClickListener(new TimeTableItemViewHolder.OnTimeItemClickListener() {
                @Override
                public void onTimeItemClick(View view, int position, TimeGridData item) {  // 강의 눌렸을 때 슬라이드 올라오게

                    LinearLayout Sliding = (LinearLayout) getView().findViewById(R.id.Sliding);
                    Sliding.callOnClick();

                    /* 강의 눌렸을 때 토스트 메세지
                    TimeData time = item.getTime();
                    showToast(getActivity(),
                            time.getTitle() + ", " + new DateTime(time.getStartMills()).toString() +
                                    " ~ " + new DateTime(time.getStopMills()).toString());*/
                }
            });
        }

        private void initData(){
            //initLongSamples();
            //initShortSamples();

            timeTable.setStartHour(0);
            timeTable.setShowHeader(true);
            timeTable.setTableMode(TimeTableView.TableMode.SHORT);

            DateTime now = DateTime.now();
            mNow = now.withTimeAtStartOfDay().getMillis();

            //timeTable.setTimeTable(getMillis("2017-11-10 00:00:00"), mLongSamples);
            timeTable.setTimeTable(mNow, getSamples(mNow, mShortHeaders, mTitles));
        }

        // ******* 이 부분이 강의 생성 하는 것으로 추정 ***************************
        private ArrayList<TimeTableData> getSamples(long date, List<String> headers, List<String> titles){
            TypedArray colors_table = getResources().obtainTypedArray(R.array.colors_table);
            TypedArray colors_table_light = getResources().obtainTypedArray(R.array.colors_table_light);

            ArrayList<TimeTableData> tables = new ArrayList<>();
            for(int i=0; i<headers.size(); i++){
                ArrayList<TimeData> values = new ArrayList<>();
                DateTime start = new DateTime(date);
                DateTime end = start.plusMinutes((int)((Math.random() * 10) + 1) * 30);
                for(int j=0; j<titles.size(); j++){
                    int color = colors_table_light.getResourceId(j, 0);
                    int textColor = R.color.black;
                    //TEST
                    if(headers.size() == 2 && i == 1){
                        color = colors_table.getResourceId(j, 0);
                        textColor = R.color.white;
                    }

                    TimeData timeData = new TimeData(j, titles.get(j), color, textColor, start.getMillis(), end.getMillis());

                    //TEST
                    if(headers.size() == 2 && j == 2){
                        timeData.setShowError(true);
                    }
                    values.add(timeData);

                    start = end.plusMinutes((int)((Math.random() * 10) + 1) * 10);
                    end = start.plusMinutes((int)((Math.random() * 10) + 1) * 30);
                }

                tables.add(new TimeTableData(headers.get(i), values));
            }
            return tables;
        }
/*  두칸 테이블 예제
        private void initLongSamples(){
            //TEST
            ArrayList<TimeData> values = new ArrayList<>();
            values.add(new TimeData(0, "Korean", R.color.color_table_1_light, getMillis("2017-11-10 04:00:00"), getMillis("2017-11-10 05:00:00")));
            values.add(new TimeData(1, "English", R.color.color_table_2_light, getMillis("2017-11-10 07:00:00"), getMillis("2017-11-10 08:00:00")));

            ArrayList<TimeData> values2 = new ArrayList<>();
            values2.add(new TimeData(0, "Korean", R.color.color_table_1, R.color.white, getMillis("2017-11-10 03:00:00"), getMillis("2017-11-10 06:00:00")));

            TimeData timeData = new TimeData(1, "English", R.color.color_table_2, R.color.white, getMillis("2017-11-10 07:30:00"), getMillis("2017-11-10 08:55:00"));
            timeData.setShowError(true);
            values2.add(timeData);

            values2.add(new TimeData(2, "Math", R.color.color_table_3, R.color.white, getMillis("2017-11-10 10:40:00"), getMillis("2017-11-10 11:45:00")));
            values2.add(new TimeData(3, "Science", R.color.color_table_4, R.color.white, getMillis("2017-11-10 15:00:00"), getMillis("2017-11-10 17:10:00")));
            values2.add(new TimeData(4, "Physics", R.color.color_table_5, R.color.white, getMillis("2017-11-10 17:30:00"), getMillis("2017-11-10 21:30:00")));
            values2.add(new TimeData(5, "Chemistry", R.color.color_table_6, R.color.white, getMillis("2017-11-10 21:31:00"), getMillis("2017-11-10 22:45:00")));
            values2.add(new TimeData(6, "Biology", R.color.color_table_7, R.color.white, getMillis("2017-11-10 23:00:00"), getMillis("2017-11-11 02:30:00")));

            ArrayList<TimeTableData> tables = new ArrayList<>();
            tables.add(new TimeTableData("Plan", values));
            tables.add(new TimeTableData("Do", values2));

            mLongSamples.addAll(tables);
        }
*/
/* 7칸 테이블 예제
        private void initShortSamples(){
            //TEST
            ArrayList<TimeData> values = new ArrayList<>();
            values.add(new TimeData(0, "Korean", R.color.color_table_1_light, getMillis("2017-11-10 04:00:00"), getMillis("2017-11-10 05:00:00")));
            values.add(new TimeData(1, "English", R.color.color_table_2_light, getMillis("2017-11-10 07:00:00"), getMillis("2017-11-10 08:00:00")));

            ArrayList<TimeData> values2 = new ArrayList<>();
            values2.add(new TimeData(0, "Korean", R.color.color_table_1_light, getMillis("2017-11-10 03:00:00"), getMillis("2017-11-10 06:00:00")));
            values2.add(new TimeData(1, "English", R.color.color_table_2_light, getMillis("2017-11-10 07:30:00"), getMillis("2017-11-10 08:30:00")));
            values2.add(new TimeData(2, "Math", R.color.color_table_3_light, getMillis("2017-11-10 11:40:00"), getMillis("2017-11-10 11:45:00")));
            values2.add(new TimeData(3, "Science", R.color.color_table_4_light, getMillis("2017-11-10 18:00:00"), getMillis("2017-11-10 18:10:00")));
            values2.add(new TimeData(4, "Physics", R.color.color_table_5_light, getMillis("2017-11-10 20:00:00"), getMillis("2017-11-10 21:30:00")));
            values2.add(new TimeData(5, "Chemistry", R.color.color_table_6_light, getMillis("2017-11-10 21:31:00"), getMillis("2017-11-10 22:45:00")));
            values2.add(new TimeData(6, "Biology", R.color.color_table_7_light, getMillis("2017-11-10 23:00:00"), getMillis("2017-11-11 02:30:00")));

            ArrayList<TimeTableData> tables = new ArrayList<>();
            tables.add(new TimeTableData("Sun", values));
            tables.add(new TimeTableData("Mon", values2));
            tables.add(new TimeTableData("Tue", values));
            tables.add(new TimeTableData("Wed", values2));
            tables.add(new TimeTableData("Thu", values));
            tables.add(new TimeTableData("Fri", values2));
            tables.add(new TimeTableData("Sat", values));

            mShortSamples.addAll(tables);
        }
*/
  /*      툴바 변경 메소드
           private void toogleMode() {
            btnMode.setActivated(!btnMode.isActivated());
        }
*/
        // =============================================================================
        // Date format
        // =============================================================================

        private long getMillis(String day){
            DateTime date = getDateTimePattern().parseDateTime(day);
            return date.getMillis();
        }

        private DateTimeFormatter getDateTimePattern(){
            return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        }

        // =============================================================================
        // Toast
        // =============================================================================
/* 토스트 메세지 메소드
        private void showToast(Activity activity, String msg){
            Toast toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            if( v != null) v.setGravity(Gravity.CENTER);
            toast.show();
        }
        */
    }
