package com.example.ygh.app.pro.mine.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.OrderInfo;
import com.example.ygh.app.bean.OrderResult;
import com.example.ygh.app.bean.Result;
import com.example.ygh.app.pro.mine.view.adapter.MyOrderAdapter;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyOrderInfoActivity extends AppCompatActivity {
    private ImageView iv_back;
    private XRecyclerView rl_order;
    private LinearLayoutManager manager;
    private List<OrderInfo> orderInfos;
    private MyOrderAdapter myOrderAdapter;
    private String userid="";
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    rl_order.setAdapter(new MyOrderAdapter(MyOrderInfoActivity.this,orderInfos));
                    rl_order.refreshComplete();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorderinfo);
        init();
    }
    private void init(){
        iv_back =findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_order = findViewById(R.id.rl_order);
        manager = new LinearLayoutManager(MyOrderInfoActivity.this);
        rl_order.setLayoutManager(manager);
        rl_order.setRefreshProgressStyle(ProgressStyle.SysProgress);
        rl_order.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        rl_order.setArrowImageView(R.drawable.iconfont_downgrey);
        rl_order.setLoadingMoreEnabled(false);

        //加载刷新组件
        rl_order.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        rl_order.refresh();
    }
    private void initData(){
        //okhttp获取数据--
        userid = SpUtil.getString(MyOrderInfoActivity.this,"userid","");
        //"http://yapi.demo.qunar.com/mock/11910/title?userid="+userid
        OkHttpUtil.doGet("http://192.168.43.240:8080/order/getorder?userId="+userid, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    formatData(response.body().string());
                }
            }
        });
    }
    private void formatData(String result) {
        Gson gson = new Gson();
        OrderResult result1 = gson.fromJson(result,OrderResult.class);
        if (null==result1.getData()||result1.getData().equals("")){
            ToastUtil.showToast(MyOrderInfoActivity.this,"暂无数据");
            return;
        }
        orderInfos =  result1.getData();
        handler.sendEmptyMessage(1);

    }
}
