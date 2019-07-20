package com.example.ygh.app.pro.mine.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class ModifySexActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView modifysex;
    private RadioGroup rgSex;
    private RadioButton radio0,radio1;
    private Gson gson;
    private UserResult userResult;
    private UserResult.UserInfoBean list;
    private Map<String,String> map = new HashMap<>();
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (list.getUserSex().equals("男")){
                        radio0.setChecked(true);
                    }else if(list.getUserSex().equals("女")){
                        radio1.setChecked(true);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifysex);
        init();
    }
    private void init(){
        iv_back = findViewById(R.id.iv_back);
        modifysex = findViewById(R.id.modifysex);
        rgSex = findViewById(R.id.rgSex);
        radio0 = findViewById(R.id.radio0);
        radio1 = findViewById(R.id.radio1);
        gson = new Gson();

        iv_back.setOnClickListener(this);
        modifysex.setOnClickListener(this);
        map.put("userId",SpUtil.getString(ModifySexActivity.this,"userid",""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.modifysex:
                modifysex();
                break;
        }
    }
    private void modifysex(){
        if (R.id.radio0==rgSex.getCheckedRadioButtonId()){
            map.put("userSex","男");
        }else if (R.id.radio1==rgSex.getCheckedRadioButtonId()){
            map.put("userSex","女");
        }
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
            ToastUtil.showToast(ModifySexActivity.this,"修改成功");
        }else {
            ToastUtil.showToast(ModifySexActivity.this,result.getMessage());
        }
    }
}
