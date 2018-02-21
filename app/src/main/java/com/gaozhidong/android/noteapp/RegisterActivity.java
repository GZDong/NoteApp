package com.gaozhidong.android.noteapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gaozhidong.android.noteapp.ServicePort.Register_Interface;
import com.gaozhidong.android.noteapp.ServiceResultEntity.RegisterResult;
import com.gaozhidong.android.noteapp.Util.InterfaceConst;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    private static String TAG = "test";
    private AutoCompleteTextView mAccountText;
    private EditText mPasswordText;
    private EditText mAPasswordText;
    private Button mRBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mRBtn = (Button) findViewById(R.id.register_button);
        mAccountText = (AutoCompleteTextView) findViewById(R.id.account);
        mPasswordText = (EditText) findViewById(R.id.password);
        mAPasswordText = (EditText) findViewById(R.id.password_again);
        mRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mAccountText.getText())){
                    Toast.makeText(RegisterActivity.this,"账号不能为空!",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(mPasswordText.getText())||TextUtils.isEmpty(mAPasswordText.getText())){
                    Toast.makeText(RegisterActivity.this,"密码不能为空!",Toast.LENGTH_SHORT).show();
                }else if (!mPasswordText.getText().toString().equals(mAPasswordText.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"密码不一致!",Toast.LENGTH_SHORT).show();
                }else {
                    String account = mAccountText.getText().toString();
                    String password = mPasswordText.getText().toString();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(InterfaceConst.HOST)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Register_Interface register_interface = retrofit.create(Register_Interface.class);
                    Observable<RegisterResult> observable = register_interface.getObservable(account,password);
                    observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<RegisterResult>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e(TAG, "onError: " + e );
                                }

                                @Override
                                public void onNext(RegisterResult registerResult) {
                                    if (registerResult == null){
                                        Toast.makeText(RegisterActivity.this,"账号已经存在！",Toast.LENGTH_LONG).show();
                                    }else {
                                        Log.e(TAG, "onNext: " + registerResult.getResult());
                                        Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                            });
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
