package com.gaozhidong.android.noteapp.Model;

import android.content.Context;

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
        for (int i = 0; i <= 10; i++) {
            NoteBody noteBody = NoteBody.Builder()
                    .setNoteId(1)
                    .setAccount("gaozhidong")
                    .setTime("2017-5-3 13:14")
                    .setCalendar(Calendar.getInstance())
                    .setText("testtest")
                    .create();
            mBodyList.add(noteBody);
        }
    }
    public List<NoteBody> getBodyList(){
        return mBodyList;
    }
}
