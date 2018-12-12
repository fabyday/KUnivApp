package com.kangwon.a356.kangwonunivapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kangwon.a356.kangwonunivapp.R;
import com.kangwon.a356.kangwonunivapp.dataprocess.database.MessageObject;
import com.kangwon.a356.kangwonunivapp.dataprocess.ProcessManager;
import com.kangwon.a356.kangwonunivapp.dataprocess.network.NetworkExecuteMessage;

public class SignUpActivity extends AppCompatActivity {


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            MessageObject managerMsg = (MessageObject) msg.obj;
            System.out.println(((NetworkExecuteMessage)managerMsg.getProcessedData()).getNumber());
            if (((NetworkExecuteMessage)managerMsg.getProcessedData()).getNumber() == NetworkExecuteMessage.SUCCESS) {
                onBackPressed();
            }
            else
                Toast.makeText(SignUpActivity.this, "회원가입에 실패하였습니다. 동일한 ID가 존재합니다.", Toast.LENGTH_LONG).show();

        }
    };

    ProcessManager processManager = ProcessManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        final EditText signUpName = (EditText)findViewById(R.id.signUpName);
        final EditText signUpID = (EditText)findViewById(R.id.signUpID);
        final EditText signUpPW = (EditText)findViewById(R.id.signUpPW);
        Button createButton = (Button)findViewById(R.id.CreateButton);



        createButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processManager.signin(signUpID.getText().toString(), signUpName.getText().toString(), signUpPW.getText().toString(), handler);
                Toast.makeText(SignUpActivity.this, "회원가입 중 입니다...", Toast.LENGTH_LONG).show();
            }
        }));
    }

    @Override
    protected void onDestroy() {
        System.out.println("Signin destoryer");
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
