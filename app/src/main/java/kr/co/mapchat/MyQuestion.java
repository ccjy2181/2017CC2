package kr.co.mapchat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

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

<<<<<<< HEAD
=======
    MessageDTO messageDTO;

    String[] test1 = { "test1", "test2" };
    String[] test2 = { "01:00", "01:02" };

>>>>>>> b0c44dc0fa2458b2f6699f627b54ca448f508e5f
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);

        setupToolbarWithUpNav(R.id.toolbar, "컴공 화석", R.drawable.ic_action_back);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setTitle("학생회관 줄 긴가요?", "2017-11-29 02:58");
        mAdapter = new ConversationRecyclerView(this,setData());
        mRecyclerView.setAdapter(mAdapter);
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

    public void setTitle(String title, String time){
        TextView tv_title = (TextView)findViewById(R.id.question_title);
        TextView tv_time = (TextView)findViewById(R.id.question_time);

        tv_title.setText(title);
        tv_time.setText(time);
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
