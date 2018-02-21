package com.gaozhidong.android.noteapp.ServiceResultEntity;

import java.util.List;

/**
 * Created by zhidong on 2017/9/20.
 */

public class LoginResult {

    /**
     * noteid : 1
     * notedata : i am gzd
     * notetime : 2017-9-16
     * noteaccount : gaozhidong
     */

    private String noteid;
    private String notedata;
    private String notetime;
    private String noteaccount;

    public String getNoteid() {
        return noteid;
    }

    public void setNoteid(String noteid) {
        this.noteid = noteid;
    }

    public String getNotedata() {
        return notedata;
    }

    public void setNotedata(String notedata) {
        this.notedata = notedata;
    }

    public String getNotetime() {
        return notetime;
    }

    public void setNotetime(String notetime) {
        this.notetime = notetime;
    }

    public String getNoteaccount() {
        return noteaccount;
    }

    public void setNoteaccount(String noteaccount) {
        this.noteaccount = noteaccount;
    }
}
