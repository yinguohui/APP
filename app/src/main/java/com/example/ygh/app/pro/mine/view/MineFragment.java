package com.example.ygh.app.pro.mine.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ygh.app.MainActivity;
import com.example.ygh.app.R;
import com.example.ygh.app.bean.UserResult;
import com.example.ygh.app.pro.base.view.BaseFragment;
import com.example.ygh.app.pro.base.view.item.DefaultItemBuilder;
import com.example.ygh.app.pro.essence.view.views.CircleNetworkImageImage;
import com.example.ygh.app.pro.mine.view.navigation.MineNavigationBuilder;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.ToastUtil;
import com.example.ygh.app.util.VolleyUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MineFragment extends BaseFragment implements View.OnClickListener{
    private CircleNetworkImageImage user_icon;
    private Intent intent;
    private TextView tv_user;
    private RelativeLayout rl_news;
    private RelativeLayout rl_save;
    private RelativeLayout rl_achieve;
    private RelativeLayout rl_wallet;
    private RelativeLayout rl_coupon;
    private RelativeLayout rl_setting;
    private RelativeLayout rl_about;
    private RelativeLayout rl_user;
    private RelativeLayout rl_mall;
    private RelativeLayout rl_order;
    private RelativeLayout rl_mymovies;
    private RelativeLayout rl_about_us;
    private UserResult userResult;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    SpUtil.putString(getContext(),"status",userResult.getList().getUserStatus()+"");
                    VolleyUtils.loadImage(getContext(),user_icon,userResult.getList().getUserImg());
                    tv_user.setText(userResult.getList().getUserName());
                    break;
                case 2:
                    loadindfale();
                    break;
            }
        }
    };
    @Override
    public int getContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initContentView(View viewContent) {
        initView(viewContent);

    }

    public void initView(View viewContent){
        SpUtil.putString(getContext(),"seatid","");
        //得到控件
        user_icon =  viewContent.findViewById(R.id.iv_user);
        tv_user = viewContent.findViewById(R.id.tv_user);
        rl_news =  viewContent.findViewById(R.id.rl_news);
        rl_save =  viewContent.findViewById(R.id.rl_save);
        rl_achieve =  viewContent.findViewById(R.id.rl_achieve);
        rl_wallet =  viewContent.findViewById(R.id.rl_wallet);
        rl_coupon =  viewContent.findViewById(R.id.rl_coupon);
        rl_setting =  viewContent.findViewById(R.id.rl_setting);
        rl_about =  viewContent.findViewById(R.id.rl_about);
        rl_user = viewContent.findViewById(R.id.rl_user);
        rl_mall = viewContent.findViewById(R.id.rl_mall);
        rl_order = viewContent.findViewById(R.id.rl_order);
        rl_mymovies = viewContent.findViewById(R.id.rl_mymovies);
        rl_about_us= viewContent.findViewById(R.id.rl_about_us);
        //设置控件事件

        rl_news.setOnClickListener(this);
        rl_save.setOnClickListener(this);
        rl_achieve.setOnClickListener(this);
        rl_wallet.setOnClickListener(this);
        rl_coupon.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        rl_user.setOnClickListener(this);
        rl_mall.setOnClickListener(this);
        rl_order.setOnClickListener(this);
        rl_mymovies.setOnClickListener(this);
        rl_about_us.setOnClickListener(this);
        user_icon.setImageResource(R.drawable.icon_user_boy);

    }

    @Override
    public void onClick(View v) {
        if (!SpUtil.getBoolean(getContext(),"isLogin",false)){
            startActivity(new Intent(getContext(),LoginActivity.class));
            ToastUtil.showToast(getActivity(),"请先登录");
            return;
        }
        switch (v.getId()){
            case R.id.rl_news:
                startActivity(new Intent(getActivity(), TitleActivity.class));
                break;
            case R.id.rl_order:
                startActivity(new Intent(getActivity(), MyOrderInfoActivity.class));
                break;
            case R.id.rl_mymovies:
                startActivity(new Intent(getActivity(), MyMovieTicketActivity.class));
                break;
            case R.id.rl_save:
                startActivity(new Intent(getActivity(), ScoreActivity.class));
                break;
            case R.id.rl_achieve:
                startActivity(new Intent(getActivity(), SellTicketsActivity.class));
                break;
            case R.id.rl_mall:
                startActivity(new Intent(getActivity(), MallActivity.class));
                break;
            case R.id.rl_wallet:
                startActivity(new Intent(getContext(),WalletActivity.class));
                break;
            case R.id.rl_coupon:
                startActivity(new Intent(getActivity(), DiscountTypeActivty.class));
                break;
            case R.id.rl_about_us:
                startActivity(new Intent(getActivity(),AboutUsActivity.class));
                break;
            case R.id.rl_setting:
                startActivity(new Intent(getActivity(),SettingActivity.class));
                break;
            case R.id.rl_about:
                SpUtil.putBoolean(getActivity(),"isLogin",false);
                startActivity(new Intent(getActivity(),LoginActivity.class));
                ToastUtil.showToast(getActivity(),"点击了退出");
                break;
        }
    }

    @Override
    public void onResume() {
        if (!SpUtil.getBoolean(getContext(),"isLogin",false)){
            tv_user.setText("请登录登录。。。");
            tv_user.setOnClickListener(this);
        }else {
            String id  = SpUtil.getString(getContext(),"userid","");
            loadind(id);
            tv_user.setClickable(false);
        }
        super.onResume();
    }
    private void loadind(String id){
        OkHttpUtil.doGet("http://192.168.43.240:8080/user/userinfo?user_id="+id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    fromdata(response.body().string());
                }
            }
        });
    }

    private void  loadindfale(){
        SpUtil.putBoolean(getContext(),"isLogin",false);
        ToastUtil.showToast(getContext(),"信息加载失败，退出重新登陆试试");
        tv_user.setText("请登录登录。。。");
        tv_user.setClickable(true);
        tv_user.setOnClickListener(this);

    }
    private void fromdata(String data){
        Gson gson = new Gson();
        userResult = gson.fromJson(data,UserResult.class);
        if (userResult.getStatus().equals("success")){
            handler.sendEmptyMessage(1);
        }

    }
}
