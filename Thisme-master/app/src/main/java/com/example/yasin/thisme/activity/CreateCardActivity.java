package com.example.yasin.thisme.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yasin.thisme.R;
import com.example.yasin.thisme.model.Card;
import com.example.yasin.thisme.model.ThismeDB;

import java.util.HashMap;
import java.util.Map;

public class CreateCardActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private Button addBtn,cancelBtn,saveBtn;
    private LinearLayout[] contentLinearLayout = new LinearLayout[50];
    private EditText[] met = new EditText[50];
    private EditText[] met2 = new EditText[50];
    private ThismeDB thismeDB;
    private EditText etName,etPhoneNum,etEamil,etQQ,etWeixin,etMiaosu;
    private Map<String,String> more = new HashMap<String,String>();
    private int fillFlag=0;
    Intent intent2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        intent2 = getIntent();
        initLayout();
    }

    private void initLayout() {
        final Intent intent = new Intent(this,MainActivity.class);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.activity_create_card_toolbar);
        if(intent2.getIntExtra("from",1)==1){
            toolbar.setTitle("创建自己的名片");
        }else{
            toolbar.setTitle("添加朋友的名片");
        }

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
        etPhoneNum = (EditText) findViewById(R.id.create_card_phonenum);
        etEamil = (EditText) findViewById(R.id.create_card_email);
        etQQ = (EditText) findViewById(R.id.create_card_qq);
        etWeixin = (EditText) findViewById(R.id.create_card_weixin);
        etMiaosu = (EditText) findViewById(R.id.create_card_miaosu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_card_more_btn:
                if(fillFlag<50){
                        addInformation();
                }else{
                    Toast.makeText(this,"最多添加50条",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.create_card_cancel_btn:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.create_card_save_btn:
                Card card = new Card();
                if(intent2.getIntExtra("from",1)==1){
                    card.setShuxing("1");
                }else{
                    card.setShuxing("2");
                }
                card.setName(etName.getText().toString());
                card.setPhoneNum(etPhoneNum.getText().toString());
                card.setEmail(etEamil.getText().toString());
                card.setQQ(etQQ.getText().toString());
                card.setWeixin(etWeixin.getText().toString());
                card.setMiaosu(etMiaosu.getText().toString());
                for(int i=0;i<fillFlag;i++){
                    more.put(met[i].getText().toString(),met2[i].getText().toString());
                }
                Log.e("yasin more",more.toString());
                card.setMore(more.toString());
                Log.e("cca",card.getEmail());
                thismeDB = ThismeDB.getInsstance(this);
                thismeDB.saveCard(card);
                Toast.makeText(this,"名片已创建",Toast.LENGTH_SHORT).show();
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
