package com.gaozhidong.android.noteapp.Model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by zhidong on 2017/9/19.
 */

public class NoteBody extends DataSupport implements Serializable{
    private int mNoteId;
    private String mText;
    private String mTime;
    private String mAccount;
    private Calendar mCalendar;
    private int flag = 0;

    public NoteBody() {
    }

    public static NoteBody Builder() {
        return new NoteBody();
    }

    public NoteBody setNoteId(int noteId) {
        mNoteId = noteId;
        return this;
    }

    public NoteBody setText(String text) {
        mText = text;
        return this;
    }

    public NoteBody setTime(String time) {
        mTime = time;
        return this;
    }

    public NoteBody setCalendar(Calendar calendar) {
        mCalendar = calendar;
        return this;
    }

    public NoteBody setAccount(String account) {
        mAccount = account;
        return this;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public NoteBody create() {
        return this;
    }


    public int getFlag() {
        return flag;
    }

    public int getNoteId() {
        return mNoteId;
    }

    public String getTime() {
        return mTime;
    }

    public String getText() {
        return mText;
    }

    public Calendar getCalendar() {
        return mCalendar;
    }

    public String getAccount() {
        return mAccount;
    }

    public String getPhotoFilename(){
        return "IMG_" + getNoteId() + ".jpg";
    }
}
