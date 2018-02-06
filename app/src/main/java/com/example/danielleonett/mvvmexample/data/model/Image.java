package com.example.danielleonett.mvvmexample.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by daniel.leonett on 31/01/2018.
 */

public class Image implements Parcelable {

    private String url;
    private int width;
    private int height;

    protected Image(Parcel in) {
        url = in.readString();
        width = in.readInt();
        height = in.readInt();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeInt(width);
        parcel.writeInt(height);
    }
}
