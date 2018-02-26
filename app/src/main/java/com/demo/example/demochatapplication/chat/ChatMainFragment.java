package com.demo.example.demochatapplication.chat;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.demo.example.demochatapplication.R;
import com.demo.example.demochatapplication.appBase.BaseActivity;
import com.demo.example.demochatapplication.chat.adapters.ChatUserListAdapter;
import com.demo.example.demochatapplication.chat.model.IResponseVO;
import com.demo.example.demochatapplication.chat.model.categoryTypeVO;
import com.demo.example.demochatapplication.login.controller.LoginController;
import com.demo.example.demochatapplication.login.model.UserDataVO;

import java.util.ArrayList;

/**
 * Created by poonampatel on 25/02/18.
 */

public class ChatMainFragment extends Fragment implements IResponseVO
{
    private static final String TAG = "RESPONSE";
    TabLayout _categoryTabs;
    ViewPager _userPager;
    ChatUserListAdapter _chatUserListAdapter;
    int tabSelectedPosition = 0;
    public static ProgressBar progressDailog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity) getActivity()).toggleDrawerIcon(true);
        ((BaseActivity) getActivity()).settoolbarTitle(getActivity().getResources().getString(R.string.app_name));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.chat_fragment_layout, container, false);
        progressDailog = view.findViewById(R.id.progressDailog);
        _categoryTabs = view.findViewById(R.id.categories_tabs);
        _categoryTabs.setTabTextColors(getContext().getResources().getColor(R.color.white), getContext().getResources().getColor(R.color.white));
        _userPager = view.findViewById(R.id.product_pager);
        _categoryTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        _categoryTabs.setVisibility(View.VISIBLE);
        _userPager.setVisibility(View.GONE);
        progressDailog.setVisibility(View.VISIBLE);
        LoginController.getInstance().getAllUsersList(this);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void SetTabPagerAdapters(ArrayList<categoryTypeVO> categoryTypeVOList, ArrayList<UserDataVO> userDataVOList)
    {
        _chatUserListAdapter = new ChatUserListAdapter(getChildFragmentManager(), categoryTypeVOList, userDataVOList);
        _userPager.setVisibility(View.VISIBLE);
        _categoryTabs.setupWithViewPager(_userPager);
        _userPager.setAdapter(_chatUserListAdapter);
        _userPager.setCurrentItem(0);
        progressDailog.setVisibility(View.GONE);
        _userPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                super.onPageSelected(position);
                Log.d(TAG, "PageSelected" + _userPager.getCurrentItem());
                tabSelectedPosition = _userPager.getCurrentItem();
            }
        });
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        _userPager = null;
        _chatUserListAdapter = null;
    }
}
