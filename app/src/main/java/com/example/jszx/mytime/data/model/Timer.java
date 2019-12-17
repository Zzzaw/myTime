package com.example.jszx.mytime.data.model;

import java.io.Serializable;

/**
 * Created by jszx on 2019/12/16.
 */

public class Timer implements Serializable {
    private String Title;
    private int CoverResourceId;

    public Timer(String name,int pictureId) {
        this.Title = name;
        this.CoverResourceId = pictureId;
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

    public void setCoverResourceId(int pictureId) {
        this.CoverResourceId = pictureId;
    }
}
