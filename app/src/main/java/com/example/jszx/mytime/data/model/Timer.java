package com.example.jszx.mytime.data.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jszx on 2019/12/16.
 */

public class Timer implements Serializable {
    private String Title;
    private int CoverResourceId;
    private Date date;

    public Timer(String name,int pictureId) {
        this.Title = name;
        this.CoverResourceId = pictureId;
        long time = System.currentTimeMillis();
        this.date = new Date(time);
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String name) {
        this.Title = name;
    }


    public int getCoverResourceId() {
        return CoverResourceId;
    }

    public Date getDate() { return  date; }
    public void setDate(Date date) { this.date = date; }

    public void setCoverResourceId(int pictureId) {
        this.CoverResourceId = pictureId;
    }
}
