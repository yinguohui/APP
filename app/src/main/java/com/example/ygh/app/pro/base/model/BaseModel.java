package com.example.ygh.app.pro.base.model;

import android.content.Context;

import com.example.ygh.app.mvp.model.MvpModel;

public abstract class BaseModel implements MvpModel {

    private Context context;

    public BaseModel(Context context){
        this.context = context;
    }

    public String getServerUrl(){
        //http://yapi.demo.qunar.com/mock/11910/title
        //return  "http://api.budejie.com/api/api_open.php";
        return  "http://yapi.demo.qunar.com/mock/11910/title";

    }

}