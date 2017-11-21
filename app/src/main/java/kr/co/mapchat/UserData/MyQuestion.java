package kr.co.mapchat.UserData;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import kr.co.mapchat.Adapter.ListViewAdapter;
import kr.co.mapchat.R;

public class MyQuestion extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView listview ;
    ListViewAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);
        adapter = new ListViewAdapter() ;

//        // 리스트뷰 참조 및 Adapter달기
//        listview = (ListView)findViewById(R.id.myQuestionList);
//        listview.setAdapter(adapter);
//        adapter.addItem("1", "테스트", "내용");
//        adapter.addItem("2", "테스트", "내용");
//        adapter.addItem("3", "테스트", "내용");
//        adapter.addItem("4", "테스트", "내용");
//        adapter.addItem("5", "테스트", "내용");
//        adapter.addItem("6", "테스트", "내용");
//        adapter.addItem("7", "테스트", "내용");
//        adapter.notifyDataSetChanged();
//
//        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
//        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        adapter.addItem("99", "테스트", "내용");
        adapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
