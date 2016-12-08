package com.swpuiot.criminalintent;

import java.util.UUID;

/**
 * Created by DELL on 2016/12/8.
 */
public class Crime {
    private UUID mId;
    private String mTitle;

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public UUID getmId() {

        return mId;
    }

    public Crime(){
        mId = UUID.randomUUID();

    }
}
