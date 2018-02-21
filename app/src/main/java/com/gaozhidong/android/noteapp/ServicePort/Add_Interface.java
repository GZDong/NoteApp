package com.gaozhidong.android.noteapp.ServicePort;

import com.gaozhidong.android.noteapp.ServiceResultEntity.RegisterResult;
import com.gaozhidong.android.noteapp.Util.InterfaceConst;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhidong on 2017/9/20.
 */

public interface Add_Interface {
    @POST(InterfaceConst.ADD)
    @FormUrlEncoded
    Observable<RegisterResult> getObservable(@Field("noteid")String noteid,@Field("notedata")String notedata,@Field("notetime")String notetime,
                                             @Field("noteaccount")String noteaccount);
}
