package kr.co.mapchat.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
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
import kr.co.mapchat.adapter.MessageADT;
import kr.co.mapchat.dto.MessageDTO;
import kr.co.mapchat.util.fireBase.MyFirebaseConnector;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentMyQuestion extends Fragment implements MessageADT.ViewHolder.ClickListener{
    private MyFirebaseConnector myFirebaseConnector;

    private RecyclerView mRecyclerView;
    private MessageADT mAdapter;
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

        List<MessageDTO> data = new ArrayList<>();

        getActivity().supportInvalidateOptionsMenu();
        ((MainActivity)getActivity()).changeTitle(R.id.toolbar, "내 질문");

        tv_selection = (TextView) view.findViewById(R.id.tv_selection);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MessageADT(getContext(), data,this);
        mRecyclerView.setAdapter (mAdapter);

        myFirebaseConnector = new MyFirebaseConnector("message", this.getContext());
        myFirebaseConnector.getMyMessage(data, mAdapter);

        return view;
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
