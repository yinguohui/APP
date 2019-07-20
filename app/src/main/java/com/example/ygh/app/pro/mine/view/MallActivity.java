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
import com.example.ygh.app.bean.PostsListBean;
import com.example.ygh.app.bean.SellInfo;
import com.example.ygh.app.bean.TicketResult;
import com.example.ygh.app.pro.mine.view.adapter.MallAdapter;
import com.example.ygh.app.pro.mine.view.adapter.SellTicketsAdapter;
import com.example.ygh.app.pro.mine.view.adapter.TitleAdapter;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.SpUtil;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MallActivity extends AppCompatActivity {
    private XRecyclerView xw_mall;
    private ImageView iv_back;
    private MallAdapter mallAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<TicketResult.TicketInfo> list = new ArrayList<>();
    private String userid="";
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mallAdapter = new MallAdapter(MallActivity.this,list);
                    xw_mall.setAdapter(mallAdapter);
                    xw_mall.refreshComplete();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall);
        init();
    }
    private void init(){
        xw_mall = findViewById(R.id.xw_mall);
        iv_back = findViewById(R.id.iv_back);
        linearLayoutManager = new LinearLayoutManager(MallActivity.this);
        xw_mall.setLayoutManager(linearLayoutManager);
        xw_mall.setRefreshProgressStyle(ProgressStyle.SysProgress);
        xw_mall.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        xw_mall.setArrowImageView(R.drawable.iconfont_downgrey);
        xw_mall.setLoadingMoreEnabled(false);

        //加载刷新组件
        xw_mall.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        xw_mall.refresh();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initData(){
        OkHttpUtil.doGet("http://192.168.43.240:8080/order/showticket", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    jiexiData(response.body().string());
                }
            }
        });
    }
    private void  jiexiData(String info){
        Gson gson = new Gson();
        TicketResult result = gson.fromJson(info,TicketResult.class);
        if (!result.getStatus().equals("success")){
            return;
        }
        list = result.getData();
        handler.sendEmptyMessage(1);

    }
}
