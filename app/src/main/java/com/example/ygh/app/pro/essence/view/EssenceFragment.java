package com.example.ygh.app.pro.essence.view;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.PostsListBean;
import com.example.ygh.app.pro.base.view.BaseFragment;
import com.example.ygh.app.pro.essence.view.adapter.EssenceAdapter;
import com.example.ygh.app.pro.essence.view.adapter.EssenceVideoAdapter;
import com.example.ygh.app.pro.essence.view.adapter.ShouyeAdapter;
import com.example.ygh.app.pro.essence.view.navigation.EssenceNavigationBuilder;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.ToastUtil;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EssenceFragment extends BaseFragment {
    private XRecyclerView xw_shouye;
    private LinearLayoutManager manager;
    private ShouyeAdapter shouyeAdapter;
    //数据列表
    private List<PostsListBean.Info.PostList> postList =new ArrayList<>();
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    shouyeAdapter = new ShouyeAdapter(postList,getActivity());
                    xw_shouye.setAdapter(shouyeAdapter);
                    xw_shouye.refreshComplete();
                    break;
            }
        }
    };
    //得到视图
    @Override
    public int getContentView() {
        return R.layout.fragment_essence;
    }

    //初始化视图
    @Override
    public void initContentView(View viewContent) {
        initToolBar(viewContent);
        xw_shouye = viewContent.findViewById(R.id.xw_shouye);
        manager = new LinearLayoutManager(getContext());
        xw_shouye.setLayoutManager(manager);
        //允许刷新，加载更多
        xw_shouye.setPullRefreshEnabled(true);
        xw_shouye.setLoadingMoreEnabled(true);
        xw_shouye.setRefreshProgressStyle(ProgressStyle.SysProgress);
        xw_shouye.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        xw_shouye.setArrowImageView(R.drawable.iconfont_downgrey);

        //加载刷新组件
        xw_shouye.setLoadingListener(new XRecyclerView.LoadingListener() {
            //刷新
            @Override
            public void onRefresh() {
                initData();
            }
            //加载更多
            @Override
            public void onLoadMore() {
                xw_shouye.refreshComplete();
            }
        });
        xw_shouye.refresh();
    }
    //初始化工具条
    private void initToolBar(View viewContent){
        EssenceNavigationBuilder builder = new EssenceNavigationBuilder(viewContent.getContext());
        builder.setTitle(R.string.main_essence_title)
                .setTitleIcon(R.drawable.attention_post_copyright);
        builder.createAndBind((ViewGroup) viewContent);
    }

    //初始化数据
    @Override
    public void initData() {
        //okhttp获取数据--
        OkHttpUtil.doGet("http://192.168.43.240:8080/title/getalltitle", new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                ToastUtil.showToast(getContext(),"加载失败，请检查");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //shouyeAdapter.notifyDataSetChanged();
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
    private void loadmore(){
        //okhttp获取数据--
        //"http://yapi.demo.qunar.com/mock/11910/title?userid="+userid
        OkHttpUtil.doGet("http://192.168.43.240:8080/title/getalltitle", new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    PostsListBean postsListBean = gson.fromJson(response.body().string(),PostsListBean.class);
                    PostsListBean.Info info = postsListBean.getInfo();
                    try {
                        for (PostsListBean.Info.PostList p: info.getPostList()) {
                            postList.add(p);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(1);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
