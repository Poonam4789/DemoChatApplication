package com.demo.example.demochatapplication.profile.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by poonampatel on 26/02/18.
 */

public class ProfileVO
{
    String id;
    String n;
    String l;
    String a;
    String s;
    String m;
    String push_channel;
    String push_an_channel;

    public ProfileVO(JSONObject obj)
    {
        try
        {
            this.id = obj.getString("id");
            this.n =obj.getString("n") ;
            this.l = obj.getString("l");
            this.a = obj.getString("a");
            this.s = obj.getString("s");
            this.m = obj.getString("m");
            this.push_channel = obj.getString("ch");
            this.push_an_channel = obj.getString("rdrs");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public String getId()
    {
        return id;
    }

    public String getN()
    {
        return n;
    }

    public String getL()
    {
        return l;
    }

    public String getA()
    {
        return a;
    }

    public String getS()
    {
        return s;
    }

    public String getM()
    {
        return m;
    }

    public String getPush_channel()
    {
        return push_channel;
    }

    public String getPush_an_channel()
    {
        return push_an_channel;
    }
}
