package kr.co.mapchat.Fragments;

import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.mapchat.MainActivity;
import kr.co.mapchat.MyAnswer;
import kr.co.mapchat.MyQuestion;
import kr.co.mapchat.R;
import kr.co.mapchat.recyclerview.Chat;
import kr.co.mapchat.recyclerview.ChatAdapter;

public class FragmentMyAnswer extends Fragment implements ChatAdapter.ViewHolder.ClickListener{
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private TextView tv_selection;
    long mNow;
    Date mDate;
    SimpleDateFormat current_date = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat current_minute = new SimpleDateFormat("HH:mm");

    public FragmentMyAnswer(){
        setHasOptionsMenu(true);
    }
    public void onCreate(Bundle a){
        super.onCreate(a);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_answer, null, false);

        getActivity().supportInvalidateOptionsMenu();
        ((MainActivity)getActivity()).changeTitle(R.id.toolbar, "내 답변");

        tv_selection = (TextView) view.findViewById(R.id.tv_selection);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ChatAdapter(getContext(),setData(),this);
        mRecyclerView.setAdapter (mAdapter);

        return view;
    }
    public List<Chat> setData(){
        List<Chat> data = new ArrayList<>();

        // 초기 셋팅 + 파일 추가시 갱신해야함

        String name[]= {"Laura Owens", "Angela Price", "Donald Turner", "Kelly", "Julia Harris", "Laura Owens", "Angela Price", "Donald Turner", "Kelly", "Julia Harris" };
        @DrawableRes int img[]= {R.drawable.userpic , R.drawable.user1, R.drawable.user2, R.drawable.user3, R.drawable.user4 , R.drawable.userpic , R.drawable.user1, R.drawable.user2, R.drawable.user3, R.drawable.user4 };
        boolean online[] = {true, false, true, false, true, true, true, false, false, true};
        String lastchat[]= {"Hi Laura Owens", "Hi there how are you", "Can we meet?", "Ow this awesome", "How are you?", "Ow this awesome", "How are you?", "Ow this awesome", "How are you?", "How are you?" };

        for (int i = 0; i<10; i++){


            Chat chat = addChat(name[i], img[i], online[i], lastchat[i]);


            data.add(chat);
        }
        return data;
    }

    public Chat addChat(String name, int img, boolean online, String lastchat){
        Chat chat = new Chat();
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        String getDate = current_date.format(mDate);
        String getMinute = current_minute.format(mDate);
        chat.setmTime(getDate + " " + getMinute);
        chat.setName(name);
        chat.setImage(img);
        chat.setOnline(online);
        chat.setLastChat(lastchat);

        return chat;
    }

    @Override
    public void onItemClicked (int position) {
        startActivity(new Intent(getActivity(), MyAnswer.class));
    }

    @Override
    public boolean onItemLongClicked (int position) {
        toggleSelection(position);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection (position);
        if (mAdapter.getSelectedItemCount()>0){
            tv_selection.setVisibility(View.VISIBLE);
        }else
            tv_selection.setVisibility(View.GONE);


        getActivity().runOnUiThread(new Runnable() {
            public void run()
            {
                tv_selection.setText("Delete ("+mAdapter.getSelectedItemCount()+")");
            }
        });

    }


}

