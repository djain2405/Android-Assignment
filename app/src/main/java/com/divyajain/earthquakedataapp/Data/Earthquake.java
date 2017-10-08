package com.divyajain.earthquakedataapp.Data;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Earthquake implements Parcelable {

    class MyDate
    {
        String date;
        String time;

        MyDate(String date, String time)
        {
            this.date = date;
            this.time = time;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(magnitude);
        dest.writeString(date);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(depth);
    }


    public static final Creator CREATOR
            = new Creator() {
        public Earthquake createFromParcel(Parcel in) {
            return new Earthquake(in);
        }

        public Earthquake[] newArray(int size) {
            return new Earthquake[size];
        }
    };

    public Earthquake(Parcel in) {
        magnitude = in.readString();
        date = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        depth = in.readString();
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    private String magnitude;
    private String date;
    private String time;
    private String latitude;
    private String longitude;
    private String depth;

    public String getSubArea() {
        return subArea;
    }

    public void setSubArea(String subArea) {


        this.subArea = subArea;
    }

    private String subArea;

    public Earthquake(){
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getDate() {

        MyDate mydate = formatDate(date);
        return mydate.date;
    }

    public String getTime() {

        MyDate mydate = formatDate(date);
        return mydate.time;

    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDepth() {
        return depth;
    }

    MyDate formatDate(String datetime) {
        String fmt = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(fmt);

        Date dt = null;
        try {
            dt = df.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat tdf = new SimpleDateFormat("HH:mm:ss");
        DateFormat dfmt  = new SimpleDateFormat("yyyy-MM-dd");


        String timeOnly = tdf.format(dt);
        String dateOnly = dfmt.format(dt);

        return new MyDate(dateOnly, timeOnly);
    }


}
