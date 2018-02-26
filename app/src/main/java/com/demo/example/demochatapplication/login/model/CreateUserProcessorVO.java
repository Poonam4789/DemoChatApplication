package com.demo.example.demochatapplication.login.model;

import android.util.Log;

import com.demo.example.demochatapplication.WebServices.model.IWebServiceResponseVO;
import com.demo.example.demochatapplication.WebServices.Util.DataList;

import org.json.JSONObject;

/**
 * Created by poonampatel on 25/02/18.
 */

public class CreateUserProcessorVO implements IWebServiceResponseVO
{
    public DataList<ResultDataVO> getResultDataVOS()
    {
        return _resultDataVOS;
    }

    private DataList<ResultDataVO> _resultDataVOS;

    @Override
    public void processResponse(Object data)
    {
        _resultDataVOS = new DataList<>();
        if (data instanceof JSONObject)
        {
            JSONObject obj = (JSONObject) data;
            Log.d("RESPONSE","CreateUser"+obj.toString());
            try
            {
                    ResultDataVO resultDataVO = new ResultDataVO(obj);
                    _resultDataVOS.add(resultDataVO);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}
