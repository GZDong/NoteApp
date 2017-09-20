package com.gaozhidong.android.noteapp.ServiceResultEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhidong on 2017/9/20.
 */

public class LoginResult {

    private List<Body> Bodies;

    public class Body{
        String noteid;
        String notedata;
        String notetime;
        String noteaccount;

        public String getNoteaccount() {
            return noteaccount;
        }

        public String getNotedata() {
            return notedata;
        }

        public String getNoteid() {
            return noteid;
        }

        public String getNotetime() {
            return notetime;
        }
    }

    public List<Body> getBodies() {
        return Bodies;
    }
}
