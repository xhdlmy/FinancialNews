package com.aide.financial.net.retrofit.resp;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GankResp implements Parcelable {

    public boolean error;
    public List<GankData> results;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.error ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.results);
    }

    public GankResp() {
    }

    protected GankResp(Parcel in) {
        this.error = in.readByte() != 0;
        this.results = in.createTypedArrayList(GankData.CREATOR);
    }

    public static final Creator<GankResp> CREATOR = new Creator<GankResp>() {
        @Override
        public GankResp createFromParcel(Parcel source) {
            return new GankResp(source);
        }

        @Override
        public GankResp[] newArray(int size) {
            return new GankResp[size];
        }
    };
}