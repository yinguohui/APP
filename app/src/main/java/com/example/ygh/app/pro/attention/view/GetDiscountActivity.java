package com.example.ygh.app.pro.attention.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

public class GetDiscountActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back;
    private Button choose_use;
    private Map<String,String>map = new HashMap<>();
    private Map<String,String>map1 = new HashMap<>();
    private UserResult.UserInfoBean userInfoBean;
    private Gson gson = new Gson();
    private String fromdata="";
    private Handler getdishandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    fromdata(fromdata);
                    break;
                case 2:
                    fromdisget(fromdata);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_get);
        init();
    }
    private void init(){
        map.put("discountid",getIntent().getStringExtra("discountid"));
        map1.put("discountscore",getIntent().getStringExtra("discountscore"));
        map1.put("discountype",getIntent().getStringExtra("discountype"));
        map.put("userid",SpUtil.getString(GetDiscountActivity.this,"userid",""));
        initData();
        iv_back = findViewById(R.id.iv_back);
        choose_use = findViewById(R.id.choose_use);
        iv_back.setOnClickListener(this);
        choose_use.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.choose_use:
                getdis();
                break;

        }
    }

    private void initData(){
        OkHttpUtil.doGet("http://192.168.43.240:8080/user/userinfo?user_id="+map.get("userid"), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtil.showToast(GetDiscountActivity.this,"错误");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    fromdata = response.body().string();
                    getdishandler.sendEmptyMessage(1);

                }
            }
        });
    }
    private void fromdata(String data){
        UserResult userResult = gson.fromJson(data,UserResult.class);
        if (userResult.getStatus().equals("success")){
            userInfoBean = userResult.getList();
        }

    }

    private void getdis(){
        if (Integer.parseInt(map1.get("discountscore"))-userInfoBean.getUserScore()<0){
            map.put("userscore",userInfoBean.getUserScore()-Integer.parseInt(map1.get("discountscore"))+"");
            showNormalDialog();
        }else {
            ToastUtil.showToast(GetDiscountActivity.this,"积分不足,请确认你的积分再来兑换哟");
        }
    }
    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(GetDiscountActivity.this);
        normalDialog.setTitle("兑换优惠券");
        normalDialog.setMessage("你要用"+map1.get("discountscore")+"兑换"+map1.get("discountype")+"吗?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       getdisdata();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // 显示
        normalDialog.show();
    }
    private void getdisdata(){
        OkHttpUtil.doPost("http://192.168.43.240:8080/discount/getdis",map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtil.showToast(GetDiscountActivity.this,"错误");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    fromdata = response.body().string();
                    getdishandler.sendEmptyMessage(2);
                }else {
                    ToastUtil.showToast(GetDiscountActivity.this,"错误");
                }
            }
        });
    }
    private void fromdisget(String data){
        Result result = gson.fromJson(data,Result.class);
        if (result.getStatus().equals("success")){
            ToastUtil.showToast(GetDiscountActivity.this,"兑换成功");
            finish();
        }else {
            ToastUtil.showToast(GetDiscountActivity.this,"失败");
        }
    }
}
