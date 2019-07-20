package com.example.ygh.app.pro.mine.model;

import android.content.Context;

import com.example.ygh.app.http.impl.RequestParam;
import com.example.ygh.app.http.impl.SystemHttpCommand;
import com.example.ygh.app.http.util.HttpTask;
import com.example.ygh.app.http.util.HttpUtils;
import com.example.ygh.app.pro.base.model.BaseModel;

public class LoginModel extends BaseModel {
    public LoginModel(Context context) {
        super(context);
    }

    public void login(String username, String password, HttpUtils.OnHttpResultListener onHttpResultListener){
        RequestParam requestParam = new RequestParam();
        requestParam.put("username",username);
        requestParam.put("password",password);
        HttpTask httpTask = new HttpTask("http://yapi.demo.qunar.com/mock/64560/app/test/login",requestParam,new SystemHttpCommand(),onHttpResultListener);
        httpTask.execute();
    }
    public void register(String username, String password, HttpUtils.OnHttpResultListener onHttpResultListener){
        RequestParam requestParam = new RequestParam();
        requestParam.put("username",username);
        requestParam.put("password",password);
        HttpTask httpTask = new HttpTask("http://yapi.demo.qunar.com/mock/64560/app/test/login",requestParam,new SystemHttpCommand(),onHttpResultListener);
        httpTask.execute();
    }

}