package com.gaozhidong.android.noteapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.gaozhidong.android.noteapp.Model.NotesLab;
import com.gaozhidong.android.noteapp.ServicePort.Login_Interface;
import com.gaozhidong.android.noteapp.ServiceResultEntity.LoginResult;
import com.gaozhidong.android.noteapp.Util.InterfaceConst;
import com.google.gson.Gson;

import org.litepal.tablemanager.Connector;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity{
    private static final String TAG = "test";

    private Button mSignBtn;
    private static final String p1 = "android.permission.READ_EXTERNAL_STORAGE";
    private static final String p2 = "android.permission.WRITE_EXTERNAL_STORAGE";
    private AutoCompleteTextView mAccountTV;
    private EditText mPasswordET;

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
        mAccountTV = (AutoCompleteTextView) findViewById(R.id.account);
        mPasswordET = (EditText) findViewById(R.id.password);
    }
    void initAction(){
        mSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Log.e(TAG, "onClick: " + mAccountTV.getText() + "\n" + mPasswordET.getText() );
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(InterfaceConst.HOST)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Login_Interface login_interface = retrofit.create(Login_Interface.class);
                Observable<LoginResult> observable = login_interface.getObservable(mAccountTV.getText().toString(),mPasswordET.getText().toString());
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<LoginResult>() {
                            @Override
                            public void onCompleted() {
                                Log.e(TAG, "onCompleted: " );
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onError: " ,e);
                            }

                            @Override
                            public void onNext(LoginResult loginResult) {
                                Log.e(TAG, "onNext: " );
                                if (loginResult != null){
                                    NotesLab.get(LoginActivity.this).setBodyList(loginResult);
                                }
                            }
                        });*/
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

