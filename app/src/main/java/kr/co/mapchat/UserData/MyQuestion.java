package kr.co.mapchat.UserData;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import kr.co.mapchat.Adapter.ListViewAdapter;
import kr.co.mapchat.R;

public class MyQuestion extends AppCompatActivity {

    ListView listview ;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);

        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.myQuestionList);
        listview.setAdapter(adapter);
        adapter.addItem("1", "테스트", "내용");
        adapter.notifyDataSetChanged();
    }
}
