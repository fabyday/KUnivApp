package com.kangwon.a356.kangwonunivapp.database.layoutdataset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kangwon.a356.kangwonunivapp.R;
import com.kangwon.a356.kangwonunivapp.database.ClassInfo;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListItem> listViewItemList = new ArrayList<ListItem>();

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView classnameView = (TextView) convertView.findViewById(R.id.classname);
        TextView instructorView = (TextView) convertView.findViewById(R.id.instructor);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영

        classnameView.setText(listViewItem.getClassname());
        instructorView.setText(listViewItem.getInstructor());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String classname, String instructor) {
        ListItem item = new ListItem(classname, instructor);
        listViewItemList.add(item);
    }


    public void removeItem(int postion)
    {
        listViewItemList.remove(postion-1);
    }

    public void addItem(ClassInfo[] classInfos)
    {
        listViewItemList = new ArrayList<>();
        int size = classInfos.length;
        for(int i = 0; i<size; i++)
        {
            listViewItemList.add(new ListItem(classInfos[i].getClassName(), classInfos[i].getInstructor()));
        }
    }


}
