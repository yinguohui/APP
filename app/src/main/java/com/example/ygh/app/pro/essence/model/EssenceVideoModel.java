package com.example.ygh.app.pro.essence.model;

import android.content.Context;
import android.text.TextUtils;

import com.example.ygh.app.http.impl.RequestParam;
import com.example.ygh.app.http.impl.SystemHttpCommand;
import com.example.ygh.app.http.util.HttpTask;
import com.example.ygh.app.http.util.HttpUtils;
import com.example.ygh.app.pro.base.model.BaseModel;

import java.util.Date;

/**
 * M层:数据层
 *
 * 请求网络数据
 * 加载本地数据库缓存数据
 * 加载SD卡数据等等......
 *
 * Created by Dream on 16/5/28.
 */
public class EssenceVideoModel extends BaseModel {


    public EssenceVideoModel(Context context){
        super(context);
    }

    private String getUrl(){
        return getServerUrl();
    }

    //定义访问精华接口
    //第一步:定义URL
    //第二步:定义接口

    /**
     * 获取精华列表
     * @param type---数据类型(例如:图片  视频  音频等等)
     * @param page---页码
     * @param maxtime--用户加载更多
     * @param onHttpResultListener---数据回调监听
     */
    public void getEssenceList(int type, int page, String maxtime,HttpUtils.OnHttpResultListener onHttpResultListener){
        RequestParam requestParam = new RequestParam();
//        requestParam.put("seat_buy","list");
//        requestParam.put("c","data");
//        requestParam.put("type",1);
        if (!TextUtils.isEmpty(maxtime)){
//            requestParam.put("maxtime",maxtime);
        }
        //发送请求
        HttpTask httpTask = new HttpTask(
                getUrl(),
                requestParam,
                new SystemHttpCommand(),
                onHttpResultListener);
        httpTask.execute();
    }
}
