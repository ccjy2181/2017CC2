package kr.co.mapchat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.mapchat.recylcerchat.ChatData;
import kr.co.mapchat.recylcerchat.ConversationRecyclerView;

public class MyAnswer extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ConversationRecyclerView mAdapter;
    private EditText text;
    private Button send;
    private String name;
    long mNow;
    SimpleDateFormat current_date = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat current_minute = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_answer);

        mNow = System.currentTimeMillis();
        current_date.format(new Date(mNow));
        current_minute.format(new Date(mNow));

        setupToolbarWithUpNav(R.id.toolbar, name, R.drawable.ic_action_back);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ConversationRecyclerView(this,setData());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
            }
        }, 1000);

        text = (EditText) findViewById(R.id.et_message);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
                    }
                }, 500);
            }
        });
        send = (Button) findViewById(R.id.bt_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!text.getText().equals("")){
                    List<ChatData> data = new ArrayList<ChatData>();
                    ChatData item = new ChatData();
                    item.setTime(current_date + " " + current_minute);
                    item.setType("2");
                    item.setText(text.getText().toString());
                    data.add(item);

                    // Firebase에 답변 저장하는 코드 적어야함!

                    mAdapter.addItem(data);
                    mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() -1);
                    text.setText("");
                }
            }
        });
    }

    public void setName(String get_name){
        //채팅방 유저 이름 정해주기
        name = get_name;
    }

    public List<ChatData> setData(){
        List<ChatData> data = new ArrayList<>();

        String text[] = {"Test"};
        String time[] = { current_date + "" + current_minute};
        String type[] = {"1"};

        for (int i=0; i<text.length; i++){
            ChatData item = new ChatData();
            item.setType(type[i]);
            item.setText(text[i]);
            item.setTime(time[i]);
            data.add(item);
        }

        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_userphoto, menu);
        return true;
    }
}
