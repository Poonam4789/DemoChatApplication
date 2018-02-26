package com.demo.example.demochatapplication;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.demo.example.demochatapplication.WebServices.connector.WebServicesManager;
import com.demo.example.demochatapplication.login.controller.LoginController;
import com.demo.example.demochatapplication.network.NetworkChangeReceiver;

/**
 * Created by poonampatel on 21/02/18.
 */

public class DemoChatApplication extends Application
{
    public static final String DEVELOPMENT_BUILD = "development", PRODUCTION_BUILD = "production";
    private static String APIKEY = "10108xbe8e430df890202085ea36a364745175";
    private static DemoChatApplication _demoChatApplication;
    private WebServicesManager _webServicesManager;

    public static DemoChatApplication getApplicationInstance()
    {
        return _demoChatApplication;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d("Called",  "Application Called");
        _demoChatApplication = this;
        _webServicesManager = new WebServicesManager(getApplicationContext());
        NetworkChangeReceiver.initNetworkChange(getApplicationContext());
        LoginController.initializeLoginController(getApplicationContext());
    }

    public Context getApplicationContext()
    {
        return this;
    }

    public static String getAPIKEY()
    {
        return APIKEY;
    }
    public WebServicesManager getWebServicesManager()
    {
        return _webServicesManager;
    }
}
