package com.demo.example.demochatapplication.chat.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.demo.example.demochatapplication.WebServices.Util.DataList;
import com.demo.example.demochatapplication.chat.ChatDisplayFragment;
import com.demo.example.demochatapplication.chat.model.IResponseVO;
import com.demo.example.demochatapplication.chat.model.categoryTypeVO;
import com.demo.example.demochatapplication.login.model.UserDataVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poonampatel on 25/02/18.
 */

public class ChatUserListAdapter extends FragmentStatePagerAdapter
{
    private ArrayList<categoryTypeVO> _categoryTypeList;
    private ArrayList<UserDataVO> _userDataVOArrayList;

    public ChatUserListAdapter(FragmentManager fm, ArrayList<categoryTypeVO> categoryTypeList, ArrayList<UserDataVO> allUserist)
    {
        super(fm);
        _categoryTypeList = categoryTypeList;
        _userDataVOArrayList = allUserist;
    }

    @Override
    public Fragment getItem(int position)
    {
        ChatDisplayFragment chatDisplayFragment = new ChatDisplayFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ChatDisplayFragment.CONTACT_LIST, _categoryTypeList);
        bundle.putParcelableArrayList(ChatDisplayFragment.GROUP_LIST, _userDataVOArrayList);
        bundle.putInt(ChatDisplayFragment.CURRENT_CATEGORY,position);
        chatDisplayFragment.setArguments(bundle);
        return chatDisplayFragment;
    }

    @Override
    public int getCount()
    {
        return _categoryTypeList.size();
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        return _categoryTypeList.get(position).getName();
    }
}
