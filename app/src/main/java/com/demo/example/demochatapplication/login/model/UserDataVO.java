package com.demo.example.demochatapplication.login.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by poonampatel on 25/02/18.
 */

public class UserDataVO implements Parcelable
{
    private String userid;
    private String username;
    private String link;
    private String avatar;
    private String uid;
    private String friends;
    private String grp;
    private String displayname;

    public UserDataVO(JSONObject obj)
    {
        if(obj!=null)
        {
            try
            {
                userid = obj.getString("userid");
                username = obj.optString("username");
                link = obj.optString("link");
                avatar = obj.optString("avatar");
                uid = obj.getString("uid");
                friends = obj.optString("friends");
                grp = obj.optString("grp");
                displayname = obj.optString("displayname");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public String getUserid()
    {
        return userid;
    }

    public String getUsername()
    {
        return username;
    }

    public String getLink()
    {
        return link;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public String getUid()
    {
        return uid;
    }

    public String getFriends()
    {
        return friends;
    }

    public String getGrp()
    {
        return grp;
    }

    public String getDisplayname()
    {
        return displayname;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    protected UserDataVO(Parcel in)
    {
        userid = in.readString();
        username = in.readString();
        link = in.readString();
        avatar = in.readString();
        uid = in.readString();
        friends = in.readString();
        grp = in.readString();
        displayname = in.readString();
    }

    public static final Creator<UserDataVO> CREATOR = new Creator<UserDataVO>()
    {
        @Override
        public UserDataVO createFromParcel(Parcel in)
        {
            return new UserDataVO(in);
        }

        @Override
        public UserDataVO[] newArray(int size)
        {
            return new UserDataVO[size];
        }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(userid);
        dest.writeString(username);
        dest.writeString(link);
        dest.writeString(avatar);
        dest.writeString(uid);
        dest.writeString(friends);
        dest.writeString(grp);
        dest.writeString(displayname);
    }
}
