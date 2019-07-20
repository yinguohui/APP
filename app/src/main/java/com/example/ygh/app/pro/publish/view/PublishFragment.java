package com.example.ygh.app.pro.publish.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.ygh.app.R;
import com.example.ygh.app.pro.base.view.BaseFragment;
import com.example.ygh.app.pro.mine.view.LoginActivity;
import com.example.ygh.app.pro.newpost.view.ChoseSeatActivity;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.ToastUtil;

public class PublishFragment extends BaseFragment {
    @Override
    public int getContentView() {
        return R.layout.fragment_publish;
    }

    @Override
    public void initContentView(final View viewContent) {
        Button button = viewContent.findViewById(R.id.fragment_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SpUtil.getBoolean(getContext(),"isLogin",false)){
                    startActivity(new Intent(getContext(),LoginActivity.class));
                    ToastUtil.showToast(getContext(),"请先登录");
                    return;
                }
                if (SpUtil.getString(getContext(),"status","0").equals("0")){
                    ToastUtil.showToast(getContext(),"你已被禁言,解除请联系客服");
                    return;
                }
                startActivity(new Intent(viewContent.getContext(),PublishedActivity.class));
            }
        });
    }
}
