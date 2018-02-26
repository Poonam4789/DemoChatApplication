package com.demo.example.demochatapplication.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.demo.example.demochatapplication.R;
import com.demo.example.demochatapplication.appBase.BaseActivity;
import com.demo.example.demochatapplication.chat.adapters.AllAndGroupUserAdapter;
import com.demo.example.demochatapplication.chat.model.categoryTypeVO;
import com.demo.example.demochatapplication.login.model.UserDataVO;

import java.util.ArrayList;


/**
 * Created by poonampatel on 25/02/18.
 */

public class ChatDisplayFragment extends Fragment
{
    private final String TAG = "ChatDisplayFragment";
    public static final String CONTACT_LIST = "all_contacts_list";
    public static final String GROUP_LIST = "all_groups_list";
    public static final String CURRENT_CATEGORY = "current_category";

    RecyclerView _productCoversListView;

    private ArrayList<categoryTypeVO> _categoryTypeVOS;
    private ArrayList<UserDataVO> _userDataVOArrayList;
    private AllAndGroupUserAdapter _allAndGroupUserAdapter;
    private ProgressBar _progressBar;
    int _currentCategoryItem = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.all_user_layout, container, false);
        ((BaseActivity) getActivity()).toggleDrawerIcon(true);
        _progressBar = (ProgressBar) view.findViewById(R.id.page_progress);

        _productCoversListView = (RecyclerView) view.findViewById(R.id.product_view);

        _productCoversListView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        _productCoversListView.setItemAnimator(null);

        _categoryTypeVOS = getArguments().getParcelableArrayList(CONTACT_LIST);
        _userDataVOArrayList = getArguments().getParcelableArrayList(GROUP_LIST);
        _currentCategoryItem = getArguments().getInt(CURRENT_CATEGORY);

        Log.d(TAG, "no of Categories" + _categoryTypeVOS.size());
        Log.d(TAG, "no of All Usrs" + _userDataVOArrayList.size());
        _progressBar.setVisibility(View.VISIBLE);
        setCategoryWiseAdapter();
        return view;
    }

    private void setCategoryWiseAdapter()
    {
        _productCoversListView.setVisibility(View.VISIBLE);
        _allAndGroupUserAdapter = new AllAndGroupUserAdapter(getActivity().getSupportFragmentManager(), _userDataVOArrayList, _currentCategoryItem);
        _productCoversListView.setAdapter(_allAndGroupUserAdapter);
        _progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        _progressBar = null;
        _productCoversListView = null;
        _allAndGroupUserAdapter = null;
    }

}
