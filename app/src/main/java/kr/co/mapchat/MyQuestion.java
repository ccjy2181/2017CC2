package kr.co.mapchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kr.co.mapchat.dto.MessageDTO;
import kr.co.mapchat.recylcerchat.ChatData;
import kr.co.mapchat.recylcerchat.ConversationRecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MyQuestion extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ConversationRecyclerView mAdapter;
    private EditText text;
    private Button send;
    private String name;
    List<ChatData> chatDataList;

    MessageDTO messageDTO;

    String[] test1 = { "test1", "test2" };
    String[] test2 = { "01:00", "01:02" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        messageDTO = (MessageDTO) bundle.getSerializable("messageDTO");

        setupToolbarWithUpNav(R.id.toolbar, messageDTO.getTitle(), R.drawable.ic_action_back);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ConversationRecyclerView(this,setTitle());
        mRecyclerView.setAdapter(mAdapter);

        // addReply에 있는 data들 전부 add 후 notifyDataSetChanged 실행
        mAdapter.addItem(addReply(test1, test2));

        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
            }
        }, 1000);
    }

    public void setName(String get_name){
        //채팅방 유저 이름 정해주기
        name = get_name;
    }

    public ChatData setTitle(){
        // String text, String time 파라메터로 가져와서 넣기, 타입은 2로 고정(내가 보내는 메시지)

        String text = "학관 식당 좀 많이 긴가요??";
        String time = "2017-11-29 02:58";
        String type = "2";

        ChatData item = new ChatData();
        item.setType(type);
        item.setText(text);
        item.setTime(time);

        return item;
    }

    public List<ChatData> addReply(String[] text, String[] time){

        List<ChatData> items = new ArrayList<>();
        for (int i=0; i<text.length; i++) {
            ChatData item = new ChatData();
            item.setType("1");
            item.setText(text[i]);
            item.setTime(time[i]);
            items.add(item);
        }
        return items;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_userphoto, menu);
        return true;
    }
}
