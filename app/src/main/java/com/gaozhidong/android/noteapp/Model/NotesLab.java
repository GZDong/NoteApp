package com.gaozhidong.android.noteapp.Model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.gaozhidong.android.noteapp.ServicePort.Add_Interface;
import com.gaozhidong.android.noteapp.ServicePort.Delete_Interface;
import com.gaozhidong.android.noteapp.ServicePort.Update_Interface;
import com.gaozhidong.android.noteapp.ServiceResultEntity.LoginResult;
import com.gaozhidong.android.noteapp.ServiceResultEntity.RegisterResult;
import com.gaozhidong.android.noteapp.Util.DateUtils;
import com.gaozhidong.android.noteapp.Util.InterfaceConst;
import com.gaozhidong.android.noteapp.Util.LogUtil;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhidong on 2017/9/19.
 */

public class NotesLab {

    private static NotesLab sNotesLab;
    private Context mContext;
    private List<NoteBody> mBodyList;
    private int maxId;
    public static final String TAG = "Lab";

    private NotesLab(Context context) {
        mContext = context.getApplicationContext();
        mBodyList = new ArrayList<>();
        initBodyList();
        maxId = getMaxId();
    }

    public static NotesLab get(Context context) {
        if (sNotesLab == null) {
            synchronized (NotesLab.class) {
                if (sNotesLab == null) {
                    sNotesLab = new NotesLab(context);
                }
            }
        }
        return sNotesLab;
    }

    private void initBodyList() {

            NoteBody noteBody = NoteBody.Builder()
                    .setNoteId(1)
                    .setAccount("高志栋")
                    .setTime(DateUtils.getNowStrDate())
                    .setCalendar(Calendar.getInstance())
                    .setText("明天就是答辩了！后天解放！")
                    .create();
            mBodyList.add(noteBody);

        NoteBody noteBody2 = NoteBody.Builder()
                .setNoteId(2)
                .setAccount("高志栋")
                .setTime(DateUtils.getNowStrDate())
                .setCalendar(Calendar.getInstance())
                .setText("记得带钥匙")
                .create();
        mBodyList.add(noteBody2);

        NoteBody noteBody3 = NoteBody.Builder()
                .setNoteId(3)
                .setAccount("高志栋")
                .setTime(DateUtils.getNowStrDate())
                .setCalendar(Calendar.getInstance())
                .setText("明天下午要去见客户，记得把文档带上，客户要看")
                .create();
        mBodyList.add(noteBody3);

        NoteBody noteBody4 = NoteBody.Builder()
                .setNoteId(4)
                .setAccount("高志栋")
                .setTime(DateUtils.getNowStrDate())
                .setCalendar(Calendar.getInstance())
                .setText("10点半有场球赛要看")
                .create();
        mBodyList.add(noteBody4);

        NoteBody noteBody5 = NoteBody.Builder()
                .setNoteId(5)
                .setAccount("高志栋")
                .setTime(DateUtils.getNowStrDate())
                .setCalendar(Calendar.getInstance())
                .setText("回去的时候记得买水！买完水回到老地方，把水给那个人，然后回头再把水瓶返回去。")
                .create();
        mBodyList.add(noteBody5);

        NoteBody noteBody6 = NoteBody.Builder()
                .setNoteId(6)
                .setAccount("高志栋")
                .setTime(DateUtils.getNowStrDate())
                .setCalendar(Calendar.getInstance())
                .setText("跟我到成都的街头走一走")
                .create();
        mBodyList.add(noteBody6);

        NoteBody noteBody7 = NoteBody.Builder()
                .setNoteId(7)
                .setAccount("高志栋")
                .setTime(DateUtils.getNowStrDate())
                .setCalendar(Calendar.getInstance())
                .setText("回去之后记得大扫除一波，不然的宿舍太脏了，住了会很不舒服的")
                .create();
        mBodyList.add(noteBody7);

    }
    public List<NoteBody> getBodyList(){
        return mBodyList;
    }

    public File getPhotoFile(NoteBody noteBody){
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null){
            return null;
        }

