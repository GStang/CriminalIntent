package com.swpuiot.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by DELL on 2016/12/8.
 * 这个是模型层的东西，
 * 一个Crime代表着一个坏习惯，
 * 其中包含ID，Title等东西
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mSuspect;

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

    public String getmSuspect() {
        return mSuspect;
    }

    public void setmSuspect(String mSuspect) {
        this.mSuspect = mSuspect;
    }

    public Crime() {
        this(UUID.randomUUID());
    }

    public Crime(UUID uuid) {
        mId = uuid;
        mDate = new Date();
    }

}
