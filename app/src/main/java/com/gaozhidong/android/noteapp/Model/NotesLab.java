package com.gaozhidong.android.noteapp.Model;

import android.content.Context;
import android.os.Environment;

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
                    .setAccount("gaozhidong")
                    .setTime("2017-5-3 13:14")
                    .setCalendar(Calendar.getInstance())
                    .setText("testtest")
                    .create();
            mBodyList.add(noteBody);

        NoteBody noteBody2 = NoteBody.Builder()
                .setNoteId(2)
                .setAccount("gao")
                .setTime("2017-5-3 13:14")
                .setCalendar(Calendar.getInstance())
                .setText("llllllobr")
                .create();
        mBodyList.add(noteBody2);

        NoteBody noteBody3 = NoteBody.Builder()
                .setNoteId(3)
                .setAccount("gaozhi")
                .setTime("2017-5-3 13:14")
                .setCalendar(Calendar.getInstance())
                .setText("eeeedddd")
                .create();
        mBodyList.add(noteBody3);

        NoteBody noteBody4 = NoteBody.Builder()
                .setNoteId(4)
                .setAccount("gzhig")
                .setTime("2017-5-3 13:14")
                .setCalendar(Calendar.getInstance())
                .setText("vdvdcrt")
                .create();
        mBodyList.add(noteBody4);

        NoteBody noteBody5 = NoteBody.Builder()
                .setNoteId(5)
                .setAccount("gsdfdfsng")
                .setTime("2017-5-3 13:14")
                .setCalendar(Calendar.getInstance())
                .setText("343434343443ew")
                .create();
        mBodyList.add(noteBody5);

        NoteBody noteBody6 = NoteBody.Builder()
                .setNoteId(6)
                .setAccount("gsdfsxxxxxxdong")
                .setTime("2017-5-3 13:14")
                .setCalendar(Calendar.getInstance())
                .setText("xcvxcvxcxc")
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
}
