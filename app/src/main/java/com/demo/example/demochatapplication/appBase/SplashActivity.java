package com.demo.example.demochatapplication.appBase;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.demo.example.demochatapplication.R;
import com.demo.example.demochatapplication.Utils.PermissionsHelper;
import com.demo.example.demochatapplication.Utils.SharedPrefranceManager;
import com.demo.example.demochatapplication.login.LoginFragment;
import com.demo.example.demochatapplication.login.controller.LoginController;
import com.demo.example.demochatapplication.login.model.ResultDataVO;
import com.inscripts.interfaces.Callbacks;

import org.json.JSONObject;

import cometchat.inscripts.com.cometchatcore.coresdk.CometChat;

/**
 * Created by poonampatel on 23/02/18.
 */

public class SplashActivity extends AppCompatActivity
{
    private static final String TAG = "SplashActivity";
    private AnimationDrawable loadingViewAnim = null;
    private LinearLayout loadingLayout;
    private ResultDataVO loginResponse;
    private String SITEURL = "";
    private String APIKEY = "10108xbe8e430df890202085ea36a364745175";
    private String LICENSEKEY = "COMETCHAT-V528X-727B9-XUDUT-WE44H";
    public static CometChat cometChat;
    public static boolean IsComerChatInitialized = false;
    private ProgressBar _progressBar;
    public boolean _appPerMission_WRITE_EXTERNAL = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {//requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);
        Log.d("Called", TAG);
        loadingLayout = (LinearLayout) findViewById(R.id.LinearLayout1);
        _progressBar = findViewById(R.id.progressDailog);
        init();
    }

    private void init()
    {
        PermissionsHelper helper = new PermissionsHelper(this);
        if (helper.isPermissionGranted(Manifest.permission.READ_PHONE_STATE))
        {
            launchLoading(getApplicationContext(), getIntent());
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionsHelper.APP_PERMISSIONS_CODE);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState)
    {

    }

    public void launchLoading(Context context, Intent intent)
    {
        cometChat = CometChat.getInstance(getApplicationContext());
        cometChat.initializeCometChat(SITEURL, LICENSEKEY, APIKEY, true, new Callbacks()
        {
            @Override
            public void successCallback(JSONObject jsonObject)
            {
                Log.d("Called", "CharMAin SuccessCalled");
                IsComerChatInitialized = true;
                Log.d("Called", "Main Called");
                if (SharedPrefranceManager.getUserId(getApplicationContext()) == "0")
                {
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentlayout);
                    if (fragment != null && !(fragment instanceof LoginFragment))
                    {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    }
                    if (getSupportFragmentManager().findFragmentByTag("LOGIN") == null)
                    {
                        _progressBar.setVisibility(View.GONE);
                        loadingLayout.setVisibility(View.GONE);
                        LoginFragment loginFragment = new LoginFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.contentlayout, loginFragment, "EXPLORE").commitAllowingStateLoss();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
                    LoginController.isLoggedIn = true;
                    Intent i = new Intent(getApplicationContext(), ChatMainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getApplicationContext().startActivity(i);
                }
                Log.d("Response", "Success");
            }

            @Override
            public void failCallback(JSONObject jsonObject)
            {
                IsComerChatInitialized = false;
                Log.d("Response", "Failed");
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionsHelper.APP_PERMISSIONS_CODE)
        {
            PermissionsHelper _helper = new PermissionsHelper(this);
            if (!_helper.checkPermissionRequestResults(grantResults))
            {
                if (!_helper.isPermissionGranted(Manifest.permission.READ_PHONE_STATE))
                {
                    _helper.showMissingPermissionDialog(getString(R.string.phone_permission), this, false);
                    return;
                }
                if (permissions[0].contains(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                {
                    _helper.showMissingPermissionDialog(getString(R.string.storage_permission), this, false);
                    return;
                }
                launchLoading(getApplicationContext(), getIntent());
            }
            else
            {
                if (_helper.isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                {
                    _appPerMission_WRITE_EXTERNAL = true;
                    return;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2)
        {
            SplashActivity.this.finish();
        }

        if (resultCode == 200)
        {
            SplashActivity.this.finish();
        }
    }
}
