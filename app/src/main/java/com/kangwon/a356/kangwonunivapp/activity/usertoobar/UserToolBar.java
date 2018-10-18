package com.kangwon.a356.kangwonunivapp.activity.usertoobar;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.widget.Button;


/**
 * @autor 노지현
 * @version 1
 * 뷰의 하단에 사용될 메뉴바를 만드는데 사용되는 클래스이다.
 */
public class UserToolBar extends Toolbar {

    private static final int BUTTONSIZE=3;
    private static final int SUBBUTTONSIZE=3;
    Button[] buttons;
    Button[] subButtons;

    public UserToolBar(Context context)
    {
        super(context);
        buttons= new Button[3];
        subButtons = new Button[3];
        for(int i = 0; i<BUTTONSIZE; i++)
        {
            return; // for github test
        }

    }




}
