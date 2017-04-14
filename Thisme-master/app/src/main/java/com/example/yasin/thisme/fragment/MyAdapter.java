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
 * Created by Yasin on 2016/2/15.
 * 用于CardFragment的Recyclerview
 */
class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private AppCompatActivity mContent;
    private List<Card> data;

    private MyAdapterClickLitener myAdapterClickLitener;

    public MyAdapter(AppCompatActivity mContent,List<Card> list) {
        this.mContent = mContent;
        this.data = list;
    }
    /*
    * 设置item格式
    * */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ThismeViewHolder holder = new ThismeViewHolder(LayoutInflater.from(mContent)
                .inflate(R.layout.mycard_card_view, parent,
                        false));
        return holder;
    }

    /*
    * 绑定数据
    * */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ThismeViewHolder vh = (ThismeViewHolder) holder;
        TextView tvName = vh.getTv(1);
        TextView tvMiaosu = vh.getTv(2);
        Card mCard;
        mCard = data.get(position);
        tvName.setText(mCard.getName());
        tvMiaosu.setText(mCard.getMiaosu());
        Button shareBtn,editBtn,deleteBtn;
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
            shareBtn = vh.getBtn(1);
            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = vh.getLayoutPosition();
                    myAdapterClickLitener.OnShareBtn(pos);
                }
            });
            editBtn = vh.getBtn(2);
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = vh.getLayoutPosition();
                    myAdapterClickLitener.OnEditBtn(pos);
                }
            });
            deleteBtn = vh.getBtn(3);
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

    public void setOnItemClickLitener(MyAdapterClickLitener myAdapterClickLitener) {
        this.myAdapterClickLitener = myAdapterClickLitener;
    }

    class ThismeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvName, tvMiaosu;
        private Button shareBtn, editBtn, deleteBtn;

        public ThismeViewHolder(final View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.mycard_name);
            tvMiaosu = (TextView) itemView.findViewById(R.id.mycard_miaosu);
            shareBtn = (Button) itemView.findViewById(R.id.mycard_share);
            editBtn = (Button) itemView.findViewById(R.id.mycard_edit);
            deleteBtn = (Button) itemView.findViewById(R.id.mycard_delete);
        }

        public TextView getTv(int flag) {
            if (flag == 1) {
                return tvName;
            } else {
                return tvMiaosu;
            }
        }

        public Button getBtn(int flag){
            if(flag==1){
                return shareBtn;
            }else if(flag==2){
                return editBtn;
            }else{
                return deleteBtn;
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mycard_share:
                    break;

            }
        }
    }

    public interface MyAdapterClickLitener {
        /*
        * 整个item的clicklitener
        * */
        void OnItemClick(View view, int position);
        /*
        * sharebtn的clicklistener
        * */
        void OnShareBtn(int position);
        void OnEditBtn(int position);
        void OnDeleteBtn(int position);
    }


}
