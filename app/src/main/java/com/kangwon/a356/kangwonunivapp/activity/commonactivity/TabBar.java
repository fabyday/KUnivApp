package com.kangwon.a356.kangwonunivapp.activity.commonactivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kangwon.a356.kangwonunivapp.R;

import java.util.ArrayList;

public class TabBar extends LinearLayout {
    Context context;
    ArrayList<LinearLayout> panel; //프레임 위에 올라가는 패널

    Button[] menuButton;
    private static int SIZE = 3;
    private String[] buttonName={"test1","test2","test3"};
    ViewGroup.LayoutParams buttonLayoutPrams;

    public TabBar(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        panel = new ArrayList<>();
        panel.add(new LinearLayout(context, attr));
        this.setOrientation(VERTICAL);
        addView(panel.get(0));
    }

    /**
     * @author 노지현
     * @param Orientation 레이아웃의 위치 방향 1 세로, 0 가로
     * @param size 버튼의 개수
     * @param buttonName 버튼의 이름들 배열
     * @param Listener 버튼에 들어갈 리스너배열
     */
    public void init(int Orientation, int size, String[] buttonName, OnClickListener[] Listener)
    {
        if(!(Orientation == 1 || Orientation ==0))
            panel.get(0).setOrientation(HORIZONTAL);
        else
            this.setOrientation(Orientation);
        this.setSize(size);
        setButtonName(buttonName);
        makeButton();
    }

    /**
     * @author 노지현
     * @param size 들어갈 버튼의 개수 설정
     * 전부 공통인 메뉴바를 써야함. 따라서 정적 함수이다.
     */
    public static void setSize(int size) {
        if (size < 0)
            return;

        SIZE = size;
    }

    public static int getSize() {
        return SIZE;
    }

    public void setButtonWeight(int index, int weight)
    {
        menuButton[index].setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT, weight));
    }



    private void makeButton() {

        menuButton = new Button[SIZE];
        buttonLayoutPrams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);

        for (int i = 0; i < SIZE; i++) {
            menuButton[i] = new Button(context);
            menuButton[i].setText(buttonName[i]);
            addView(menuButton[i], buttonLayoutPrams);
        }

    }

    /**
     * @author 노지현
     * @param index 설정하려는 버튼 번호의 인덱스들의 리스트
     * @param Listener 버튼에 설정할 리스너
     */
    public void setButtonOnClickListener(int[] index, OnClickListener Listener) throws ArrayIndexOutOfBoundsException
    {
        for(int i : index)
            if(i < 0 || i>=SIZE )
                throw new ArrayIndexOutOfBoundsException("존재하지 않는 버튼 번호를 넣었습니다.");

        for(int i : index)
            menuButton[i].setOnClickListener(Listener);
    }


    /**
     * @author 노지현
     * @param buttonName String 타입의 배열이다.
     * @throws NullPointerException 만일 String 자체나 String일 경우 예외를 발생한다.
     * 제목을 가지고 초기화를 진행한다.
     */
    public void setButtonName(String[] buttonName) throws NullPointerException
    {
        if(buttonName == null)
            throw new NullPointerException("String 배열자체가 널입니다.");
        for(int i = 0; i<buttonName.length-1; i++)
            if(buttonName[i]==null)
                throw new NullPointerException("배열에 NULL이 포함되어 있습니다.");
        this.buttonName = buttonName;
    }





}
