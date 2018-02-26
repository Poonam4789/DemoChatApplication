package com.demo.example.demochatapplication.appBase;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.demo.example.demochatapplication.R;
import com.demo.example.demochatapplication.Settings.SettingsFragment;
import com.demo.example.demochatapplication.network.NetworkChangeReceiver;
import com.demo.example.demochatapplication.network.NetworkStatus;

/**
 * Created by poonampatel on 22/02/18.
 */

public abstract class BaseActivity extends AppCompatActivity
{
    private NetworkChangeReceiver _networkReceiver;
    protected ProgressBar progressDailog;
    public static boolean chatWindowOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.d("Called", "Base Called");
        _networkReceiver = new NetworkChangeReceiver(networkChangeHandler);
    }

    protected void initToolbar(Toolbar toolbar)
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void settoolbarTitle(String title)
    {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        registerNetworkChangeReceiver();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterNetworkChangeReciver();
    }

    public void showHideProgress(NetworkStatus networkStatus)
    {
        switch (networkStatus)
        {
            case STATUS_LOADING:

                showProgressBar(true);
                break;

            case STATUS_SUCCESS:

                showProgressBar(false);
                break;

        }
    }

    protected void showProgressBar(boolean toShow)
    {
        if (progressDailog != null)
        {
            if (toShow)
            {
                progressDailog.setVisibility(View.VISIBLE);
            }
            else
            {
                progressDailog.setVisibility(View.GONE);
            }
        }
    }

    /*******************
     * Network change receiver implementation
     **************/

    /*************
     * Register Network Change Receiver
     *********/
    private void registerNetworkChangeReceiver()
    {
        registerReceiver(_networkReceiver, NetworkChangeReceiver.getNetworkChangeFilter());
    }

    /*********
     * Unregister Network Change Receiver
     ************/
    private void unregisterNetworkChangeReciver()
    {
        unregisterReceiver(_networkReceiver);
    }

    /**
     * @networkChangeHandler will get message
     * msg.what = NETWORK_NOT_CONNECTED; if network connected
     * msg.what = NETWORK_CONNECTED;     if network not connected
     */
    Handler networkChangeHandler = new Handler()
    {
        @Override
        public void handleMessage(Message networkMessage)
        {
            onNetworkChange(NetworkChangeReceiver.isOnline());
        }
    };

    protected abstract void onNetworkChange(boolean isNetworkConnected);

    protected void showNoNetworkBar(boolean isNetworkConnected)
    {
        if (!isNetworkConnected)
        {
            Snackbar.make(findViewById(android.R.id.content), "Please check network connection", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    public void toggleDrawerIcon(boolean showDrawer)
    {
        if (showDrawer)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        else
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            case R.id.search:
                Toast.makeText(getApplicationContext(), "Search called", Toast.LENGTH_LONG).show();
                return true;
            case R.id.chat:
                Toast.makeText(getApplicationContext(), "Chat called", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_bar_menu_view_profile:
                Toast.makeText(getApplicationContext(), "View Profile called", Toast.LENGTH_LONG).show();
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SettingsFragment settingsFragment = new SettingsFragment();
                ft.add(R.id.contentlayout, settingsFragment, "SettingsFragment").addToBackStack("SettingsFragment");
                ft.commit();
                return true;
            case R.id.video:
                Toast.makeText(getApplicationContext(), "video called", Toast.LENGTH_LONG).show();
                return true;
            case R.id.call:
                Toast.makeText(getApplicationContext(), "phone called", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        MenuItem _video = menu.findItem(R.id.video);
        MenuItem _call = menu.findItem(R.id.call);
        MenuItem _search = menu.findItem(R.id.search);
        MenuItem _chat = menu.findItem(R.id.chat);
        if (chatWindowOpen)
        {
            _video.setVisible(true);
            _call.setVisible(true);
            _search.setVisible(false);
            _chat.setVisible(false);
        }
        else
        {
            _video.setVisible(false);
            _call.setVisible(false);
            _search.setVisible(true);
            _chat.setVisible(true);
        }
        this.invalidateOptionsMenu();
        return true;
    }
    public void invalidateMenu(){
        invalidateOptionsMenu();
    }
}
