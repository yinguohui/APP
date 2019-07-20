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

public class ModifySignActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView modifysign;
    private EditText et_sign;
    private Gson gson;
    private Map<String,String> sign = new HashMap<>();
    private UserResult userResult;
    private UserResult.UserInfoBean list;
    private Map<String,String> map = new HashMap<>();

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    et_sign.setText(list.getUserSign());
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifysign);
        init();
    }
    private void init(){
        iv_back = findViewById(R.id.iv_back);
        modifysign = findViewById(R.id.modifysign);
        et_sign = findViewById(R.id.et_sign);
        gson = new Gson();

        iv_back.setOnClickListener(this);
        modifysign.setOnClickListener(this);
        map.put("userId",SpUtil.getString(ModifySignActivity.this,"userid",""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.modifysign:
                map.put("userSign",et_sign.getText().toString());
                if (map.get("userSign").length()>25){
                    ToastUtil.showToast(ModifySignActivity.this,"签名长度不能大于25");
                    return;
                }
                modifysign();
                break;
        }
    }
    private void modifysign(){
        OkHttpUtil.doPost("http://192.168.43.240:8080/user/updateuserinfo", map, new Callback() {
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

    //
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
            ToastUtil.showToast(ModifySignActivity.this,"修改成功");
        }else {
            ToastUtil.showToast(ModifySignActivity.this,result.getMessage());
        }
    }

}