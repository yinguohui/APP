package com.example.ygh.app.pro.mine.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.Result;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ForgetActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_phone;
    EditText et_code;
    Button bt_code;
    Button bt_forget;
    String code = "";
    String shoujihao="";
    String newmima = "";
    EditText et_newpassword;
    private boolean runningDownTimer;     //判断是否在倒计时
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                    ToastUtil.showToast(ForgetActivity.this,"网络错误");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        init();
    }
    private void init(){
        et_phone = findViewById(R.id.et_phone);
        et_code = findViewById(R.id.et_code);
        bt_code = findViewById(R.id.bt_code);
        bt_forget = findViewById(R.id.bt_forget);
        et_newpassword = findViewById(R.id.et_newpassword);
        bt_code.setOnClickListener(this);
        bt_forget.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_code:
                onPcode();
                break;
            case R.id.bt_forget:
                newpassword();
                break;

        }
    }

    private void  newpassword(){
        if (!judge()){
            return;
        }
        if (et_code.getText().toString().equals("")){
            ToastUtil.showToast(ForgetActivity.this,"请输入验证码");
        }
        if (code.equals(et_code.getText().toString())){
            //重置修改密码
            OkHttpUtil.doGet("http://192.168.43.240:8080/user/updateuserpassword?userTel="+shoujihao+"&userPassword="+newmima, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    handler.sendEmptyMessage(-1);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()){
                        Gson gson = new Gson();
                        Result result =gson.fromJson(response.body().string(),Result.class);
                        if (result.getStatus().equals("success")){
                            finish();
                            ToastUtil.showToast(ForgetActivity.this,"重置成功，返回登陆");
                        }else {
                            ToastUtil.showToast(ForgetActivity.this,"手机号尚未注册");
                        }
                    }else {
                        handler.sendEmptyMessage(-1);
                    }
                }
            });
        }else {
            ToastUtil.showToast(ForgetActivity.this,"验证码不正确");
        }
    }

    /**
     * 倒计时
     */
    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            runningDownTimer = true;
            bt_code.setText((l / 1000) + "秒后重发");
        }

        @Override
        public void onFinish() {
            runningDownTimer = false;
            bt_code.setText("重新发送");
        }

    };

    //发送验证码按钮
    public void onPcode() {
        if (!judge()){
            return;
        }
        //如果60秒倒计时没有结束
        if (runningDownTimer) {
            return;
        }

        bt_code.setText("正在发送");
        getcode();
        downTimer.start();  // 倒计时开始
    }
    //获取后台验证码
    private void getcode(){
        Random r = new Random();
        code = "";
        //产生随机验证码
        for (int i=0;i<6;i++){
            code +=r.nextInt(10);
        }

        OkHttpUtil.doGet("http://v.juhe.cn/sms/send?mobile="+shoujihao+"&tpl_id=154057&tpl_value=%23code%23%3D"+code+"&key=3ce6ab5eaa050dcc5780aba49b1c0363", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(-1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                }else {
                    handler.sendEmptyMessage(-1);
                }
            }
        });
    }
    private boolean judge(){
        shoujihao = et_phone.getText().toString();
        newmima = et_newpassword.getText().toString();
        if (shoujihao.equals("")||newmima.equals("")){
            ToastUtil.showToast(ForgetActivity.this,"手机号或新密码不能为空");
            return false;
        }
        if (shoujihao.length()!=11){
            ToastUtil.showToast(ForgetActivity.this,"请输入正确的手机号");
            return false;
        }
        return true;
    }

}
