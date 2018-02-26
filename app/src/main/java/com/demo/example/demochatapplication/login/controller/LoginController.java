package com.demo.example.demochatapplication.login.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.demo.example.demochatapplication.DemoChatApplication;
import com.demo.example.demochatapplication.Utils.SharedPrefranceManager;
import com.demo.example.demochatapplication.WebServices.Util.ApplicationURL;
import com.demo.example.demochatapplication.WebServices.Util.NameValueServicesParam;
import com.demo.example.demochatapplication.WebServices.model.IWebServiceResponseVO;
import com.demo.example.demochatapplication.WebServices.model.OnWebServiceResponseListener;
import com.demo.example.demochatapplication.appBase.ChatMainActivity;
import com.demo.example.demochatapplication.appBase.SplashActivity;
import com.demo.example.demochatapplication.chat.model.IResponseVO;
import com.demo.example.demochatapplication.chat.model.categoryTypeVO;
import com.demo.example.demochatapplication.login.model.CreateUserProcessorVO;
import com.demo.example.demochatapplication.login.model.UserDataVO;
import com.inscripts.interfaces.Callbacks;
import com.inscripts.interfaces.SubscribeCallbacks;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import static com.demo.example.demochatapplication.appBase.SplashActivity.cometChat;

/**
 * Created by poonampatel on 23/02/18.
 */

public class LoginController implements OnWebServiceResponseListener
{
    private static final String TAG = "LoginController";
    private static LoginController _instance;
    private IResponseVO _iResponseVOWeakReference;
    private Context _context;
    public static boolean isLoggedIn = false;
    Random _random = new Random();

    public static LoginController getInstance()
    {
        return _instance;
    }

    public static void initializeLoginController(Context context)
    {
        if (_instance == null)
        {
            _instance = new LoginController(context);
        }
    }

    private LoginController(Context context)
    {
        _context = context;
    }

    public void login(String username, String password)
    {
        Log.d("Called", "Login Called");
        ArrayList<NameValuePair> valuePairList = new ArrayList<>();
        valuePairList.add(new NameValueServicesParam("username", username));
        valuePairList.add(new NameValueServicesParam("password", password));
        valuePairList.add(new NameValueServicesParam("link", ""));
        valuePairList.add(new NameValueServicesParam("avatar", ""));
        valuePairList.add(new NameValueServicesParam("displayname", username));
        valuePairList.add(new NameValueServicesParam("roleid", String.valueOf(_random.nextInt())));
        DemoChatApplication.getApplicationInstance().getWebServicesManager().servicePost(this, new CreateUserProcessorVO(), ApplicationURL.CREATE_USER_URL, valuePairList);
    }

    private void callLoginWebService(String userid)
    {
        cometChat.login(userid, new Callbacks()
        {
            @Override
            public void successCallback(JSONObject response)
            {
                Toast.makeText(_context, "Login Success", Toast.LENGTH_LONG).show();
                isLoggedIn = true;
                Intent i = new Intent(_context, ChatMainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                _context.startActivity(i);
            }

            @Override
            public void failCallback(JSONObject response)
            {
                isLoggedIn = false;
                Toast.makeText(_context, "Login Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAllUsersList(IResponseVO listener)
    {
        _iResponseVOWeakReference = listener;
        Log.d("Called", "getAllUsersList Called");
        ArrayList<NameValuePair> valuePairList = new ArrayList<>();
        valuePairList.add(new NameValueServicesParam("page_number", "1"));
        valuePairList.add(new NameValueServicesParam("limit", "20"));
        DemoChatApplication.getApplicationInstance().getWebServicesManager().servicePost(this, new CreateUserProcessorVO(), ApplicationURL.GET_ALL_USER_URL, valuePairList);
    }

    @Override
    public void onWebServiceResponseSuccess(IWebServiceResponseVO response)
    {

        CreateUserProcessorVO mProcessor = (CreateUserProcessorVO) response;
        ArrayList<UserDataVO> userResponse = mProcessor.getResultDataVOS().get(0).getData();
        if (userResponse != null)
        {
            if (userResponse.size() > 1)
            {
                _iResponseVOWeakReference.SetTabPagerAdapters(getAllCategories(), userResponse);
            }
            else
            {
                callLoginWebService(userResponse.get(0).getUserid());
                SharedPrefranceManager.setUserId(_context, userResponse.get(0).getUserid());
            }
        }
    }

    @Override
    public void onWebServiceResponseFailed(String errorMesg, int errorCode)
    {
        Toast.makeText(_context, "Unable to login Please try again later", Toast.LENGTH_LONG).show();
    }

    public ArrayList<categoryTypeVO> getAllCategories()
    {
        ArrayList<categoryTypeVO> categoryTypeVOS = new ArrayList<>();
        categoryTypeVOS.add(new categoryTypeVO("Contacts"));
        categoryTypeVOS.add(new categoryTypeVO("Groups"));
        return categoryTypeVOS;
    }
}
