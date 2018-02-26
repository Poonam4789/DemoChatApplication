package com.demo.example.demochatapplication.chat.adapters;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.example.demochatapplication.R;
import com.demo.example.demochatapplication.chatorgroup.ChatOrGroupFragment;
import com.demo.example.demochatapplication.login.model.UserDataVO;
import com.demo.example.demochatapplication.profile.ProfileFragment;

import java.util.ArrayList;

/**
 * Created by poonampatel on 25/02/18.
 */

public class AllAndGroupUserAdapter extends RecyclerView.Adapter<AllAndGroupUserAdapter.ViewHolder>
{
    private ArrayList<UserDataVO> _userDataVOArrayList;

    FragmentManager _fragmentManager;
    int _selectedCategoryTabs;

    public AllAndGroupUserAdapter(FragmentManager supportFragmentManager, ArrayList<UserDataVO> userDataVOArrayList, int tabselected)
    {
        _fragmentManager = supportFragmentManager;
        _userDataVOArrayList = userDataVOArrayList;
        _selectedCategoryTabs = tabselected;
    }

    @Override
    public AllAndGroupUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllAndGroupUserAdapter.ViewHolder holder, int position)
    {
        if (_selectedCategoryTabs == 0)
        {
            holder._name.setText(_userDataVOArrayList.get(position).getUsername());
            holder._isactive.setText(_userDataVOArrayList.get(position).getUserid());
            holder._userstatus.setText(_userDataVOArrayList.get(position).getDisplayname());
            holder._isonline.setVisibility(View.VISIBLE);
        }
        else
        {
            holder._name.setText(_userDataVOArrayList.get(position).getDisplayname());
            holder._isactive.setText(_userDataVOArrayList.get(position).getUid());
            holder._userstatus.setText(_userDataVOArrayList.get(position).getGrp());
            holder._isonline.setVisibility(View.GONE);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private CardView _cardView;
        private TextView _name, _isactive, _userstatus;
        ImageView _isonline;

        public ViewHolder(View view)
        {
            super(view);
            _cardView = view.findViewById(R.id.card_view);
            _name = (TextView) view.findViewById(R.id.name);
            _isactive = (TextView) view.findViewById(R.id.is_active);
            _userstatus = (TextView) view.findViewById(R.id.text_status);
            _isonline = view.findViewById(R.id.is_online);
            _cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            FragmentTransaction ft = _fragmentManager.beginTransaction();

            Bundle bundle = new Bundle();
            bundle.putString(ChatOrGroupFragment.GUEST_ID,_userDataVOArrayList.get(getPosition()).getUserid());
            bundle.putString(ChatOrGroupFragment.GUEST_NAME,_userDataVOArrayList.get(getPosition()).getDisplayname());

            ChatOrGroupFragment chatOrGroupFragment = new ChatOrGroupFragment();
            chatOrGroupFragment.setArguments(bundle);
            ft.replace(R.id.contentlayout, chatOrGroupFragment, "ChatOrGroupFragment").addToBackStack("ChatOrGroupFragment").commit();
        }
    }

    @Override
    public int getItemCount()
    {
        return _userDataVOArrayList.size();
    }
}
