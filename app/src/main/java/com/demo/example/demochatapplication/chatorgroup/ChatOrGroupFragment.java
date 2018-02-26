package com.demo.example.demochatapplication.chatorgroup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.example.demochatapplication.R;
import com.demo.example.demochatapplication.appBase.BaseActivity;

import java.util.ArrayList;

/**
 * Created by poonampatel on 26/02/18.
 */

public class ChatOrGroupFragment extends Fragment implements View.OnClickListener
{
    TextView _day_title;
    ImageView _more, chat_camera, chat_voice;
    EditText _chat_text;
    public static String GUEST_ID = "guest_id";
    public static String GUEST_NAME = "guest_id";
    String _guestId, _guestName;
    RecyclerView _chatTextWindowList;
    ArrayList<String> _stringArrayList;
    ChatTextAdapter _chatTextAdapter;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        _guestId = getArguments().getString(GUEST_ID);
        _guestName = getArguments().getString(GUEST_NAME);

        BaseActivity.chatWindowOpen = true;
        ((BaseActivity) getActivity()).invalidateMenu();
        ((BaseActivity) getActivity()).toggleDrawerIcon(false);
        if (_guestName != null)
        {
            ((BaseActivity) getActivity()).settoolbarTitle(_guestName);
        }
        else
        {
            ((BaseActivity) getActivity()).settoolbarTitle(getActivity().getResources().getString(R.string.guest));
        }
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
        View rootView = inflater.inflate(R.layout.chat_or_group_chat_fragment_layout, container, false);
        _day_title = rootView.findViewById(R.id.day_title);

        _chatTextWindowList = rootView.findViewById(R.id.chat_window_list);
        _chatTextWindowList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        _chatTextWindowList.setItemAnimator(null);

        _stringArrayList = new ArrayList<>();
        _more = rootView.findViewById(R.id.more);
        chat_camera = rootView.findViewById(R.id.chat_camera);
        chat_voice = rootView.findViewById(R.id.chat_voice);
        _chat_text = rootView.findViewById(R.id.chat_text);

        _more.setOnClickListener(this);
        chat_camera.setOnClickListener(this);
        chat_voice.setOnClickListener(this);
        _chatTextAdapter  = new ChatTextAdapter(_stringArrayList);
        _chatTextWindowList.setAdapter(_chatTextAdapter);
        _chatTextWindowList.setVisibility(View.VISIBLE);


        TextView.OnEditorActionListener exampleListener = new TextView.OnEditorActionListener()
        {
            public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_PREVIOUS || event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER)
                {
                    performSendMessage();
                    return true;
                }
                return false;
            }

        };
        _chat_text.setOnEditorActionListener(exampleListener);

        return rootView;
    }

    private void performSendMessage()
    {
        _chat_text.clearFocus();
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(_chat_text.getWindowToken(), 0);

        _stringArrayList.add(_chat_text.getText().toString());
        _chatTextWindowList.setVisibility(View.VISIBLE);
        _chatTextWindowList.setAdapter(_chatTextAdapter);
        _chatTextAdapter.setTextList(_stringArrayList);
        _chatTextAdapter.notifyDataSetChanged();
        _chat_text.setText("");
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.more:
                Toast.makeText(getActivity(), "Show More Options to send", Toast.LENGTH_LONG).show();
                break;
            case R.id.chat_camera:
                Toast.makeText(getActivity(), "Open Camera Clicked", Toast.LENGTH_LONG).show();
                break;
            case R.id.chat_voice:
                Toast.makeText(getActivity(), "Send Voice", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
