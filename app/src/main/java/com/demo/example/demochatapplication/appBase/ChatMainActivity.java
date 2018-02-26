package com.demo.example.demochatapplication.appBase;

import android.os.Bundle;
import android.util.Log;
import com.demo.example.demochatapplication.R;
import com.demo.example.demochatapplication.chat.ChatMainFragment;

public class ChatMainActivity extends BaseActivity
{
    private static final String TAG = "ChatMainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        toggleDrawerIcon(true);
        ChatMainFragment pagerFragment = new ChatMainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentlayout, pagerFragment, "ChatMainFragment").commit();
    }

    @Override
    protected void onNetworkChange(boolean isNetworkConnected)
    {
        showNoNetworkBar(isNetworkConnected);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        toggleDrawerIcon(true);
    }

    @Override
    public void onBackPressed()
    {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        Log.d("BackPress", "Fragmentcount = " + count);
        if (count == 0)
        {
            super.onBackPressed();
        }
        else if (count == 1)
        {
            super.onBackPressed();
            toggleDrawerIcon(true);
            BaseActivity.chatWindowOpen = false;
            settoolbarTitle(getApplicationContext().getResources().getString(R.string.app_name));
        }
        else if (count > 1)
        {
            String name = getSupportFragmentManager().getBackStackEntryAt(0).getName();
            Log.d("BackPress", "Fragment = " + name);
            if ("ChatMainFragment".equalsIgnoreCase(name))
            {
                super.onBackPressed();
                toggleDrawerIcon(true);
                BaseActivity.chatWindowOpen = false;
                settoolbarTitle(getApplicationContext().getResources().getString(R.string.app_name));
            }
            else if ("SettingsFragment".equalsIgnoreCase(name))
            {
                super.onBackPressed();
                BaseActivity.chatWindowOpen = false;
                toggleDrawerIcon(false);
                settoolbarTitle(getApplicationContext().getResources().getString(R.string.settings));

            }
            else if ("ProfileFragment".equalsIgnoreCase(name))
            {
                super.onBackPressed();
                BaseActivity.chatWindowOpen = false;
                toggleDrawerIcon(false);
                settoolbarTitle(getApplicationContext().getResources().getString(R.string.profile));
            }
            else if ("ChatOrGroupFragment".equalsIgnoreCase(name))
            {
                BaseActivity.chatWindowOpen = true;
                toggleDrawerIcon(false);
                settoolbarTitle(getApplicationContext().getResources().getString(R.string.guest));
            }
        }
        invalidateMenu();
    }
}
