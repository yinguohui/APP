package com.example.ygh.app.pro.mine.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.PostsListBean;
import com.example.ygh.app.pro.mine.view.adapter.TitleAdapter;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.SpUtil;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/*
* 用户发布的话题
* */
public class TitleActivity extends AppCompatActivity {
    private XRecyclerView xw_title;
    private LinearLayoutManager manager;
    private PostsListBean postsListBean;
    private List<PostsListBean.Info.PostList> postList;
    private String userid="";


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    xw_title.setAdapter(new TitleAdapter(postList,TitleActivity.this));
                    xw_title.refreshComplete();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        init();
    }

    private void init() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        xw_title = findViewById(R.id.xw_title);
        manager = new LinearLayoutManager(TitleActivity.this);
        xw_title.setLayoutManager(manager);
        xw_title.setRefreshProgressStyle(ProgressStyle.SysProgress);
        xw_title.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        xw_title.setArrowImageView(R.drawable.iconfont_downgrey);
        xw_title.setLoadingMoreEnabled(false);

        //加载刷新组件
        xw_title.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        xw_title.refresh();
    }
    private void initData(){
        //okhttp获取数据--
        userid = SpUtil.getString(TitleActivity.this,"userid","");
        //"http://yapi.demo.qunar.com/mock/11910/title?userid="+userid
        OkHttpUtil.doGet("http://192.168.43.240:8080/title/mytitle?id="+userid, new Callback() {
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
            PostsListBean postsListBean = gson.fromJson(result,PostsListBean.class);
            PostsListBean.Info info = postsListBean.getInfo();
            postList = info.getPostList();
            handler.sendEmptyMessage(1);

    }


}
