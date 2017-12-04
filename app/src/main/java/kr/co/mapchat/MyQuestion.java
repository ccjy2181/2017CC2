package kr.co.mapchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kr.co.mapchat.dto.AnswerDTO;
import kr.co.mapchat.dto.MessageDTO;
import kr.co.mapchat.recylcerchat.ChatData;
import kr.co.mapchat.recylcerchat.ConversationRecyclerView;
import kr.co.mapchat.util.fireBase.MyFirebaseConnector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyQuestion extends BaseActivity {
    private MyFirebaseConnector myFirebaseConnector;

    private RecyclerView mRecyclerView;
    private ConversationRecyclerView mAdapter;
    private EditText text;
    private Button send;
    private String name;
    List<AnswerDTO> data;

    MessageDTO messageDTO;

    SimpleDateFormat simpleDateFormat;

    String[] test1 = { "test1", "test2" };
    String[] test2 = { "01:00", "01:02" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);

        data = new ArrayList<>();

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        messageDTO = (MessageDTO) bundle.getSerializable("messageDTO");

        setupToolbarWithUpNav(R.id.toolbar, messageDTO.getTitle(), R.drawable.ic_action_back);

        setQuestion(messageDTO.getContents(), messageDTO.getRegdate());

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ConversationRecyclerView(this, data);
        mRecyclerView.setAdapter(mAdapter);

        myFirebaseConnector = new MyFirebaseConnector("message", this);
        myFirebaseConnector.getMyMessage(data, mAdapter, messageDTO.getKey());

//        mRecyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
//            }
//        }, 1000);
    }


    public void setName(String get_name){
        //채팅방 유저 이름 정해주기
        name = get_name;
    }

    public void setQuestion(String title, Date time){
        TextView tv_title = (TextView)findViewById(R.id.question_title);
        TextView tv_time = (TextView)findViewById(R.id.question_time);
        tv_title.setText(title);
        tv_time.setText(simpleDateFormat.format(time));
    }

    public List<ChatData> setData(){
        List<ChatData> data = new ArrayList<>();

        String text[] = {"네 아직 줄 길어요! 20분은 걸릴듯", "양식코너는 대기 별로 없어요. 대신 맛없음"};
        String time[] = {"02:59", "03:01"};
        String type[] = {"1","1"};

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
