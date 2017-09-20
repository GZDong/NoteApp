package com.gaozhidong.android.noteapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.litepal.tablemanager.Connector;

public class LoginActivity extends AppCompatActivity{

    private Button mSignBtn;
    private static final String p1 = "android.permission.READ_EXTERNAL_STORAGE";
    private static final String p2 = "android.permission.WRITE_EXTERNAL_STORAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Connector.getDatabase();
        initView();
        initAction();
        if (ContextCompat.checkSelfPermission(this,p1) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,p2) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{p1,p2},1);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_register){
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

