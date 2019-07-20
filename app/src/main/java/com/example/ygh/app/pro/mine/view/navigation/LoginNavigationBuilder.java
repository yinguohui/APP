package com.example.ygh.app.pro.mine.view.navigation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.ygh.app.R;
import com.example.ygh.app.pro.base.view.Navigation.NavigationBuilderAdapter;

public class LoginNavigationBuilder extends NavigationBuilderAdapter {

    private View.OnClickListener tilteOnClickListener;

    public LoginNavigationBuilder(Context context){
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.toolbar_login_layout;
    }

    public LoginNavigationBuilder setTilteOnClickListener(View.OnClickListener tilteOnClickListener){
        this.tilteOnClickListener = tilteOnClickListener;
        return this;
    }

    @Override
    public void createAndBind(ViewGroup parent) {
        super.createAndBind(parent);
        setImageViewStyle(R.id.iv_left,getLeftIconRes(),getLeftIconOnClickListener());
        setTitleTextView(R.id.tv_title,getTitle(),tilteOnClickListener);
    }
}
