package com.example.newchatbot.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.newchatbot.R;
import com.example.newchatbot.model.ChatMessage;

import java.util.List;

public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {
    private static final int MY_MESSAGE = 0, BOT_MESSAGE = 1;
    public ChatMessageAdapter(Context context, List<ChatMessage> data) {
        super(context, R.layout.my_message, data);
    }
    @Override
    public int getViewTypeCount() {
        // my message, other message, my image, other image
        return 4;
    }
    @Override
    public int getItemViewType(int position) {
        ChatMessage item = getItem(position);
        if (item.isMine())
            return MY_MESSAGE;
        else
            return BOT_MESSAGE;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (viewType == MY_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_message, parent, false);
            TextView tvMyMessage = (TextView) convertView.findViewById(R.id.message_body);
            tvMyMessage.setText(getItem(position).getContent());
        } else if (viewType == BOT_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bot_message, parent, false);
            TextView tvBotMessage = (TextView) convertView.findViewById(R.id.message_body);
            tvBotMessage.setText(getItem(position).getContent());
        }
        return convertView;

    }
}
