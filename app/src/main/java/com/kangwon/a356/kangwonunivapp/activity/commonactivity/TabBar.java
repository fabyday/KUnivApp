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


/**
 * @author 노지현
 * @version 1
 * @see TabBar.ButtonGruopInfo 이 내부 클래스는 동적 버튼들을 생성하기 위한 데이터 블록역할을 한다.
 * 이 클래스는 커스텀 메뉴를 만드는데 사용하는 리니어레이아웃을 상속받는 클래스이다.
 * 메뉴로 사용을 할 경우 root layout은 프레임 레이아웃을 사용해주어야 한다.
 */

public class TabBar extends LinearLayout {

    ///////리니어레이아웃 기초 속성///////////////////
    Context context;
    AttributeSet attr;
    ///////버튼 속성 부여용 변수//////////////////////
    ViewGroup.LayoutParams buttonLayoutPrams;
    private static int DEFAULT_FIRST_BUTTON_SIZE = 3;//최초 만들어지는 기본 버튼의 사이즈.
    ///////각 층별 버튼 목록//////////////////////////
    ArrayList<ButtonGruopInfo> widgetGroup;
    //////////////////////////////////////////////////


    /**
     * @param context 컨텍스트값
     * @param attr 속성값
     * 두 파라미터는 findViewById를 사용하여 받아오기 위한 생성자이다.
     * 자바 코드로 뷰를 생성할 것이 아니라면 사용에 염두할 필요는 없다.
     */
    public TabBar(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        widgetGroup = new ArrayList<>();
        this.setOrientation(VERTICAL);
        buttonLayoutPrams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
    }


    /**
     *addLayerInfo와 editLayerInfo가 끝났을 경우 한번 실행.
     */
    public void init()
    {
        int i;
        for(i = widgetGroup.size()-1 ; i>=0 ; i--)
        {
            widgetGroup.get(i).getPanel().setVisibility(GONE);
            this.addView(widgetGroup.get(i).getPanel());

        }
        widgetGroup.get(i+1).getPanel().setVisibility(VISIBLE);
    }

    /**
     *
     * @param Orientation 패널이 될 리니어레이아웃의 정렬방향
     * @param size 패널에 들어갈 버튼의 개수
     * @param buttonName 버튼에 들어갈 텍스트 리스트
     * @param Listener 버튼에 들어갈 리스너 리스트
     */



    /**
     * @author 노지현
     * @param Orientation 레이아웃의 위치 방향 1 세로, 0 가로
     * @param size 버튼의 개수
     * @param buttonName 버튼의 이름들 배열
     * @param Listener 버튼에 들어갈 리스너배열
     * init을 수행하기 전의 세팅을한다.
     */
    public void addLayerInfo(int Orientation, int size, String[] buttonName, OnClickListener[] Listener)
    {

        LinearLayout panelTmp = new LinearLayout(context, attr);
        if(!(Orientation == HORIZONTAL || Orientation ==VERTICAL))
                    panelTmp.setOrientation(Orientation);
        else
            panelTmp.setOrientation(HORIZONTAL);
        ButtonGruopInfo btnTmp= new ButtonGruopInfo(panelTmp, size, buttonName, Listener);
        widgetGroup.add(btnTmp);
    }

    public void addLayerInfo(int Orientation, int size, String[] buttonName)
    {

        LinearLayout panelTmp = new LinearLayout(context, attr);
        if(!(Orientation == HORIZONTAL || Orientation ==VERTICAL))
            panelTmp.setOrientation(Orientation);
        else
            panelTmp.setOrientation(HORIZONTAL);
        ButtonGruopInfo btnTmp= new ButtonGruopInfo(panelTmp, size, buttonName);
        widgetGroup.add(btnTmp);
    }

    public void editListener(int panelNumber, int[] index, OnClickListener Listener) throws ArrayIndexOutOfBoundsException
    {

        if(widgetGroup.size() <= panelNumber)
            throw new ArrayIndexOutOfBoundsException("없는 panel 번호입니다.");
        widgetGroup.get(panelNumber).setButtonOnClickListener(index, Listener);
    }


    public void editListener(int panelNumber, OnClickListener[] Listener) throws ArrayIndexOutOfBoundsException
    {
        if(widgetGroup.size() <= panelNumber)
            throw new ArrayIndexOutOfBoundsException("없는 버튼 번호입니다." + "panel number : " + panelNumber + " widgetGroup length :" + widgetGroup.size());
        widgetGroup.get(panelNumber).setButtonOnClickListener(Listener);
    }


    /**
     * @param panelNumber 0-n까지의 변경하고픈 패널 번호를 입력한다.
     * @param visibility GONE과 VISIBLE 만을 허용한다.
     * 원하는 패널 버튼 전체에 VISIBLE과 GONE 속성을 부여한다.
     */
    public void setChildVisivility(int panelNumber, int visibility)
    {
        if(widgetGroup.size() <= panelNumber)
            return;
        if(visibility == VISIBLE || visibility == GONE)
            widgetGroup.get(panelNumber).getPanel().setVisibility(visibility);

    }

