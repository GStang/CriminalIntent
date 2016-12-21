package com.swpuiot.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by DELL on 2016/12/8.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public UUID getmId() {

        return mId;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean ismSolved() {
        return mSolved;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public Crime(){
        this(UUID.randomUUID());
    }
    public Crime(UUID uuid){
        mId = uuid;
        mDate = new Date();
    }

}
