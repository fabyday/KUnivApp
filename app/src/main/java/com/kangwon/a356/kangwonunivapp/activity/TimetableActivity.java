package com.kangwon.a356.kangwonunivapp.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.eunsiljo.timetablelib.data.TimeData;
import com.github.eunsiljo.timetablelib.data.TimeGridData;
import com.github.eunsiljo.timetablelib.data.TimeTableData;
import com.github.eunsiljo.timetablelib.view.TimeTableView;
import com.github.eunsiljo.timetablelib.viewholder.TimeTableItemViewHolder;
import com.kangwon.a356.kangwonunivapp.R;
import com.kangwon.a356.kangwonunivapp.activity.commonactivity.MessageListenable;
import com.kangwon.a356.kangwonunivapp.database.ClassInfo;
import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.database.TimeSpaceInfo;
import com.kangwon.a356.kangwonunivapp.database.TimeTableInfo;
import com.kangwon.a356.kangwonunivapp.network.NetworkExecuteMessage;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author 노지현
 * @version 1
 *
 * mNow는 Date 타입을 Long으로 바꾼것. 따라서 0은 1970년을 의미한다.
 * 이걸 이용한다면 이미 끝난 강의는 사라지게 만들 수 있으나, 파싱하는 것이 까다로운 관계로 생략한다.
 * res/layout/timetable_layout.xml의 레이아웃들을 다룬다.
 */

public class TimetableActivity extends Fragment implements MessageListenable {

    public static final int STUDENT_TIMETABLE = 1;


    //View btnMode; //툴바 2칸 ,7칸 변경 버튼
    TimeTableView timeTable;
    ImageButton attendanceBtn;

    TimeTableInfo timeTableInfo;
    ClassInfo[] classInfo;

    LinearLayout Sliding;
    List<String> mShortHeaders = Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
    long mNow = 0;


    int clickedClassIndex=0;

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
        Sliding = (LinearLayout) getView().findViewById(R.id.Sliding);
        attendanceBtn = (ImageButton) getView().findViewById(R.id.attendanceBtn);  // 출석인증 버튼
        initLayout();
        initListener();
        initData();


        ((MainActivity)getActivity()).add(this);
        ((MainActivity) getActivity()).processManager.updateRequest(MessageObject.STUDENT_TIMETABLE_TYPE, ((MainActivity) getActivity()).handler);


    }

    private void initLayout() {
        // btnMode = getView().findViewById(R.id.btnMode);
        timeTable = (TimeTableView) getView().findViewById(R.id.timeTable);
    }

    private void initListener() {

        timeTable.setOnTimeItemClickListener(new TimeTableItemViewHolder.OnTimeItemClickListener() {
            @Override
            public void onTimeItemClick(View view, int position, TimeGridData item) {  // 강의 눌렸을 때 슬라이드 올라오게
                Sliding.callOnClick();
                TimeData indexData=item.getTime();
                clickedClassIndex = (int)indexData.getKey();
                System.out.println("인덱스 값 : " + clickedClassIndex);
            }
        });

        attendanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                AttendanceActivity attrActivity = new AttendanceActivity();
                transaction.add(R.id.frameLayout, attrActivity);
                transaction.addToBackStack(null);
                attrActivity.setClassInfo(classInfo[clickedClassIndex]);
                transaction.commit();
            }
        });
    }

    private void initData() {

        timeTable.setStartHour(0);
        timeTable.setShowHeader(true);
        timeTable.setTableMode(TimeTableView.TableMode.SHORT);

        DateTime now = DateTime.now();
        mNow = now.withTimeAtStartOfDay().getMillis();
        //timeTable.setTimeTable(mNow, getSamples(mNow, mShortHeaders, )); //데이터를 셋팅
    }



    private ArrayList<TimeTableData> getSamples(List<String> headers, ClassInfo[] titles) {
        TypedArray colors_table_light = getResources().obtainTypedArray(R.array.colors_table_light);  // 강의 배경색 연한 것
        ArrayList<TimeTableData> tables = new ArrayList<>();

        int headerSize = headers.size();
        ArrayList<TimeData>[] values = new ArrayList[headerSize];
        for(int i =0; i< headerSize; i++)
        {
            values[i] = new ArrayList<>();
        }
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            TimeSpaceInfo[] timeSpaceInfos = titles[i].getTimeInfo();
            int timeLength = timeSpaceInfos.length;
            for (int j = 0; j < timeLength; j++) {
                DateTime start = new DateTime(timeSpaceInfos[j].getStartTimeInteger());
                DateTime end = new DateTime(timeSpaceInfos[j].getEndTimeInteger());

                int color = colors_table_light.getResourceId(j, 0);
                int textColor = R.color.black;

                int targetHeader = TimeSpaceInfo.DAY_STRING_TO_INTEGER.toInteger(timeSpaceInfos[j].getDay()); //헤더의 배열 인덱스 값
                System.out.println("열열열 : " + targetHeader);
                TimeData timeData = new TimeData(i , titles[i].getClassName() + "\n" + timeSpaceInfos[j].getClassRoom(), color, textColor, start.getMillis(), end.getMillis());
                values[targetHeader].add(timeData);
            }
        }

        int weekLength = values.length;
        for(int i=0; i< weekLength; i++)
        {
            tables.add(new TimeTableData(headers.get(i), values[i]));
        }


        return tables;
    }
    // =============================================================================
    // Date format
    // =============================================================================

    private long getMillis(String day) {
        DateTime date = getDateTimePattern().parseDateTime(day);
        return date.getMillis();
    }

    private DateTimeFormatter getDateTimePattern() {
        return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    }


    @Override
    public void update(MessageObject msg) {
        Object temp = msg.getProcessedData();
        if (temp instanceof NetworkExecuteMessage) {
            Toast.makeText(getActivity(), ((NetworkExecuteMessage) temp).getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            System.out.println(msg.toGETMessage());
            timeTableInfo = (TimeTableInfo) temp;
            classInfo = timeTableInfo.getClassInfo();
            Arrays.sort(classInfo);
            timeTable.setTimeTable(0, getSamples(mShortHeaders, classInfo)); //데이터를 셋팅
        }
    }
}
