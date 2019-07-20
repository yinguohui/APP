package com.example.ygh.app.pro.mine.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.UserResult;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.SpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView score;
    private ImageView iv_back;
    private UserResult userResult;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    score.setText("积分"+userResult.getList().getUserScore());
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        init();
    }
    private void init(){
        score = findViewById(R.id.tv_score);
        iv_back = findViewById(R.id.iv_back);

        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String id  = SpUtil.getString(ScoreActivity.this,"userid","");
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
        if (userResult.getStatus().equals("success")){
            handler.sendEmptyMessage(1);
        }

    }
}
