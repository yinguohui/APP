package com.example.ygh.app.pro.essence.view.navigation;

import android.content.Context;

import com.example.ygh.app.R;
import com.example.ygh.app.pro.base.view.Navigation.NavigationBuilderAdapter;

public class EssenceNavigationBuilder extends NavigationBuilderAdapter {
    public EssenceNavigationBuilder(Context context){
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.toolbar_essence_layout;
    }
}
