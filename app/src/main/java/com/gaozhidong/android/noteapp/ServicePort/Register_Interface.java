package com.gaozhidong.android.noteapp.ServicePort;

import com.gaozhidong.android.noteapp.ServiceResultEntity.RegisterResult;
import com.gaozhidong.android.noteapp.Util.InterfaceConst;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zhidong on 2017/9/20.
 */

public interface Register_Interface {
    @POST(InterfaceConst.REGISTER)
    @FormUrlEncoded
    Observable<RegisterResult> getObservable(@Field(InterfaceConst.ACCOUNT) String account,@Field(InterfaceConst.PASSWORD)String password);
}
