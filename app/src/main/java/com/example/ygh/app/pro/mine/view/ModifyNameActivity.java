package com.example.ygh.app.pro.mine.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.Result;
import com.example.ygh.app.bean.UserResult;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ModifyNameActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_modifyname;
    private EditText et_name;
    private String et_newname;
    private UserResult userResult;
    private UserResult.UserInfoBean list;
    Gson gson = new Gson();
    private Map<String,String> map = new HashMap<>();

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    et_name.setText(list.getUserName());
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyname);
        init();
    }
    private void init(){
        iv_back = findViewById(R.id.iv_back);
        tv_modifyname = findViewById(R.id.modifyname);
        et_name = findViewById(R.id.et_name);

        iv_back.setOnClickListener(this);
        tv_modifyname.setOnClickListener(this);
        map.put("userId",SpUtil.getString(ModifyNameActivity.this,"userid",""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.modifyname:
                map.put("userName",et_name.getText().toString());
                if (map.get("userName").equals("")||map.get("userName").length()>10){
                    ToastUtil.showToast(ModifyNameActivity.this,"用户名不能为空或长度大于10");
                    return;
                }
                modifyname(map);
                break;
        }
    }
    private void modifyname(Map<String,String> data){
        OkHttpUtil.doPost("http://192.168.43.240:8080/user/updateuserinfo", data , new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    fromresult(response.body().string());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        OkHttpUtil.doGet("http://192.168.43.240:8080/user/userinfo?user_id="+map.get("userId"), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
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
        userResult = gson.fromJson(data,UserResult.class);
        list = userResult.getList();
        if (userResult.getStatus().equals("success")){
            handler.sendEmptyMessage(1);
        }
    }
    private void  fromresult(String data){
        Result result = gson.fromJson(data,Result.class);
        if (result.getStatus().equals("success")){
            ToastUtil.showToast(ModifyNameActivity.this,"修改成功");
        }else {
            ToastUtil.showToast(ModifyNameActivity.this,result.getMessage());
        }
    }
}
