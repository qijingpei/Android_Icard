package com.example.yasin.thisme.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yasin.thisme.R;
import com.example.yasin.thisme.model.Card;
import com.example.yasin.thisme.utils.Temp;
import com.example.yasin.thisme.utils.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ShowCardActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView mRecyclerView;
    Card mCard;
    String[] moreData = null;
    List<Temp> mdata = new ArrayList<Temp>();
    ShowAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);
        Intent intent = getIntent();
        mCard = intent.getParcelableExtra("card");
        Log.e("Show","id="+mCard.getCardId());

        toolbar = (Toolbar) findViewById(R.id.activity_show_card_toolbar);
        toolbar.setTitle("名片详细信息");
        toolbar.setTitleTextColor(R.color.light_blue);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.show_card_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter=new ShowAdapter(this,mdata));

    }

    private void initData() {
        mdata.add(new Temp("姓名",mCard.getName()));
        mdata.add(new Temp("手机号",mCard.getPhoneNum()));
        mdata.add(new Temp("邮箱",mCard.getEmail()));
        mdata.add(new Temp("QQ",mCard.getQQ()));
        mdata.add(new Temp("微信",mCard.getWeixin()));
        mdata.add(new Temp("描述",mCard.getMiaosu()));
        moreData = Utils.HashMapString2StringArray(mCard.getMore());
        for(int i=moreData.length-1;i>0;i-=2){
            mdata.add(new Temp(moreData[i-1], moreData[i]));
        };
    }

    private class ShowAdapter extends RecyclerView.Adapter {

        AppCompatActivity mContent;
        List<Temp> data;

        public ShowAdapter(AppCompatActivity mContent,List<Temp> data){
            this.mContent = mContent;
            this.data = data;
            for(int i=0;i<data.size();i++){
                Temp temp = data.get(i);
                Log.e(temp.getName(),temp.getContent());
            }
        }

        class ShowViewHolder extends RecyclerView.ViewHolder{

            TextView tvName,tvContent;

            public ShowViewHolder(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.show_card_name);
                tvContent = (TextView) itemView.findViewById(R.id.show_card_content);
            }

            public TextView getTV(int i){
                if(i==1){
                    return tvName;
                }else{
                    return tvContent;
                }
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ShowViewHolder holder = new ShowViewHolder(LayoutInflater.from(ShowCardActivity.this)
            .inflate(R.layout.item_layout, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ShowViewHolder vh = (ShowViewHolder) holder;
            TextView tvName = vh.getTV(1);
            TextView tvContent = vh.getTV(2);
            Temp temp;
            temp = data.get(position);
            tvName.setText(temp.getName());
            tvContent.setText(temp.getContent());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
