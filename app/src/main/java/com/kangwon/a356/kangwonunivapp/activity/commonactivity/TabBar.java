package com.kangwon.a356.kangwonunivapp.activity.commonactivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kangwon.a356.kangwonunivapp.R;

public class TabBar extends LinearLayout {
    Context context;
    Button[] menuButton;
    private static int SIZE = 4;

    public TabBar(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tab_bar, this, true);

        makeButton();

    }

    /**
     * @param size 들어갈 버튼의 개수 설정
     * @author 노지현
     */
    public static void setSize(int size) {
        if (size < 0)
            return;

        SIZE = size;
    }

    public static int getSize() {
        return SIZE;
    }

    private void makeButton() {
        menuButton = new Button[SIZE];

        for (int i = 0; i < SIZE; i++) {
            menuButton[i] = new Button(context);
            menuButton[i].setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            menuButton[i].setText("test setter");
            this.addView(menuButton[i]);

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
}
