package com.example.ygh.app.pro.essence.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.example.ygh.app.R;
import com.example.ygh.app.bean.PostsListBean;
import com.example.ygh.app.mvp.presenter.impl.MvpBasePresenter;
import com.example.ygh.app.pro.base.presenter.BasePresenter;
import com.example.ygh.app.pro.base.view.BaseFragment;
import com.example.ygh.app.pro.essence.presenter.EssenceVideoPresenter;
import com.example.ygh.app.pro.essence.view.adapter.EssenceVideoAdapter;
import com.example.ygh.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class EssenceVideoFragment extends BaseFragment{

    private int mType = 0;
    private String mTitle;

    private EssenceVideoPresenter presenter;

    private RecyclerView recyclerView;
    private XRefreshView xRefreshView;
    private EssenceVideoAdapter videoAdapter;

    //数据列表
    private List<PostsListBean.Info.PostList> postList = new ArrayList<>();

    public void setType(int mType) {
        this.mType = mType;
    }
    public void setTitle(String title){
        this.mTitle = title;
    }

    @Override
    public MvpBasePresenter bindPresenter() {
        presenter = new EssenceVideoPresenter(getContext());
        return presenter;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_essence_video;
    }

    @Override
    public void initContentView(View contentView) {
        xRefreshView = (XRefreshView) contentView.findViewById(R.id.xrefreshview);
        // 设置是否可以下拉刷新
        xRefreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xRefreshView.setPullLoadEnable(true);
        // 设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(1000);
        // 静默加载模式不能设置footerview
        // 设置支持自动刷新
        xRefreshView.setAutoLoadMore(true);
        //设置静默加载时提前加载的item个数
//		xRefreshView.setPreLoadCount(2);

        recyclerView = (RecyclerView) contentView.findViewById(R.id.recycler_view_test_rv);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        videoAdapter = new EssenceVideoAdapter(postList,getContext());
        recyclerView.setAdapter(videoAdapter);
        videoAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getContext()));

        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
                loadData(true);
            }

            @Override
            public void onLoadMore(boolean isSlience) {
                loadData(false);
            }
        });
    }

    @Override
    public void initData() {
        loadData(true);
    }

    private void loadData(final boolean isDownRefresh){
        presenter.getEssenceList(mType, isDownRefresh, new BasePresenter.OnUIThreadListener<List<PostsListBean.Info.PostList>>() {
            @Override
            public void onResult(List<PostsListBean.Info.PostList> result) {
                if (isDownRefresh){
                    xRefreshView.stopRefresh();
                }else{
                    xRefreshView.stopLoadMore();
                }
                if (result == null){
                    ToastUtil.showToast(getContext(),"加载失败");
                }else {
                    ToastUtil.showToast(getContext(),"加载成功");
                    //刷新UI
                    if (isDownRefresh){
                        //如果你是下拉刷新,我就情况列表
                        postList.clear();
                    }
                    postList.addAll(result);
                    videoAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
