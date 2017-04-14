package com.example.yasin.thisme.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yasin.thisme.R;
import com.example.yasin.thisme.fragment.FriendAdapter;
import com.example.yasin.thisme.model.Card;
import com.example.yasin.thisme.model.ThismeDB;
import com.example.yasin.thisme.utils.Temp;
import com.example.yasin.thisme.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yasin on 2016/2/17.
 */
public class SearchResultActivity extends AppCompatActivity{
    Toolbar toolbar;
    RecyclerView mRecyclerView;
    List<Card> mdata = new ArrayList<Card>();
    List<Card> list = new ArrayList<Card>();
    FriendAdapter mAdapter;
    ThismeDB thismeDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);
        Log.e("yasin","search");
        String SearchContent = getIntent().getStringExtra(SearchManager.QUERY);
        thismeDB = ThismeDB.getInsstance(this);
        list = thismeDB.loadFriendCard();
        if(SearchContent!=null){
            for(int i=0;i<list.size();i++){
                if(list.get(i).getName().contains(SearchContent)){
                    mdata.add(list.get(i));
                }
            }
        }
        if(mdata.size()==0){
            Toast.makeText(this,"没有该联系人",Toast.LENGTH_LONG).show();
        }
        toolbar = (Toolbar) findViewById(R.id.activity_show_card_toolbar);
        toolbar.setTitle("搜索结果");
        toolbar.setTitleTextColor(R.color.light_blue);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.show_card_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new FriendAdapter(this,mdata);
        mAdapter.setOnItemClickLitener(new FriendAdapter.FriendAdapterClickLitener() {
            @Override
            public void OnItemClick(View view, int position) {

            }

            @Override
            public void OnCallBtn(int position) {

            }

            @Override
            public void OnMessageBtn(int position) {

            }

            @Override
            public void OnEditBtn(int position) {

            }

            @Override
            public void OnDeleteBtn(int position) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }

}
