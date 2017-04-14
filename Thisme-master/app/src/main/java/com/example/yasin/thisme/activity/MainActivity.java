package com.example.yasin.thisme.activity;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.yasin.thisme.R;
import com.example.yasin.thisme.fragment.CardFragment;
import com.example.yasin.thisme.fragment.FriendFragment;
import com.example.yasin.thisme.fragment.MoreFragment;

import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, android.support.v7.widget.Toolbar.OnMenuItemClickListener, View.OnTouchListener,
        GestureDetector.OnGestureListener {

    private RadioButton cardbtn,friendbtn,morebtn;
    android.support.v7.widget.Toolbar toolbar;
    private  GestureDetector mGestureDetector;
    private MoreFragment moreFragment;
    private CardFragment cardFragment;
    private FriendFragment friendFragment;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mGestureDetector = new GestureDetector(this);
        LinearLayout ll=(LinearLayout)findViewById(R.id.main_layout);
        ll.setOnTouchListener(this);
        ll.setLongClickable(true);

        initLayout();
        friendFragment = new FriendFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,friendFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_friend, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.ab_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }



    private void initLayout() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(R.color.white);
        toolbar.setOnMenuItemClickListener(this);
        cardbtn = (RadioButton) findViewById(R.id.card_btn);
        friendbtn = (RadioButton) findViewById(R.id.friend_btn);
        morebtn = (RadioButton) findViewById(R.id.more_btn);
        cardbtn.setOnClickListener(this);
        friendbtn.setOnClickListener(this);
        morebtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_btn:
                setTitle("我的名片");
                cardFragment = new CardFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content,cardFragment).commit();
                break;
            case R.id.friend_btn:
                setTitle("联系人");
                friendFragment = new FriendFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content,friendFragment).commit();
                break;
            case R.id.more_btn:
                setTitle("更多");
                moreFragment = new MoreFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content,moreFragment).commit();
                break;

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ab_add_me:
                final Intent intent = new Intent(this,CreateCardActivity.class);
                final MaterialDialog materialDialog = new MaterialDialog(this);
                final LinearLayout contentLL = (LinearLayout) this.getLayoutInflater().inflate(R.layout.addcard_xuanxiang,null);
                materialDialog.setCanceledOnTouchOutside(true)
                        .setContentView(contentLL)
                        .setTitle("选择添加名片方式")
                        .setNegativeButton("否",new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                materialDialog.dismiss();
                            }
                        })
                        .setPositiveButton("是", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                RadioButton addmeBtn = (RadioButton) contentLL.findViewById(R.id.addme);
                                RadioButton addFriendBySD = (RadioButton) contentLL.findViewById(R.id.addfriend_sd);
                                RadioButton addFriendBySS = (RadioButton) contentLL.findViewById(R.id.addfriend_ss);
                                if (addmeBtn.isChecked()) {
                                    intent.putExtra("from", 1);
                                    startActivity(intent);
                                    finish();
                                } else if (addFriendBySD.isChecked()) {
                                    intent.putExtra("from", 2);
                                    startActivity(intent);
                                    finish();
                                } else if (addFriendBySS.isChecked()) {
                                    Log.e("yasin", "扫一扫正在开发");
                                    finish();
                                } else {

                                }
                            }
                        });
                    materialDialog.show();
                break;
            }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if((e2.getX()-e1.getX())>=50){//left
           if(morebtn.isChecked()){
               friendbtn.setChecked(true);
               friendbtn.callOnClick();
           }else if(friendbtn.isChecked()){
               cardbtn.setChecked(true);
               cardbtn.callOnClick();
           }
        }else if(e1.getX()-e2.getX()>=50){//right
            if(cardbtn.isChecked()){
                friendbtn.setChecked(true);
                friendbtn.callOnClick();
            }else if(friendbtn.isChecked()){
                morebtn.setChecked(true);
                morebtn.callOnClick();
            }
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }


}
