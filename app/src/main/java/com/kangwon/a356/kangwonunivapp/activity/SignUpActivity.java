package com.kangwon.a356.kangwonunivapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kangwon.a356.kangwonunivapp.R;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        EditText signUpName = (EditText)findViewById(R.id.signUpName);
        EditText signUpID = (EditText)findViewById(R.id.signUpID);
        EditText signUpPW = (EditText)findViewById(R.id.signUpPW);
        Button createButton = (Button)findViewById(R.id.CreateButton);


        createButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUpActivity.this, "회원가입이 되셨습니다.", Toast.LENGTH_LONG).show();
            }
        }));
    }
}
