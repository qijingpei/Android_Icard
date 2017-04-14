package com.example.yasin.thisme.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yasin.thisme.R;
import com.example.yasin.thisme.activity.RegistActivity;
import com.example.yasin.thisme.model.User;
import com.example.yasin.thisme.utils.NetUtils;
import com.example.yasin.thisme.utils.SingletonUser;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Yasin on 2016/1/29.
 */
public class MoreFragment extends Fragment implements View.OnClickListener{

    User user;
    RelativeLayout meLayout;
    AppCompatActivity mContent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.more_fragment,container,false);
        mContent = (AppCompatActivity) this.getActivity();

//        user = User.getInsstance();

        initLayout(root);
//
//        if(user.isOnline()){
//
//        }else{
//
//        }

        return root;
    }

    private void initLayout(LinearLayout root) {
        meLayout = (RelativeLayout) root.findViewById(R.id.more_me);
        meLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_me:

                final MaterialDialog materialDialog = new MaterialDialog(mContent);
                //在dialog中加入登录界面
                final LinearLayout contentLL = (LinearLayout) mContent.getLayoutInflater().inflate(R.layout.activity_login,null);
                /*
                    自动填写用户名和密码
                 */
                //如果本地中有存储的手机号和密码信息  ，  那么赋值给控件
                //注：如果没有则会返回空字符串“”
                User storedUser = getStoredUser();
                ((EditText)contentLL.findViewById(R.id.et_phoneNumber)).setText(storedUser.getPhoneNumber());
                ((EditText)contentLL.findViewById(R.id.et_password)).setText(storedUser.getPassword());

                materialDialog.setTitle("登录")
                        .setCanceledOnTouchOutside(true)
                        .setContentView(contentLL)
                        .setNegativeButton("注册", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转到登录界面
                                Intent intent = new Intent(mContent,RegistActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("登录",new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EditText etPhoneNumber = (EditText)contentLL.findViewById(R.id.et_phoneNumber);
                                EditText etPassword = (EditText)contentLL.findViewById(R.id.et_password);
                                final CheckBox chstorePassword = (CheckBox) contentLL.findViewById(R.id.ch_storePassword);


                                final User form = new User();
                                form.setPhoneNumber(etPhoneNumber.getText().toString().trim());
                                form.setPassword(etPassword.getText().toString().trim());

                                //У���ֻ��ţ�11λ���֣������루3-15λ��
                                if (form.getPhoneNumber() ==null  || (form.getPhoneNumber().length() !=11)) {
                                    Toast.makeText(mContent,"请填写11位手机号",Toast.LENGTH_SHORT).show();
                                    return ;
                                }
                                if(form.getPassword() == null || form.getPassword().length()<3 || form.getPassword().length()>15) {
                                    Toast.makeText(mContent, "密码长度为3-15位", Toast.LENGTH_SHORT).show();
                                    return ;
                                }
                                new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        //��������
                                        Log.i("MoreFragment", "开启了子线程" );
                                        final String state = NetUtils.loginOfGet(form.getPhoneNumber(), form.getPassword());

                                        Log.i("MoreFragment", "状态码是：" + state);
                                        //ִ�����������߳���
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                //�����߳�����ʾ
                                                switch (Integer.valueOf(state)) {
                                                    case 0:// 0��ʾ�ɹ���½
                                                        Toast.makeText(mContent, "登录成功", Toast.LENGTH_SHORT).show();
                                                        //设置单例模式，初始化SingletonUser对象和其中的User对象的数据
                                                        SingletonUser.getInstance();
                                                        SingletonUser.setUser(form);
                                                        Log.i("MoreFragment", "即将开始实现记住密码功能");
                                                        //实现“记住密码”功能
                                                        if(chstorePassword.isChecked()) {
                                                            storePassword(form);
                                                        }
                                                        Log.i("MoreFragment","记住密码功能已经实现");
                                                        //跳转界面
                                                        materialDialog.dismiss();;
                                                        break;
                                                    case 1:// 1��ʾ�������
                                                        Toast.makeText(mContent, "密码错误", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    case 2:// 2��ʾ�û���������
                                                        Toast.makeText(mContent, "用户名不存在", Toast.LENGTH_SHORT).show();
                                                        break;

                                                    default:
                                                        break;
                                                }
                                                //Toast.makeText(MainActivity.this, state, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }).start();
                            }
                        });
                materialDialog.show();
                break;

        }
    }
    /*
    使用SharedPreferences存储手机号和密码，实现“记住密码”功能
     */
    private void storePassword(User user){
        //  MODE_PRIVATE --> Context.MODE_PRIVATE
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putString("phoneNumber",user.getPhoneNumber());
        editor.putString("password",user.getPassword());
        editor.commit();
    }
    private User getStoredUser() {
        SharedPreferences pref = getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        User user = new User();
        user.setPhoneNumber(pref.getString("phoneNumber", ""));//第二个参数是找不到相应的值时，返回的默认值或其他类型
        user.setPassword(pref.getString("password",""));
        return user;
    }
}
