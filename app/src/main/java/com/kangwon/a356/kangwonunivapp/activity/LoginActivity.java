package com.kangwon.a356.kangwonunivapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kangwon.a356.kangwonunivapp.R;
import com.kangwon.a356.kangwonunivapp.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.dataprocess.ProcessManager;
import com.kangwon.a356.kangwonunivapp.network.NetworkExecuteMessage;

/**
 * @author 노지현
 * @version 1
 * 이 클래스는 로그인을 담당하는 클래스이며
 * res/layout/login_layout.xml의 레이아웃들을 사용한다.
 */


public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    TextView id;
    TextView pw;


    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            MessageObject managerMsg = (MessageObject) msg.obj;
            if (managerMsg.getNEM().getNumber() == NetworkExecuteMessage.SUCCESS) {
                changeMainActivity();
            }
        }
    };
    ProcessManager manager = ProcessManager.getInstance(handler);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ActivityTools.makeFullScreen(this);

        id = (TextView) findViewById(R.id.loginInputID);
        pw = (TextView) findViewById(R.id.loginInputPW);





        loginButton = (Button) findViewById(R.id.loginLoginButton);
        loginButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.login(id.getText().toString(), pw.getText().toString());
                Log.i("LoginActivity", "login waiting");
            }
        }));
    }


    private void changeMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Log.i("LoginActivity", "login accessed");
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("LoginActivity", "onDestroyed");
        finish();

    }
}

