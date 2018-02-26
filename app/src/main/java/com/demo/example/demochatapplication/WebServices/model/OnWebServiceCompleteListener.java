package com.demo.example.demochatapplication.WebServices.model;

import com.demo.example.demochatapplication.WebServices.connector.WebServiceConnector;

/**
 * Created by poonampatel on 25/02/18.
 */

public interface OnWebServiceCompleteListener
{
    void serviceComplete(WebServiceConnector connector, WebServiceConnector.ServiceDataInfo serviceInfo);
}
