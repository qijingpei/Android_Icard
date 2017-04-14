package com.example.yasin.thisme.fragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yasin.thisme.R;
import com.example.yasin.thisme.model.Card;

import java.util.List;

/**
 * Created by Yasin on 2016/2/17.
 * 用于FriendFragment的Recyclerview
 */
public class FriendAdapter extends RecyclerView.Adapter {

    private AppCompatActivity mContent;
    private List<Card> data;

    private FriendAdapterClickLitener myAdapterClickLitener;

    public FriendAdapter(AppCompatActivity mContent,List<Card> list) {
        this.mContent = mContent;
        this.data = list;
    }
    /*
    * 设置item格式
    * */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FriendViewHolder holder = new FriendViewHolder(LayoutInflater.from(mContent)
                .inflate(R.layout.friends_item, parent,
                        false));
        return holder;
    }

    /*
    * 绑定数据
    * */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final FriendViewHolder vh = (FriendViewHolder) holder;
        TextView tvName = vh.getTv(1);
        TextView tvNum = vh.getTv(2);
        Card mCard;
        mCard = data.get(position);
        tvName.setText(mCard.getName());
        tvNum.setText(mCard.getPhoneNum());
        Button MessageBtn,CallBtn,editBtn,deleteBtn;
        /*
        * 自己写ItemClickListener
        * */
        if(myAdapterClickLitener!=null){
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = vh.getLayoutPosition();
                    myAdapterClickLitener.OnItemClick(vh.itemView,pos);
                }
            });
            CallBtn = vh.getBtn(1);
            CallBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = vh.getLayoutPosition();
                    myAdapterClickLitener.OnCallBtn(pos);
                }
            });
            MessageBtn = vh.getBtn(2);
            MessageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = vh.getLayoutPosition();
                    myAdapterClickLitener.OnMessageBtn(pos);
                }
            });
            editBtn = vh.getBtn(3);
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = vh.getLayoutPosition();
                    myAdapterClickLitener.OnEditBtn(pos);
                }
            });
            deleteBtn = vh.getBtn(4);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = vh.getLayoutPosition();
                    myAdapterClickLitener.OnDeleteBtn(pos);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickLitener(FriendAdapterClickLitener myAdapterClickLitener) {
        this.myAdapterClickLitener = myAdapterClickLitener;
    }

    class FriendViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName, tvNum;
        private Button CallBtn,MessageBtn, editBtn, deleteBtn;

        public FriendViewHolder(final View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.mycard_name);
            tvNum = (TextView) itemView.findViewById(R.id.friend_phone_num);
            CallBtn = (Button) itemView.findViewById(R.id.call);
            MessageBtn = (Button) itemView.findViewById(R.id.message);
            editBtn = (Button) itemView.findViewById(R.id.mycard_edit);
            deleteBtn = (Button) itemView.findViewById(R.id.mycard_delete);
        }

        public TextView getTv(int flag) {
            if (flag == 1) {
                return tvName;
            } else {
                return tvNum;
            }
        }

        public Button getBtn(int flag){
            if(flag==1){
                return CallBtn;
            }else if(flag==2){
                return MessageBtn;
            }else if(flag==3){
                return editBtn;
            }else{
                return deleteBtn;
            }
        }
    }

    public interface FriendAdapterClickLitener {
        /*
        * 整个item的clicklitener
        * */
        void OnItemClick(View view, int position);
        /*
        * callbtn的clicklistener
        * */
        void OnCallBtn(int position);
        void OnMessageBtn(int position);
        void OnEditBtn(int position);
        void OnDeleteBtn(int position);
    }


}
