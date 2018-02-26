package com.demo.example.demochatapplication.Settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.example.demochatapplication.R;
import com.demo.example.demochatapplication.Utils.SharedPrefranceManager;
import com.demo.example.demochatapplication.appBase.BaseActivity;
import com.demo.example.demochatapplication.appBase.SplashActivity;
import com.demo.example.demochatapplication.profile.ProfileFragment;
import com.inscripts.interfaces.Callbacks;

import org.json.JSONObject;

/**
 * Created by poonampatel on 26/02/18.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener
{
    TextView _view_profile, logout;
    ImageView _view_profile_icon, _view_profile_arrow, _logout_icon;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity) getActivity()).toggleDrawerIcon(false);
        ((BaseActivity) getActivity()).settoolbarTitle(getActivity().getResources().getString(R.string.settings));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.more_item_layout, container, false);
        _view_profile = rootView.findViewById(R.id.view_profile);
        logout = rootView.findViewById(R.id.logout);

        _view_profile_icon = rootView.findViewById(R.id.view_profile_icon);
        _view_profile_arrow = rootView.findViewById(R.id.view_profile_arrow);
        _logout_icon = rootView.findViewById(R.id.logout_icon);

        _view_profile.setOnClickListener(this);
        logout.setOnClickListener(this);

        _view_profile_icon.setOnClickListener(this);
        _view_profile_arrow.setOnClickListener(this);
        _logout_icon.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.view_profile:
            case R.id.view_profile_arrow:
            case R.id.view_profile_icon:
                ShowProfileFragment();
                break;
            case R.id.logout:
            case R.id.logout_icon:
                setLogout();
                break;
        }
    }

    public void ShowProfileFragment()
    {
        Toast.makeText(getActivity(), "Profile called", Toast.LENGTH_LONG).show();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(ProfileFragment.USER_ID,"LoggedInUserID");

        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(bundle);
        ft.replace(R.id.contentlayout, profileFragment, "ProfileFragment").addToBackStack("ProfileFragment");
        ft.commit();
    }

    public void setLogout()
    {
        Toast.makeText(getActivity(), "logout called", Toast.LENGTH_LONG).show();
        SplashActivity.cometChat.logout(new Callbacks()
        {
            @Override
            public void successCallback(JSONObject response)
            {
                SharedPrefranceManager.setUserId(getContext(),"0");
                getActivity().finish();
                Toast.makeText(getActivity(), "logout success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failCallback(JSONObject response)
            {
                Toast.makeText(getActivity(), "logout failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ((BaseActivity) getActivity()).toggleDrawerIcon(false);
        ((BaseActivity) getActivity()).settoolbarTitle(getActivity().getResources().getString(R.string.settings));
    }
}
