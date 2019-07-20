package com.example.ygh.app.pro.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.UserBean;
import com.example.ygh.app.mvp.presenter.impl.MvpBasePresenter;
import com.example.ygh.app.pro.base.presenter.BasePresenter;
import com.example.ygh.app.pro.base.view.BaseActivtiy;
import com.example.ygh.app.pro.mine.presenter.RegisterPresenter;
import com.example.ygh.app.pro.mine.view.navigation.LoginNavigationBuilder;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends BaseActivtiy implements View.OnClickListener{

    private RegisterPresenter registerPresenter;


    private EditText et_phone;
    private EditText et_password;
    private EditText et_config_password;
    private EditText et_code;
    private String shoujihao="";
    private String newmima = "";
    private Button bt_code;
    private String code ="";
    private boolean runningDownTimer;     //判断是否在倒计时
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initToolBar();
        initView();
    }
    private void initToolBar(){
        LinearLayout ll_register = (LinearLayout) findViewById(R.id.ll_register);
        LoginNavigationBuilder builder = new LoginNavigationBuilder(this);
        builder.setLeftIcon(R.drawable.login_close_selector).setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        builder.createAndBind(ll_register);
    }
    private void initView(){
        et_phone =  findViewById(R.id.et_phone);
        et_password =  findViewById(R.id.et_password);
        bt_code = findViewById(R.id.bt_code);
        et_code = findViewById(R.id.et_code);
        et_config_password = findViewById(R.id.et_config_password);
        findViewById(R.id.bt_code).setOnClickListener(this);
        findViewById(R.id.bt_register).setOnClickListener(this);
    }
    @Override
    public MvpBasePresenter bindPresenter() {
        registerPresenter = new RegisterPresenter(this);
        return registerPresenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_register:
                if(et_code.getText().toString().equals("")){
                    ToastUtil.showToast(RegisterActivity.this,"请先输入验证码");
                }else if(et_code.getText().toString().equals(code)){
                    register();
                }else {
                    ToastUtil.showToast(RegisterActivity.this,"验证码不正确");
                }
                break;
            case R.id.bt_code:
                onPcode();
                break;
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
        //产生随机验证码
        code= "";
        for (int i=0;i<6;i++){
            code +=r.nextInt(10);
        }

        OkHttpUtil.doGet("http://v.juhe.cn/sms/send?mobile="+shoujihao+"&tpl_id=157950&tpl_value=%23code%23%3D"+code+"&key=3ce6ab5eaa050dcc5780aba49b1c0363", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                }
            }
        });
    }
    private boolean judge(){
        shoujihao = et_phone.getText().toString();
        newmima = et_password.getText().toString();
        String configpwd = et_config_password.getText().toString();
        if (shoujihao.equals("")||newmima.equals("")){
            ToastUtil.showToast(RegisterActivity.this,"手机号或新密码不能为空");
            return false;
        }
        if (!newmima.equals(configpwd)){
            ToastUtil.showToast(RegisterActivity.this,"两次密码不一样");
            return false;
        }
        if (shoujihao.length()!=11){
            ToastUtil.showToast(RegisterActivity.this,"请输入正确的手机号");
            return false;
        }
        return true;
    }
    private void register(){
        if (!judge()){
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("userTel",shoujihao);
        map.put("userPassword",newmima);
        OkHttpUtil.doPost("http://192.168.43.240:8080/user/register", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                ToastUtil.showToast(RegisterActivity.this,"注册失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    formdata(response.body().string());
                }

            }
        });
    }
    private void formdata(String data){
        Gson gson = new Gson();
        Map map = gson.fromJson(data,Map.class);
        if (map.get("status").equals("success")){
            ToastUtil.showToast(RegisterActivity.this,"注册成功");
            finish();
        }
        else {
            ToastUtil.showToast(RegisterActivity.this,"注册失败"+map.get("message"));
        }
    }
}
