package com.demo.example.demochatapplication.chat.model;

import com.demo.example.demochatapplication.login.model.UserDataVO;

import java.util.ArrayList;

/**
 * Created by poonampatel on 25/02/18.
 */

public interface IResponseVO
{
    public void SetTabPagerAdapters(ArrayList<categoryTypeVO> categoryTypeVOList , ArrayList<UserDataVO> userDataVOList);
}
