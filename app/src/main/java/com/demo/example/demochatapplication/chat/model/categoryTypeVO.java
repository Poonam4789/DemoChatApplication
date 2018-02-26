package com.demo.example.demochatapplication.chat.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by poonampatel on 25/02/18.
 */

public class categoryTypeVO implements Parcelable
{
   private String _name;

    public categoryTypeVO(){

    }
    protected categoryTypeVO(Parcel in)
    {
        _name = in.readString();
    }

    public categoryTypeVO(String name){
        _name = name;
    }

    public static final Creator<categoryTypeVO> CREATOR = new Creator<categoryTypeVO>()
    {
        @Override
        public categoryTypeVO createFromParcel(Parcel in)
        {
            return new categoryTypeVO(in);
        }

        @Override
        public categoryTypeVO[] newArray(int size)
        {
            return new categoryTypeVO[size];
        }
    };

    public String getName()
    {
        return _name;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(_name);
    }
}
