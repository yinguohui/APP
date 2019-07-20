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
import com.example.ygh.app.bean.DiscountResult;
import com.example.ygh.app.bean.MyDiscountResult;
import com.example.ygh.app.pro.mine.view.adapter.DiscountAdapter;
import com.example.ygh.app.pro.newpost.view.DiscountActivity;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.ToastUtil;
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

public class DiscountAcyivity extends AppCompatActivity {
    private XRecyclerView rl_discount;
    private ImageView iv_back;
    private LinearLayoutManager linearLayoutManager;
    private MyDiscountResult result;
    private List<MyDiscountResult.MyDiscountBean> discountBeans;
    private DiscountAdapter discountAdapter;
    private Map<String,String> map = new HashMap<>();

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    rl_discount.setAdapter(new DiscountAdapter(DiscountAcyivity.this,discountBeans));
                    rl_discount.refreshComplete();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        init();
    }
    private void init(){
        map.put("user_id",SpUtil.getString(DiscountAcyivity.this,"userid",""));
        rl_discount = findViewById(R.id.rl_discount);
        iv_back = findViewById(R.id.iv_back);
        linearLayoutManager = new LinearLayoutManager(DiscountAcyivity.this);
        rl_discount.setLayoutManager(linearLayoutManager);
        rl_discount.setRefreshProgressStyle(ProgressStyle.SysProgress);
        rl_discount.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        rl_discount.setArrowImageView(R.drawable.iconfont_downgrey);
        rl_discount.setLoadingMoreEnabled(false);
        //加载刷新组件
        rl_discount.setLoadingListener(new XRecyclerView.LoadingListener() {
            //刷新
            @Override
            public void onRefresh() {
            }
            //加载更多
            @Override
            public void onLoadMore() {
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
    }
    private void initData(){
        String discount_status =getIntent().getStringExtra("discount_status");
        String ds_type=getIntent().getStringExtra("ds_type");
        String url = "http://192.168.43.240:8080/discount/getmydiscount?user_id="+map.get("user_id");
        if (null!=discount_status){
            url = url+"&discount_status="+discount_status;
        }
        if(null!=ds_type){
            url = url+"&ds_type="+ds_type;
        }

        OkHttpUtil.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    analyzeData(response.body().string());
                }
            }
        });
    }

    private void analyzeData(String data){
        try{
            Gson gson = new Gson();
            result = gson.fromJson(data,MyDiscountResult.class);
            if (null==result.getList()){
                ToastUtil.showToast(DiscountAcyivity.this,result.getMessage());
                return;
            }
            discountBeans = result.getList();
            handler.sendEmptyMessage(1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
