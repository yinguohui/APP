package com.example.ygh.app.pro.mine.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.SeatInfo;
import com.example.ygh.app.bean.SellInfo;
import com.example.ygh.app.bean.TicketResult;
import com.example.ygh.app.pro.mine.view.adapter.SellTicketsAdapter;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.ToastUtil;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SellTicketsActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_price;
    private XRecyclerView rl_selltickets;
    private LinearLayoutManager linearLayoutManager;
    private SellInfo sellInfo;
    private SellTicketsAdapter sellTicketsAdapter;
    private Button sell_tickets_second;
    private ImageView iv_back;
    private List<TicketResult.TicketInfo> ticketInfo;
    private Map<String,String> map =new HashMap<>();
    String info;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    sellTicketsAdapter = new SellTicketsAdapter(SellTicketsActivity.this,ticketInfo);
                    rl_selltickets.setAdapter(sellTicketsAdapter);
                    rl_selltickets.refreshComplete();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selltickets);
        init();
    }

    private void init() {
        map.put("userId",SpUtil.getString(SellTicketsActivity.this,"userid",""));
        et_price = findViewById(R.id.et_price);
        rl_selltickets = findViewById(R.id.rl_selltickets);
        sell_tickets_second = findViewById(R.id.sell_tickets_second);
        iv_back = findViewById(R.id.iv_back);
        linearLayoutManager = new LinearLayoutManager(SellTicketsActivity.this);
        iv_back.setOnClickListener(this);
        rl_selltickets.setLayoutManager(linearLayoutManager);
        rl_selltickets.setRefreshProgressStyle(ProgressStyle.SysProgress);
        rl_selltickets.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        rl_selltickets.setArrowImageView(R.drawable.iconfont_downgrey);
        rl_selltickets.setLoadingMoreEnabled(false);
        //加载刷新组件
        rl_selltickets.setLoadingListener(new XRecyclerView.LoadingListener() {
            //刷新
            @Override
            public void onRefresh() {
                initData();
            }
            //加载更多
            @Override
            public void onLoadMore() {
            }
        });
        sell_tickets_second.setOnClickListener(this);
        initData();
    }
    private void initData(){
        OkHttpUtil.doGet("http://192.168.43.240:8080/order/getmovie?userId="+map.get("userId"), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    info = response.body().string();
                    jiexiData();
                }
            }
        });
    }
    private void  jiexiData(){
        Gson gson = new Gson();
        try {
            TicketResult result = gson.fromJson(info,TicketResult.class);
            if (!result.getStatus().equals("success")){
                return;
            }
            ticketInfo = result.getData();
            handler.sendEmptyMessage(1);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sell_tickets_second:
                map.put("seatid",SpUtil.getString(SellTicketsActivity.this,"seatid",""));
                showNormalDialog();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
    private void sell(){
        String price = et_price.getText().toString();
        if (price.equals("")||map.get("seatid").equals("")){
            ToastUtil.showToast(SellTicketsActivity.this,"金额和电影票不能为空");
            return;
        }
        OkHttpUtil.doGet("http://192.168.43.240:8080/order/sellticket?seatid="+map.get("seatid")+"&price="+price, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                ToastUtil.showToast(SellTicketsActivity.this,"失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    initData();
                    ToastUtil.showToast(SellTicketsActivity.this,"成功");
                }
            }
        });
    }
    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(SellTicketsActivity.this);
        normalDialog.setTitle("出售二手票");
        normalDialog.setMessage("你要出售这张票么?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       sell();
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

}
