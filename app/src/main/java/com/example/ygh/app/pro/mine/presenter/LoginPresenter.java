package com.example.ygh.app.pro.mine.presenter;

import android.content.Context;

import com.example.ygh.app.bean.UserBean;
import com.example.ygh.app.http.util.HttpUtils;
import com.example.ygh.app.pro.base.presenter.BasePresenter;
import com.example.ygh.app.pro.mine.model.LoginModel;

public class LoginPresenter extends BasePresenter<LoginModel> {

    public LoginPresenter(Context context) {
        super(context);
    }

    @Override
    public LoginModel bindModel() {
        return new LoginModel(getContext());
    }

    public void login(String username, String password, final OnUIThreadListener<UserBean> onUIThreadListener){
        getModel().login(username, password, new HttpUtils.OnHttpResultListener() {
            @Override
            public void onResult(String result) {
                //解析model
                if (result == null){
                    onUIThreadListener.onResult(null);
                }else {
                    UserBean userBean = getGson().fromJson(result,UserBean.class);
                    onUIThreadListener.onResult(userBean);
                }
            }
        });
    }

}
