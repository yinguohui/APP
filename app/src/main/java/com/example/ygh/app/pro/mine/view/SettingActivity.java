package com.example.ygh.app.pro.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.UserResult;
import com.example.ygh.app.pro.essence.view.views.CircleNetworkImageImage;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.VolleyUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_back;
    CircleNetworkImageImage iv_image;
    RelativeLayout rl_name,rl_sex,rl_sign,rl_image,rl_password;
    TextView tv_name,tv_sex,tv_sign,tv_phone;

    private UserResult userResult;
    private UserResult.UserInfoBean list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    VolleyUtils.loadImage(SettingActivity.this,iv_image,list.getUserImg());
                    tv_name.setText(list.getUserName());
                    tv_sex.setText(list.getUserSex());
                    tv_sign.setText(list.getUserSign());
                    tv_phone.setText(list.getUserTel());
                    break;
            }
        }
    };
    private void init(){
        iv_back = findViewById(R.id.iv_back);
        iv_image= findViewById(R.id.iv_image);

        tv_phone = findViewById(R.id.tv_phone);
        rl_name = findViewById(R.id.rl_name);
        rl_sex = findViewById(R.id.rl_sex);
        rl_sign = findViewById(R.id.rl_sign);
        rl_image = findViewById(R.id.rl_image);
        rl_password =findViewById(R.id.rl_password);

        tv_name = findViewById(R.id.tv_name);
        tv_sex = findViewById(R.id.tv_sex);
        tv_sign = findViewById(R.id.tv_sign);

        iv_back.setOnClickListener(this);
        rl_name.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_sign.setOnClickListener(this);
        rl_image.setOnClickListener(this);
        rl_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_name:
                startActivity(new Intent(SettingActivity.this,ModifyNameActivity.class));
                break;
            case R.id.rl_sex:
                startActivity(new Intent(SettingActivity.this,ModifySexActivity.class));
                break;
            case R.id.rl_sign:
                startActivity(new Intent(SettingActivity.this,ModifySignActivity.class));
                break;
            case R.id.rl_image:
                startActivity(new Intent(SettingActivity.this,ModifyHeafImageActivity.class));
                break;
            case R.id.rl_password:
                startActivity(new Intent(SettingActivity.this,ModifyPasswordActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String id  = SpUtil.getString(SettingActivity.this,"userid","");
        OkHttpUtil.doGet("http://192.168.43.240:8080/user/userinfo?user_id="+id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    fromdata(response.body().string());
                }
            }
        });
    }
    private void fromdata(String data){
        Gson gson = new Gson();
        userResult = gson.fromJson(data,UserResult.class);
        list = userResult.getList();
        if (userResult.getStatus().equals("success")){
            handler.sendEmptyMessage(1);
        }

    }
}
