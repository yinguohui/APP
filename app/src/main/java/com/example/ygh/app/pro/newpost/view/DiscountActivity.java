package com.example.ygh.app.pro.newpost.view;

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
import android.widget.ImageView;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.DiscountResult;
import com.example.ygh.app.bean.MyDiscountResult;
import com.example.ygh.app.pro.mine.view.adapter.DiscountAdapter;
import com.example.ygh.app.pro.newpost.view.adapter.DiscountUseAdapter;
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

public class DiscountActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_back;
    Button choose_sure;
    XRecyclerView xRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<MyDiscountResult.MyDiscountBean> discountBeans;
    private MyDiscountResult result;
    private Map<String,String> map = new HashMap<>();
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    xRecyclerView.setAdapter(new DiscountUseAdapter(DiscountActivity.this,discountBeans));
                    xRecyclerView.refreshComplete();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_use);
        init();
    }
    private void init(){
        map.put("user_id",SpUtil.getString(DiscountActivity.this,"userid",""));
        iv_back = findViewById(R.id.iv_back);
        choose_sure = findViewById(R.id.choose_sure);
        xRecyclerView = findViewById(R.id.rl_discount_use);

        linearLayoutManager = new LinearLayoutManager(DiscountActivity.this);
        xRecyclerView.setLayoutManager(linearLayoutManager);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.SysProgress);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        xRecyclerView.setLoadingMoreEnabled(false);
        //加载刷新组件
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            //刷新
            @Override
            public void onRefresh() {
            }
            //加载更多
            @Override
            public void onLoadMore() {
            }
        });
        initData();
        iv_back.setOnClickListener(this);
        choose_sure.setOnClickListener(this);
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.choose_sure:
                showNormalDialog();
                break;
        }
    }

//    private OnCheckOnClickListener onCheckOnClickListener;
//    public interface OnCheckOnClickListener {void onCheckClick(PortIdGetOrder item);}
//    public void setOnCheckClickListener(OnCheckOnClickListener onCheckOnClickListener)
//    {this.onCheckOnClickListener = onCheckOnClickListener;}
    public void initData(){
        OkHttpUtil.doGet("http://192.168.43.240:8080/discount/getmydiscount?user_id="+map.get("user_id")+"&discount_status=0&ds_type=0", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
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
                ToastUtil.showToast(DiscountActivity.this,result.getMessage());
                return;
            }
            handler.sendEmptyMessage(1);
            discountBeans = result.getList();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(DiscountActivity.this);
        normalDialog.setIcon(R.drawable.jd1);
        normalDialog.setTitle("优惠券");
        normalDialog.setMessage("你要确定选择"+SpUtil.getString(DiscountActivity.this,"discountid",""));
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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

