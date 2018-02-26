package com.demo.example.demochatapplication.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.demo.example.demochatapplication.R;
import com.demo.example.demochatapplication.Utils.SharedPrefranceManager;
import com.demo.example.demochatapplication.WebServices.Util.DataList;
import com.demo.example.demochatapplication.appBase.BaseActivity;
import com.demo.example.demochatapplication.appBase.SplashActivity;
import com.demo.example.demochatapplication.profile.model.ProfileVO;
import com.inscripts.interfaces.Callbacks;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by poonampatel on 26/02/18.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener
{
    public static String USER_ID = "guest_id";
    TextView _name, _status, _msg;
    ImageView _profile_image, _name_edit;
    LinearLayout _ll_card_view2, _ll_card_view3;
    String _userId = "";

    DataList<ProfileVO> _profileVOS = new DataList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity) getActivity()).toggleDrawerIcon(false);
        ((BaseActivity) getActivity()).settoolbarTitle(getActivity().getResources().getString(R.string.profile));
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
        View rootView = inflater.inflate(R.layout.profile_fragment_layout, container, false);

        _profile_image = rootView.findViewById(R.id.profile_image);

        _name = rootView.findViewById(R.id.name);
        _name_edit = rootView.findViewById(R.id.name_edit);

        _msg = rootView.findViewById(R.id.msg);
        _status = rootView.findViewById(R.id.status);

        _ll_card_view2 = rootView.findViewById(R.id.ll_card_view2);
        _ll_card_view3 = rootView.findViewById(R.id.ll_card_view3);

        _name_edit.setOnClickListener(this);
        _ll_card_view2.setOnClickListener(this);
        _ll_card_view3.setOnClickListener(this);

        if (_userId.equalsIgnoreCase("LoggedInUserID") || _userId.equalsIgnoreCase("") || _userId.isEmpty())
        {
            _userId = SharedPrefranceManager.getUserId(getContext());
        }
        else
        {
            _userId = getArguments().getString(USER_ID);
        }
        SplashActivity.cometChat.getUserInfo(_userId, new Callbacks()
        {
            @Override
            public void successCallback(JSONObject response)
            {
                _profileVOS = new DataList<>();
                Log.d("USer", "Response Success" + response.toString());
                if (response instanceof JSONObject)
                {
                    ProfileVO profileVO = new ProfileVO(response);
                    _profileVOS.add(profileVO);
                    Log.d("USer", "Response Success name" + profileVO.getN());
                    _name.setText(profileVO.getN());
                    _msg.setText(profileVO.getM());
                    _status.setText(profileVO.getS());
                    Glide.with(getContext())
                            .load("https:" + profileVO.getA())
                            .into(_profile_image);
                }
            }

            @Override
            public void failCallback(JSONObject response)
            {
                Log.d("USer", "Response failed" + response.toString());
            }
        });
        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.name_edit:
                Toast.makeText(getContext(), "Edit Name Called", Toast.LENGTH_LONG).show();
                break;
            case R.id.ll_card_view2:
                Toast.makeText(getContext(), "Edit Status Message Called", Toast.LENGTH_LONG).show();
                break;
            case R.id.ll_card_view3:
                Toast.makeText(getContext(), "Edit Online Status Called", Toast.LENGTH_LONG).show();
                break;

        }
    }

    public DataList<ProfileVO> getProfileVOS()
    {
        return _profileVOS;
    }

    public static Drawable getBitmapFromURL(String src)
    {
        try
        {
            Log.e("src", "http:" + src);
            URL url = new URL("http:" + src);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

//            Log.e("src", "http:"+src);
//            URL url = new URL("http:"+src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            Log.e("Bitmap", "returned");
            return new BitmapDrawable(bmp);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }

}
