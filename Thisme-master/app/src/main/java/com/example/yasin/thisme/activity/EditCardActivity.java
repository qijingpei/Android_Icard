package com.example.yasin.thisme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yasin.thisme.R;
import com.example.yasin.thisme.model.Card;
import com.example.yasin.thisme.model.ThismeDB;
import com.example.yasin.thisme.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yasin on 2016/2/17.
 */
public class EditCardActivity extends AppCompatActivity implements View.OnClickListener{

        private Toolbar toolbar;
        private Button addBtn,cancelBtn,saveBtn;
        private LinearLayout[] contentLinearLayout = new LinearLayout[50];
        private EditText[] met = new EditText[50];
        private EditText[] met2 = new EditText[50];
        private ThismeDB thismeDB;
        private EditText etName,etPhoneNum,etEamil,etQQ,etWeixin,etMiaosu;
        private Map<String,String> more = new HashMap<String,String>();
        private int fillFlag=0;
        Card mCard;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_card);
            mCard = getIntent().getParcelableExtra("card");
            initLayout();
        }

        private void initLayout() {
            final Intent intent = new Intent(this,MainActivity.class);
            toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.activity_create_card_toolbar);
            toolbar.setTitle("修改自己的名片");
            toolbar.setTitleTextColor(R.color.light_blue);
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_36dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                    finish();
                }
            });
            addBtn = (Button) findViewById(R.id.create_card_more_btn);
            addBtn.setOnClickListener(this);
            cancelBtn = (Button) findViewById(R.id.create_card_cancel_btn);
            cancelBtn.setOnClickListener(this);
            saveBtn = (Button) findViewById(R.id.create_card_save_btn);
            saveBtn.setOnClickListener(this);
            etName = (EditText) findViewById(R.id.create_card_name);
            etName.setText(mCard.getName());
            etPhoneNum = (EditText) findViewById(R.id.create_card_phonenum);
            etPhoneNum.setText(mCard.getPhoneNum());
            etEamil = (EditText) findViewById(R.id.create_card_email);
            etEamil.setText(mCard.getEmail());
            etQQ = (EditText) findViewById(R.id.create_card_qq);
            etQQ.setText(mCard.getQQ());
            etWeixin = (EditText) findViewById(R.id.create_card_weixin);
            etWeixin.setText(mCard.getWeixin());
            etMiaosu = (EditText) findViewById(R.id.create_card_miaosu);
            etMiaosu.setText(mCard.getMiaosu());
            String[] dataS = Utils.HashMapString2StringArray(mCard.getMore());
            for(int i=dataS.length-1;i>0;i-=2){
                LinearLayout container = (LinearLayout) findViewById(R.id.create_card_more_information);
                contentLinearLayout[fillFlag] = (LinearLayout) getLayoutInflater().inflate(R.layout.more_item, container, false);
                met[fillFlag] = (EditText) contentLinearLayout[fillFlag].findViewById(R.id.mycard_more_name);
                met2[fillFlag] = (EditText) contentLinearLayout[fillFlag].findViewById(R.id.mycard_more_content);
                met[fillFlag].setText(dataS[i-1]);
                met2[fillFlag].setText(dataS[i]);
                container.addView(contentLinearLayout[fillFlag]);
                fillFlag++;
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.create_card_more_btn:
                    if(fillFlag<50){
                        addInformation();
                    }else{
                        Toast.makeText(this, "最多添加50条", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.create_card_cancel_btn:
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.create_card_save_btn:
                    mCard.setName(etName.getText().toString());
                    mCard.setPhoneNum(etPhoneNum.getText().toString());
                    mCard.setEmail(etEamil.getText().toString());
                    mCard.setQQ(etQQ.getText().toString());
                    mCard.setWeixin(etWeixin.getText().toString());
                    mCard.setMiaosu(etMiaosu.getText().toString());
                    for(int i=0;i<fillFlag;i++){
                        more.put(met[i].getText().toString(),met2[i].getText().toString());
                    }
                    mCard.setMore(more.toString());
                    thismeDB = ThismeDB.getInsstance(this);
                    thismeDB.xiugaiCard(mCard);
                    Toast.makeText(this,"名片已保存，可到名片出查看",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(this,MainActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
            }
        }
        //动态添加内容
        private void addInformation() {
            LinearLayout container = (LinearLayout) findViewById(R.id.create_card_more_information);
            contentLinearLayout[fillFlag] = (LinearLayout) getLayoutInflater().inflate(R.layout.more_item, container, false);
            met[fillFlag] = (EditText) contentLinearLayout[fillFlag].findViewById(R.id.mycard_more_name);
            met2[fillFlag] = (EditText) contentLinearLayout[fillFlag].findViewById(R.id.mycard_more_content);
            container.addView(contentLinearLayout[fillFlag]);
            fillFlag++;
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
