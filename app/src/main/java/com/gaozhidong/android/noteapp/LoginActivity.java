package com.gaozhidong.android.noteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.litepal.tablemanager.Connector;

public class LoginActivity extends AppCompatActivity{

    private Button mSignBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Connector.getDatabase();
        initView();
        initAction();
    }
    void initView(){
        mSignBtn = (Button) findViewById(R.id.sign_in_button);
    }
    void initAction(){
        mSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = NoteListActivity.newInstance(LoginActivity.this);
                startActivity(intent);
            }
        });
    }
}

