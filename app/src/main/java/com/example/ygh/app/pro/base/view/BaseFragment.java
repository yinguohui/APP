package com.example.ygh.app.pro.base.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ygh.app.mvp.presenter.impl.MvpBasePresenter;
import com.example.ygh.app.mvp.view.impl.MvpFragment;


/**
 * Created by Dream on 16/5/26.
 */
public abstract class BaseFragment<P extends MvpBasePresenter> extends MvpFragment<P> {
    //我们自己的Fragment需要缓存视图
    private View viewContent;//缓存视图

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (viewContent == null){
            viewContent = inflater.inflate(getContentView(),container,false);
            initContentView(viewContent);
        }

        //判断Fragment对应的Activity是否存在这个视图
        ViewGroup parent = (ViewGroup) viewContent.getParent();
        if (parent != null){
            //如果存在,那么我就干掉,重写添加,这样的方式我们就可以缓存视图
            parent.removeView(viewContent);
        }
        return viewContent;
    }

    public abstract int getContentView();

    public abstract void initContentView(View viewContent);

    public void initData(){

    }
    @Override
    public P bindPresenter() {
        return null;
    }
}
