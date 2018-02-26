package com.demo.example.demochatapplication.chatorgroup;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.example.demochatapplication.R;

import java.util.ArrayList;

/**
 * Created by poonampatel on 26/02/18.
 */

public class ChatTextAdapter extends RecyclerView.Adapter<ChatTextAdapter.ViewHolder>
{
    ArrayList<String> _textList;

    public ChatTextAdapter(ArrayList<String> text)
    {
        _textList = text;
    }

    @Override
    public ChatTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_text_item, parent, false);
        return new ChatTextAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatTextAdapter.ViewHolder holder, int position)
    {
        Log.d("TEXT","TEXT"+_textList.get(position));
        holder._textView.setText(_textList.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView _textView;

        public ViewHolder(View view)
        {
            super(view);
            _textView = view.findViewById(R.id.text1);
        }
    }

    @Override
    public int getItemCount()
    {
        return _textList.size();
    }

    public void setTextList(ArrayList<String> textList)
    {
        _textList = textList;
    }
}
