package com.example.ygh.app.pro.base.presenter;

import android.content.Context;

import com.example.ygh.app.mvp.presenter.impl.MvpBasePresenter;
import com.example.ygh.app.pro.base.model.BaseModel;
import com.google.gson.Gson;

public abstract class BasePresenter<M extends BaseModel> extends MvpBasePresenter {

    private Context context;
    private Gson gson;
    private M model;

    public BasePresenter(Context context){
        this.context = context;
        this.gson = new Gson();
        this.model = bindModel();
    }

    public Context getContext() {
        return context;
    }

    public Gson getGson() {
        return gson;
    }

    public M getModel() {
        return model;
    }

    public abstract M bindModel();

    public interface OnUIThreadListener<T>{
        public void onResult(T result);
    }
}
