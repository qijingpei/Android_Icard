package com.example.yasin.thisme.fragment;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.yasin.thisme.R;
import com.example.yasin.thisme.activity.EditCardActivity;
import com.example.yasin.thisme.activity.MainActivity;
import com.example.yasin.thisme.activity.ShowCardActivity;
import com.example.yasin.thisme.model.Card;
import com.example.yasin.thisme.model.ThismeDB;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Yasin on 2016/1/29.
 */
public class FriendFragment extends Fragment{

    private ThismeDB thismeDB;
    AppCompatActivity mContent;
    RecyclerView mRecyclerView;
    FriendAdapter mAdapter;
    List<Card> list = new ArrayList<Card>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        thismeDB = ThismeDB.getInsstance(this.getActivity().getApplicationContext());
        mContent = (AppCompatActivity) this.getActivity();
        list = thismeDB.loadFriendCard();
        mRecyclerView = new RecyclerView(this.getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new FriendAdapter(mContent,list);
        mAdapter.setOnItemClickLitener(new FriendAdapter.FriendAdapterClickLitener() {
            @Override
            public void OnItemClick(View view, int position) {
                Card mCard = list.get(position);
                Intent intent = new Intent(mContent, ShowCardActivity.class);
                intent.putExtra("card",mCard);
                startActivity(intent);
            }

            @Override
            public void OnCallBtn(int position) {

                Log.e("yasin","call");
                /*
                拨打电话
                 */
                Card mCard = list.get(position);
                //取出号码
                String phoneNumber = mCard.getPhoneNum();
                Log.i("FriendFragment","号码是："+phoneNumber);

                //根据号码拨打电话
                Intent intent = new Intent();
                intent.setData(Uri.parse("tel:"+phoneNumber));//指定要拨打的电话，加上tel:前缀让系统知道这是要打电话
                startActivity(intent);//执行
            }

            @Override
            public void OnMessageBtn(int position) {
                Log.e("yasin","message");
            }

            @Override
            public void OnEditBtn(int position) {
                Card mCard = list.get(position);
                Intent intent = new Intent(mContent, EditCardActivity.class);
                intent.putExtra("card",mCard);
                startActivity(intent);
            }

            @Override
            public void OnDeleteBtn(final int position) {
                final MaterialDialog materialDialog = new MaterialDialog(mContent);
                LinearLayout nullLayout = (LinearLayout) mContent.getLayoutInflater().inflate(R.layout.null_layout, null);
                materialDialog.setTitle("是否删除")
                        .setContentView(nullLayout)
                        .setPositiveButton("是", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                thismeDB.deleteCard(list.get(position).getCardId());
                                list.remove(position);
                                mAdapter.notifyItemRemoved(position);
                                materialDialog.dismiss();
                            }
                        })
                        .setNegativeButton("否", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                materialDialog.dismiss();
                            }
                        })
                        .setCanceledOnTouchOutside(true);
                materialDialog.show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return mRecyclerView;
    }

}
