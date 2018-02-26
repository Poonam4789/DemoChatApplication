package com.demo.example.demochatapplication.login.model;

import android.util.Log;

import com.demo.example.demochatapplication.WebServices.Util.DataList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by poonampatel on 22/02/18.
 */

public class ResultDataVO
{
    private String _status;

    private String _message;

    private DataList<UserDataVO> _dataVODataList;

    public String getStatus()
    {
        return _status;
    }

    public String getMessage()
    {
        return _message;
    }

    public DataList<UserDataVO> getData()
    {
        return _dataVODataList;
    }

    public static boolean IsResultSuccess = false;
    JSONObject result;

    public ResultDataVO()
    {

    }

    public ResultDataVO(JSONObject obj)
    {
        Log.d("RESPONSE", "Result" + obj.toString());

        JSONObject _userData;
        UserDataVO _dataVO;
        _dataVODataList = new DataList<>();
        JSONArray _userDataList = new JSONArray();
        try
        {
            IsResultSuccess = obj.has("success");
            if (IsResultSuccess)
            {
                result = obj.getJSONObject("success");
            }
            else
            {
                result = obj.getJSONObject("failed");
            }
            _status = result.getString("status");
            _message = result.getString("message");
            if(result.optJSONObject("data")!=null)
            {
                _userData = result.getJSONObject("data");
                if (_userData != null)
                {
                    _dataVO = new UserDataVO(_userData);
                    _dataVODataList.add(_dataVO);
                }
            }else{
                _userDataList = result.getJSONArray("data");
               int length = _userDataList.length();
               if(length!=0)
               {
                   for(int i =0;i<length; i++)
                   {
                       _dataVO = new UserDataVO(_userDataList.getJSONObject(i));
                       _dataVODataList.add(_dataVO);
                   }
               }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
