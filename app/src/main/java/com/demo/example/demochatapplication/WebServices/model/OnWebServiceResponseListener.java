package com.demo.example.demochatapplication.WebServices.model;

import com.demo.example.demochatapplication.WebServices.model.IWebServiceResponseVO;

/**
 * Created by poonampatel on 25/02/18.
 */

public interface OnWebServiceResponseListener
{
    public void onWebServiceResponseSuccess(IWebServiceResponseVO response);
    public void onWebServiceResponseFailed(String errorMesg, int errorCode);
}
