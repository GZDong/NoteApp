package com.gaozhidong.android.noteapp.Model;

import android.content.Context;
import android.os.Environment;

import com.gaozhidong.android.noteapp.ServiceResultEntity.LoginResult;
import com.gaozhidong.android.noteapp.Util.DateUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zhidong on 2017/9/19.
 */

public class NotesLab {

    private static NotesLab sNotesLab;
    private Context mContext;
    private List<NoteBody> mBodyList;

    private NotesLab(Context context) {
        mContext = context.getApplicationContext();
        mBodyList = new ArrayList<>();
        initBodyList();
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
    }

    public void setBodyList(LoginResult results){
        List<NoteBody> list = new ArrayList<>();
        List<LoginResult.Body> bodies = results.getBodies();
        for (LoginResult.Body body : bodies){
            NoteBody noteBody = NoteBody.Builder()
                    .setNoteId(Integer.parseInt(body.getNoteid()))
                    .setText(body.getNotedata())
                    .setTime(body.getNotetime())
                    .setAccount(body.getNoteaccount())
                    .setCalendar(Calendar.getInstance())
                    .create();
            list.add(noteBody);
        }
        if (list != null){
            mBodyList = list;
        }
    }
    public void updateNote(int noteId,String noteData,String noteTime){
        for (NoteBody noteBody1 : mBodyList){
            if (noteBody1.getNoteId() == noteId){
                noteBody1.setText(noteData);
                noteBody1.setTime(noteTime);
                break;
            }
        }
    }
}
