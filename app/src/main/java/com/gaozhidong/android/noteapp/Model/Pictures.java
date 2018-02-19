package com.gaozhidong.android.noteapp.Model;

import org.litepal.crud.DataSupport;

/**
 * Created by zhidong on 2018/2/19.
 */

public class Pictures extends DataSupport{
    private int noteid;
    private String name;
    private String uri;
    private String path;

    public int getNoteid() {
        return noteid;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