    /**
     *
     * @param panelNumber 0-n 까지의 원하는 패널번호를 입력한다.
     * @return 0-n의 visibility를 반환하나,
     * 만일 범위를 초과했을 경우에는 가장 가까운 최소, 최대 패널의 visibility를 반환한다.
     */
    public int getChildVisivility(int panelNumber)
    {
        if(widgetGroup.size() > panelNumber || panelNumber >= 0)
            return widgetGroup.get(panelNumber).getPanel().getVisibility();

        else if(widgetGroup.size()<= panelNumber)
            return widgetGroup.size()-1;
        else
            return 0;
    }



    /**
     * @author 노지현
     * 이 private 내부 클래스는 button과 button을 담는 레이아웃, 버튼의 이름들을 가지는 정보클래스이다.
     * arraylist에 정보를 담아두는데 사용한다.
     * 기본적인 크기는 TabBar클래스의 DEFAULT_FIRST_BUTTON_SIZE 크기만큼 버튼을 저장을 한다.
     */

    private final class ButtonGruopInfo {
        private LinearLayout panel;
        private Button[] buttons;
        private String[] buttonNames = {"test1", "test2", "test3"};
        private int size = DEFAULT_FIRST_BUTTON_SIZE;


        public ButtonGruopInfo(LinearLayout layout, int size, String[] buttonNames)
        {
            setSize(size);
            this.panel = layout;
            setButtonName(buttonNames);
            makeButton();
        }

        public ButtonGruopInfo(LinearLayout layout, int size, String[] buttonNames, OnClickListener[] Listener)
        {
            this(layout, size, buttonNames);
            if(Listener != null)
                setButtonOnClickListener(Listener);

        }

        /**
         * 내부적으로만 호출되는 크기를 정해주는 메소드.
         * 만일 사이즈가 0보다 작을 경우
         */
        private void setSize(int size) {
            if (size < 0)
                return;

            this.size = size;
        }

        /**
         * 내부적으로 버튼을 만드는 메소드이다. 이 메소드는 각 레이어 객체당 한번만 사용이 가능하다.
         * 실제적으로 초기 엑티비티에서 사용하고 나머지 액티비티들이 이 객체들을 받아서 사용하는 구조를 가지고 있다.
         */
        private void makeButton() {

            buttons = new Button[size];

            for (int i = 0; i < size; i++) {
                buttons[i] = new Button(context);
                buttons[i].setText(buttonNames[i]);
                panel.addView(buttons[i], buttonLayoutPrams);
            }

        }
        /**
         * @author 노지현
         * @param buttonNames String 타입의 배열이다.
         * @throws NullPointerException 만일 String 자체나 String일 경우 예외를 발생한다.
         * 제목을 가지고 초기화를 진행한다.
         */
        public void setButtonName(String[] buttonNames) throws NullPointerException
        {
            if(buttonNames == null)
                throw new NullPointerException("String 배열자체가 널입니다.");
            for(int i = 0; i<buttonNames.length-1; i++)
                if(buttonNames[i]==null)
                    throw new NullPointerException("배열에 NULL이 포함되어 있습니다.");
            this.buttonNames = buttonNames;
        }

        /**
         * @author 노지현
         * @param index 설정하려는 버튼 번호의 인덱스들의 리스트
         * @param Listener 버튼에 설정할 리스너
         * 각기 다른 번호의 버튼에 동일한 리스너를 등록한다.
         */
        public void setButtonOnClickListener(int[] index, OnClickListener Listener) throws ArrayIndexOutOfBoundsException
        {
            if(index.length > size)
                throw new ArrayIndexOutOfBoundsException("인덱스의 개수가 버튼의 개수보다 많습니다.");
            for(int i : index)
                if(i < 0 || i>=size )
                    throw new ArrayIndexOutOfBoundsException("존재하지 않는 버튼 번호를 넣었습니다.");

            for(int i : index)
                buttons[i].setOnClickListener(Listener);
        }

        /**
         * @author 노지현
         * @param Listener 버튼에 설정할 리스너
         * 모든 버튼의 각 인덱스에 일치하는 인덱스를 가진 Listner를 등록해준다.
         */
        public void setButtonOnClickListener(OnClickListener[] Listener) throws ArrayIndexOutOfBoundsException
        {
            if(Listener.length > size)
                throw new ArrayIndexOutOfBoundsException("리스너의 개수가 버튼의 개수보다 많습니다.");

            for(int i=0; i<Listener.length; i++)
                buttons[i].setOnClickListener(Listener[i]);
        }

        /**
         *
         * @param index 버튼의 인덱스이다. (0-n 범위)
         * @param weight 주고자 하는 가중치의 크기이다.
         * @throws ArrayIndexOutOfBoundsException 만일 범위를 넘길경우 예외를 발생시킨다. Runtime error
         * 원하는 크기의 weight을 줄 수 있다.
         */
        public void setButtonWeight(int index, int weight) throws ArrayIndexOutOfBoundsException
        {
            if(index < 0 || index>=size )
            {
                throw new ArrayIndexOutOfBoundsException("존재하지 않는 버튼 번호를 넣었습니다.");
            }
            buttons[index].setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT, weight));
        }



        /**
         * 패널을 보여준다.
         */
        public LinearLayout getPanel()
        {
            return panel;
        }

    }

}
