package com.demo.example.demochatapplication.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.demo.example.demochatapplication.R;
import com.demo.example.demochatapplication.login.controller.LoginController;
import com.demo.example.demochatapplication.network.NetworkChangeReceiver;

/**
 * Created by poonampatel on 23/02/18.
 */

public class LoginFragment extends Fragment implements View.OnClickListener
{
    private Button _btnLoginIn;
    private ProgressBar _loginPrgress;
    private EditText _eTextUsername;
    private EditText _eTextPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.login_fragment_layout, container, false);
        Log.d("Called", "Login Called");
        _btnLoginIn = view.findViewById(R.id.btnLoginIn);
        _loginPrgress = view.findViewById(R.id.loginInProgressBar);
        _eTextUsername = (EditText) view.findViewById(R.id.eTextUsername);
        _eTextPassword = (EditText) view.findViewById(R.id.eTextPassword);

        _btnLoginIn.setOnClickListener(this);
        setEditTextListener();
        this.hideSoftInput(view);
        return view;
    }

    private void setEditTextListener()
    {
        this._eTextUsername.addTextChangedListener(new TextWatcher()
        {
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() > 0)
                {
                    if (LoginFragment.this._eTextPassword.getText().length() > 0)
                    {
                        LoginFragment.this.enableSignInButton();
                    }
                }
                else
                {
                    LoginFragment.this.disableSignInButton();
                }

            }

            public void afterTextChanged(Editable s)
            {
            }
        });
        this._eTextPassword.addTextChangedListener(new TextWatcher()
        {
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() > 0)
                {
                    if (LoginFragment.this._eTextUsername.getText().length() > 0)
                    {
                        LoginFragment.this.enableSignInButton();
                    }
                }
                else
                {
                    LoginFragment.this.disableSignInButton();
                }

            }

            public void afterTextChanged(Editable s)
            {
            }
        });
    }

    private void enableSignInButton()
    {
        this._btnLoginIn.setEnabled(true);
        this._btnLoginIn.setAlpha(1.0F);
    }

    private void disableSignInButton()
    {
        this._btnLoginIn.setEnabled(false);
        this._btnLoginIn.setAlpha(0.3F);
    }


    @Override
    public void onClick(View v)
    {
        if (v == this._btnLoginIn)
        {
            this.hideSoftInput(v);
            this.onLogInButtonClicked();
        }
    }

    public void hideSoftInput(View view)
    {
        try
        {
            InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService("input_method");
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        catch (NullPointerException var3)
        {
        }

    }

    private void onLogInButtonClicked()
    {
        String userName = this._eTextUsername.getText().toString().trim();
        String password = this._eTextPassword.getText().toString().trim();

        boolean loginCancel = false;
        View loginFocusView = null;
        String errorMessage = "";
        if (TextUtils.isEmpty(userName))
        {
            loginFocusView = this._eTextUsername;
            loginCancel = true;
            errorMessage = this.getResources().getString(R.string.username_input_error);
        }

        if (TextUtils.isEmpty(password))
        {
            loginFocusView = this._eTextPassword;
            loginCancel = true;
            errorMessage = this.getResources().getString(R.string.password_input_error);
        }

        if (loginCancel)
        {
            loginFocusView.requestFocus();
            Toast.makeText(this.getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        }
        else if (NetworkChangeReceiver.isOnline())
        {
            this.disableItems();
            this.callService(userName, password);
        }
        else
        {
            String str = this.getResources().getString(R.string.network_detection_error);
            Toast.makeText(this.getActivity(), str, Toast.LENGTH_LONG).show();
        }

    }

    private void enableItems()
    {
        this._btnLoginIn.setEnabled(true);
        this._btnLoginIn.setAlpha(1.0F);
        this._loginPrgress.setVisibility(View.GONE);
    }

    public  void disableItems()
    {
        this._btnLoginIn.setEnabled(false);
        this._btnLoginIn.setAlpha(0.3F);
        this._loginPrgress.setVisibility(View.VISIBLE);
    }

    private void callService(String username, String password)
    {
        LoginController.getInstance().login(username, password);
    }
}
