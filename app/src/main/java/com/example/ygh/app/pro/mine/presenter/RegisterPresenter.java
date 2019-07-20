package com.example.ygh.app.pro.mine.presenter;

import android.content.Context;

import com.example.ygh.app.bean.UserBean;
import com.example.ygh.app.http.util.HttpUtils;
import com.example.ygh.app.pro.base.presenter.BasePresenter;
import com.example.ygh.app.pro.mine.model.LoginModel;
import com.example.ygh.app.pro.mine.view.RegisterActivity;
import com.example.ygh.app.util.ToastUtil;

public class RegisterPresenter extends BasePresenter<LoginModel> {

    public RegisterPresenter(Context context) {
        super(context);
    }

    @Override
    public LoginModel bindModel() {
        return new LoginModel(getContext());
    }

    public void register(String username, String password, String configpassword, final OnUIThreadListener<UserBean> onUIThreadListener){
        if (username.isEmpty()||password.isEmpty()){
            ToastUtil.showToast(getContext(),"用户名或密码不能为空");
        }
        else if (!password.equals(configpassword)){
            ToastUtil.showToast(getContext(),"两次密码不一致");
        }
        else {
            getModel().register(username, password, new HttpUtils.OnHttpResultListener() {
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
}
