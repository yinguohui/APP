package com.example.ygh.app.pro.mine.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.Result;
import com.example.ygh.app.bean.UserResult;
import com.example.ygh.app.pro.newpost.view.BuyTicketsActivity;
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

public class BuyTicketActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_back;
    Button buy_ticket;
    Map<String,String> map = new HashMap<>();
    Double money = 0.0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyticket);
        init();
    }
    private void init(){
        iv_back = findViewById(R.id.iv_back);
        buy_ticket = findViewById(R.id.buy_ticket);
        map.put("userid",SpUtil.getString(BuyTicketActivity.this,"userid",""));
        map.put("sellmoney",getIntent().getStringExtra("sellmoney"));
        map.put("selluserid",getIntent().getStringExtra("selluserid"));
        map.put("seatid",getIntent().getStringExtra("seatid"));
        iv_back.setOnClickListener(this);
        buy_ticket.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.buy_ticket:
                getmoney();
                if (money-Double.parseDouble(map.get("sellmoney"))>=0){
                    map.put("money",money-Double.parseDouble(map.get("sellmoney"))+"");
                    showNormalDialog();
                }else {
                    ToastUtil.showToast(BuyTicketActivity.this,"你的余额不足，请充值");
                }
                break;
        }
    }

    private void getmoney(){
        OkHttpUtil.doGet("http://192.168.43.240:8080/user/userinfo?user_id=" + map.get("userid"), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                finish();
                ToastUtil.showToast(BuyTicketActivity.this,"请重试");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    jiexidata(response.body().string());
                }
            }
        });
    }
    private void jiexidata(String data){
        Gson gson = new Gson();
        UserResult userResult = gson.fromJson(data,UserResult.class);
        if (null == userResult.getList().getUserMoney()||userResult.getList().getUserMoney().equals("")){
            money = 0.0;
        }else {
            money = Double.parseDouble(userResult.getList().getUserMoney());
        }

    }
    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(BuyTicketActivity.this);
        normalDialog.setTitle("购买");
        normalDialog.setMessage("你确定购买票么?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       pay();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
    private void pay(){
        OkHttpUtil.doPost("http://192.168.43.240:8080/order/buysellticket", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                ToastUtil.showToast(BuyTicketActivity.this,"购买失败，请重试");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    Gson gson = new Gson();
                    Result userResult = gson.fromJson(response.body().string(),Result.class);
                    if (userResult.getStatus().equals("success")){
                        ToastUtil.showToast(BuyTicketActivity.this,"购买成功");
                        finish();
                    }
                    finish();
                }
            }
        });
    }
}