        return new File(externalFilesDir, noteBody.getPhotoFilename());
    }

    public void addNote(NoteBody noteBody){
        mBodyList.add(noteBody);
        int noteid = noteBody.getNoteId();
        String notedata = noteBody.getText();
        String notetime = noteBody.getTime();
        String noteaccount = noteBody.getAccount();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(InterfaceConst.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Add_Interface add_interface = retrofit.create(Add_Interface.class);
        Observable<RegisterResult> observable = add_interface.getObservable(noteid+"",notedata,notetime,noteaccount);
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
                        if (registerResult!=null){
                            Log.e(TAG, "onNext: 数据添加成功！" + registerResult.getResult());
                        }
                    }
                });
    }

    public void setBodyList(List<LoginResult> results){
        List<NoteBody> list = new ArrayList<>();
        for (LoginResult result : results){
            NoteBody noteBody = NoteBody.Builder()
                    .setNoteId(Integer.parseInt(result.getNoteid()))
                    .setText(result.getNotedata())
                    .setTime(result.getNotetime())
                    .setAccount(result.getNoteaccount())
                    .setCalendar(Calendar.getInstance())
                    .create();
            list.add(noteBody);
        }
        if (list != null){
            mBodyList = list;
        }
    }
    public void setNullList(){
        mBodyList.clear();
    }
    public void updateNote(int noteId,String noteData,String noteTime){
        for (NoteBody noteBody1 : mBodyList){
            if (noteBody1.getNoteId() == noteId){
                noteBody1.setText(noteData);
                noteBody1.setTime(noteTime);
                break;
            }
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(InterfaceConst.HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Update_Interface update_interface = retrofit.create(Update_Interface.class);
        Observable<RegisterResult> observable = update_interface.getObservable(noteId+"",noteData,noteTime);
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
                        Log.e(TAG, "onNext: 修改成功！" +registerResult.getResult() );
                    }
                });
    }
    public NoteBody queryNote(int noteId){
        for (NoteBody noteBody: mBodyList){
            if (noteBody.getNoteId() == noteId){
                return noteBody;
            }
        }
        return mBodyList.get(1);
    }

    public void updateFlag(int noteId,int flag){
        for (NoteBody noteBody: mBodyList){
            if (noteBody.getNoteId() == noteId){
                noteBody.setFlag(flag);
                break;
            }
        }
    }

    public void printList(){
        for (NoteBody noteBody : mBodyList){
            LogUtil.e("test",noteBody.getNoteId() + ";" + "\n");
        }
    }

    private int getMaxId(){
        NoteBody noteBody = mBodyList.get(0);
        int max = noteBody.getNoteId();
        for (NoteBody noteBody1 : mBodyList){
            if (max <= noteBody1.getNoteId()){
                max = noteBody1.getNoteId();
            }
        }
        return max;
    }
    public int setMaxIdAndGetIt(){
        int max = 0;
        if (mBodyList !=null && mBodyList.size() > 0){
            for (int i=0;i<mBodyList.size();i++){
                if (mBodyList.get(i).getNoteId() >= max){
                    max = mBodyList.get(i).getNoteId();
                }
            }
            max ++;
        }else {
            max = 1;
        }

        return max;
    }

    public List<String> getPicPaths(int id){
        List<String> pathlist = new ArrayList<>();
        List<Pictures> pics = getPics(id);
        if (pics != null && pics.size() > 0){
            for (Pictures pictures : pics){
                pathlist.add(pictures.getPath());
            }
        }
        return pathlist;
        /*for (NoteBody noteBody : mBodyList){
            if (noteBody.getNoteId() == id){
                File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                String path = externalFilesDir + File.separator + noteBody.getPhotoFilename();
                pathlist.add(path);
            }
        }
        return pathlist;*/
    }

    public List<Pictures> getPics(int id){
        List<Pictures> list = DataSupport.where("noteid = ?",Integer.toString(id)) .find(Pictures.class);
        return list;
    }
    public void deleteNote(int noteId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(InterfaceConst.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Delete_Interface delete_interface = retrofit.create(Delete_Interface.class);
        Observable<RegisterResult> observable = delete_interface.getObservable(noteId+"");
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
                        Log.e(TAG, "onNext: 删除成功！" + registerResult.getResult() );
                    }
                });
    }
}
