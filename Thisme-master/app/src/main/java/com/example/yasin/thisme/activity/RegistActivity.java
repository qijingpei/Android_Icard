package com.example.yasin.thisme.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yasin.thisme.R;
import com.example.yasin.thisme.model.User;
import com.example.yasin.thisme.utils.NetUtils;


public class RegistActivity extends Activity implements OnClickListener {


    private static final String TAG = "RegistActivity";
    private EditText etPhoneNumber;
    private EditText etPassword;
    private EditText etName;//姓名
    private Button btnRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        etPhoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        etPassword = (EditText) findViewById(R.id.et_password);
        etName = (EditText) findViewById(R.id.et_name);
        btnRegist = (Button) findViewById(R.id.btn_regist);

        btnRegist.setOnClickListener(this);//设置注册按钮的点击事件

    }

    //使用HttpClient方式向服务器发送get请求
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_regist:
                //提交get请求
                Log.i(TAG, "开始注册的流程，使用Post");
			/*
			 * 新建一个User对象form_r，用来保存数据
			 */
                final User form_r = new User();
                form_r.setPhoneNumber(etPhoneNumber.getText().toString().trim());
                form_r.setPassword(etPassword.getText().toString().trim());
                form_r.setName(etName.getText().toString().trim());

                //校验手机号（11位数字） 、 密码（3-15位） 、 姓名（非空，不多于20字）
                Log.i(TAG,"执行到了57行校验处");
                if (form_r.getPhoneNumber() ==null  || (form_r.getPhoneNumber().length() !=11)) {
                    Toast.makeText(this, "请输入11位手机号", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(form_r.getPassword() == null || form_r.getPassword().length()<3 || form_r.getPassword().length()>15) {
                    Toast.makeText(this, "密码长度不在3-15位", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(form_r.getName() == null ||form_r.getName().isEmpty() || form_r.getName().length()>20) {
                    Toast.makeText(this, "姓名不为空，长度不超过20", Toast.LENGTH_SHORT).show();
                    return ;
                }
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        //请求网络
                        final String state = NetUtils.registOfPost(form_r);

                        Log.i(TAG, "返回码是:"+state);
                        //执行任务在主线程中
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //在主线程中提示
                                switch (Integer.valueOf(state)) {
                                    case 0:// 0表示成功注册
                                        Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:// 1表示号码已注册
                                        Toast.makeText(RegistActivity.this, "注册失败，号码已存在", Toast.LENGTH_SHORT).show();
                                        break;


                                    default:
                                        break;
                                }
                                //Toast.makeText(MainActivity.this, state, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}

