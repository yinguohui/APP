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
import com.example.ygh.app.bean.TicketResult;
import com.example.ygh.app.pro.mine.view.adapter.MallAdapter;
import com.example.ygh.app.pro.mine.view.adapter.MyTicketsAdapter;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.SpUtil;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyMovieTicketActivity extends AppCompatActivity {
    private XRecyclerView xw_mytickets;
    private ImageView iv_back;
    private MyTicketsAdapter mallAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<TicketResult.TicketInfo> list;
    private Map<String,String> map =new HashMap<>();
    private String userid="";
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mallAdapter = new MyTicketsAdapter(MyMovieTicketActivity.this,list);
                    xw_mytickets.setAdapter(mallAdapter);
                    xw_mytickets.refreshComplete();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymovieticket);
        init();
    }
    private void init(){
        map.put("userId",SpUtil.getString(MyMovieTicketActivity.this,"userid",""));
        xw_mytickets = findViewById(R.id.xw_mytickets);
        iv_back = findViewById(R.id.iv_back);
        linearLayoutManager = new LinearLayoutManager(MyMovieTicketActivity.this);
        xw_mytickets.setLayoutManager(linearLayoutManager);
        xw_mytickets.setRefreshProgressStyle(ProgressStyle.SysProgress);
        xw_mytickets.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        xw_mytickets.setArrowImageView(R.drawable.iconfont_downgrey);
        xw_mytickets.setLoadingMoreEnabled(false);

        //加载刷新组件
        xw_mytickets.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        xw_mytickets.refresh();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initData(){
        OkHttpUtil.doGet("http://192.168.43.240:8080/order/getmovie?userId="+map.get("userId"), new Callback() {
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
