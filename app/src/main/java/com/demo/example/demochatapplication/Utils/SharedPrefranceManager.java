package com.demo.example.demochatapplication.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by poonampatel on 26/02/18.
 */

public class SharedPrefranceManager
{

    private static final String USER_ID_PREF = "user_id_pref";
    private static final String USER_ID = "user_id";

    public static String getUserId(Context context)
    {
        SharedPreferences dataPrefs = context.getSharedPreferences(USER_ID, Context.MODE_PRIVATE);
        String userId = dataPrefs.getString(USER_ID, "0");
        return userId;
    }

    public static void setUserId(Context context, String userId)
    {
        SharedPreferences.Editor dataPref = context.getSharedPreferences(USER_ID, Context.MODE_PRIVATE).edit();
        dataPref.putString(USER_ID, userId);
        dataPref.commit();
    }
}
