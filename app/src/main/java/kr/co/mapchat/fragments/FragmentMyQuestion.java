package kr.co.mapchat.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.co.mapchat.MyQuestion;
import kr.co.mapchat.MainActivity;
import kr.co.mapchat.R;
import kr.co.mapchat.recyclerview.Chat;
import kr.co.mapchat.recyclerview.ChatAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dytstudio.
 */

public class FragmentMyQuestion extends Fragment implements ChatAdapter.ViewHolder.ClickListener{
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private TextView tv_selection;
    long mNow;
    Date mDate;
    URL url;
    Bitmap bitmap;
    SimpleDateFormat current_date = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat current_minute = new SimpleDateFormat("HH:mm");

    public FragmentMyQuestion(){
        setHasOptionsMenu(true);
    }
    public void onCreate(Bundle a){
        super.onCreate(a);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_question, null, false);

        getActivity().supportInvalidateOptionsMenu();
        ((MainActivity)getActivity()).changeTitle(R.id.toolbar, "내 질문");

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

        String name[]= {"컴공 화석" };
        URL img = null;
        try {
            img = new URL("http://map2.daum.net/map/imageservice?IW=300&IH=300&MX=495112&MY=1129685&SCALE=2.5&CX=495113&CY=1129686&service=open");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        boolean online[] = {true };
        String lastchat[]= {"학관 식당 줄 많이 긴가요??"};

        for (int i = 0; i<1; i++){

            Chat chat = addChat(name[i], img, online[i], lastchat[i]);

            data.add(chat);
        }
        return data;
    }

    public Chat addChat(String name, URL img, boolean online, String lastchat){
        url = img;
        Chat chat = new Chat();
        Thread mThread = new Thread() {
            @Override
            public void run() {

                try {
                    //  아래 코드는 웹에서 이미지를 가져온 뒤
                    //  이미지 뷰에 지정할 Bitmap을 생성하는 과정

                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                } catch(IOException ex) {

                }
            }
        };

        mThread.start();

        try {
            mThread.join();

            mNow = System.currentTimeMillis();
            mDate = new Date(mNow);
            String getDate = current_date.format(mDate);
            String getMinute = current_minute.format(mDate);
            chat.setmTime(getDate + " " + getMinute);
            chat.setName(name);
            chat.setImage(bitmap);
            chat.setOnline(online);
            chat.setLastChat(lastchat);

            return chat;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return chat;
    }

    @Override
    public void onItemClicked (int position) {
        startActivity(new Intent(getActivity(), MyQuestion.class));
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
