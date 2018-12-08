package com.kangwon.a356.kangwonunivapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.kangwon.a356.kangwonunivapp.R;
import com.kangwon.a356.kangwonunivapp.activity.commonactivity.MessageListenable;
import com.kangwon.a356.kangwonunivapp.activity.commonactivity.TabBar;
import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.dataprocess.ProcessManager;

import java.util.Map;

/**
 * @author 노지현
 * @version 1
 * 이 클래스는 메인 화면을 담당하는 activity이다.
 * res/layout/activity_main.xml을 사용한다.
 */
public class MainActivity extends AppCompatActivity {


    public FragmentManager fmng;
    public FragmentTransaction transaction;
    public MessageListenable messageListener;
    ProcessManager processManager= ProcessManager.getInstance();

    public final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            MessageObject protoMessage = (MessageObject) msg.obj;

            if (messageListener == null)
                return;
            messageListener.update(protoMessage);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityTools.makeFullScreen(this);

        ImageButton button1 = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton button2 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton button3 = (ImageButton) findViewById(R.id.imageButton3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fmng = getSupportFragmentManager();
                transaction = fmng.beginTransaction();
                transaction.add(R.id.frameLayout, new TimetableActivity());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fmng = getSupportFragmentManager();
                transaction = fmng.beginTransaction();
                transaction.add(R.id.frameLayout, new StudentListActivity());
                transaction.addToBackStack(null);
                transaction.commit();
                processManager.updateRequest(MessageObject.ALL_LIST, handler);
                System.out.println("eh------------------------------asdadasdada");

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fmng = getSupportFragmentManager();
                transaction = fmng.beginTransaction();
                transaction.add(R.id.frameLayout, new InstructorLsitActivity());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
/////////////////////////////////////////////////


        final TabBar tabBar = (TabBar) findViewById(R.id.tabbar);

        tabBar.addLayerInfo(0, 3, new String[]{"홈", "즐겨찾기", "로그아웃"}, null);
        tabBar.addLayerInfo(0, 3, new String[]{"empty", "empty", "empty"}, null);
        tabBar.editListener(0, new View.OnClickListener[]{new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (fmng.popBackStackImmediate()) ;
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tabBar.getChildVisivility(1) == View.GONE)
                    tabBar.setChildVisivility(1, View.VISIBLE);
                else
                    tabBar.setChildVisivility(1, View.GONE);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Toast.makeText(MainActivity.this, "로그아웃", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        }});
        tabBar.init();
    }

    //뒤로 가기 버튼 기능 제거
    @Override
    public void onBackPressed() {
        fmng.popBackStack();
    }

    /**
     * 처리된 데이터를 보내준다.
     */
    public void add(MessageListenable listenable) {
        this.messageListener = listenable;
    }

    public FragmentTransaction getTransaction() {
        return transaction;
    }

}
