package com.gaozhidong.android.noteapp.ServicePort;

import com.gaozhidong.android.noteapp.ServiceResultEntity.LoginResult;
import com.gaozhidong.android.noteapp.Util.InterfaceConst;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhidong on 2017/9/20.
 */

public interface Login_Interface {

    @POST(InterfaceConst.SIGN)
    @FormUrlEncoded
    Observable<List<LoginResult>> getObservable(@Field(InterfaceConst.ACCOUNT) String account, @Field(InterfaceConst.PASSWORD) String password);
}
