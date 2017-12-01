package kr.co.mapchat.recylcerchat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kr.co.mapchat.R;

/**
 * Created by Dytstudio.
 */

public class HolderYou extends RecyclerView.ViewHolder {

    private TextView time, chatText, date;

    public HolderYou(View v) {
        super(v);
        time = (TextView) v.findViewById(R.id.tv_time);
        chatText = (TextView) v.findViewById(R.id.tv_chat_text);
        date = (TextView) v.findViewById(R.id.tv_date);
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    public TextView getChatText() {
        return chatText;
    }

    public void setChatText(TextView chatText) {
        this.chatText = chatText;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }
}
