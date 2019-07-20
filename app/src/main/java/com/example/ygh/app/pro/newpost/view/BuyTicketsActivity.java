package com.example.ygh.app.pro.newpost.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.BuyInfo;
import com.example.ygh.app.bean.Result;
import com.example.ygh.app.bean.SeatInfo;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BuyTicketsActivity extends AppCompatActivity implements View.OnClickListener {
    private SeatInfo.ShowInfo showInfo;
    ImageView iv_back;
    TextView tv_movies_name;
    TextView tv_movies_time;
    TextView tv_price;
    TextView tv_address;
    TextView tv_seat_info;
    Button buy_sure;
    private Map<String,String> map = new HashMap<>();
    private String show_id="";
    private String selectedseat="";
    private String selectedseatid="";
    private RelativeLayout rl_youhui;
    private BuyInfo buyInfo;
    private double price=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buytickets);

        initView();
    }

    private void initView() {
        show_id = getIntent().getStringExtra("show_id");
        selectedseat = getIntent().getStringExtra("selectedseat");
        selectedseatid = getIntent().getStringExtra("selectedseatid");
        map.put("mh_id",show_id);
        map.put("user_id",SpUtil.getString(BuyTicketsActivity.this,"userid",""));
        iv_back = findViewById(R.id.iv_back);
        tv_movies_name = findViewById(R.id.tv_movies_name);
        tv_movies_time = findViewById(R.id.tv_movies_time);
        tv_address = findViewById(R.id.tv_address);
        tv_seat_info = findViewById(R.id.tv_seat_info);
        buy_sure = findViewById(R.id.buy_sure);
        rl_youhui = findViewById(R.id.rl_youhui);
        tv_price = findViewById(R.id.tv_price);
        buy_sure.setOnClickListener(this);
        rl_youhui.setOnClickListener(this);
        getInfo();
    }

    private void getInfo() {
        OkHttpUtil.doGet("http://192.168.43.240:8080/movie/info_forbuyseat?mh_id="+map.get("mh_id")+"&user_id="+map.get("user_id"), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String json = response.body().string();
                    if (!TextUtils.isEmpty(json)) {
                        Gson gson = new Gson();
                        buyInfo = gson.fromJson(json, BuyInfo.class);
                        if (null==buyInfo.getOrder()){
                            return;
                        }
                        handler.sendEmptyMessage(1);
                    }
                }
            }
        });
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    showBuyDate();
                    break;
                case 2:
                    buysuccess();
                    break;
                case 3:
                    buyfail();
                    break;
            }
        }
    };
    private void showBuyDate(){
        tv_movies_name.setText(buyInfo.getOrder().getMovie_name()+"        "+buyInfo.getOrder().getMovie_description());
        tv_movies_time.setText(buyInfo.getOrder().getMh_time().substring(0,10)+"      "+buyInfo.getOrder().getMh_start()+"至"+buyInfo.getOrder().getMh_end());
        tv_address.setText(buyInfo.getOrder().getSpace_cinemaname()+" ----"+buyInfo.getOrder().getSpace_address());
        String s11="   ";
        selectedseat = selectedseat.substring(1,selectedseat.length()-1);
        String [] colrow = selectedseat.split(",");
        for (int i = 0;i<colrow.length;i=i+2){
            s11 = s11+" "+colrow[i]+"排"+colrow[i+1]+"座";
        }
        tv_seat_info.setText(buyInfo.getOrder().getHall_name()+s11);
        price = Double.parseDouble(buyInfo.getOrder().getMh_price())*(colrow.length/2);
        tv_price.setText("价格   ---    "+price+"元");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buy_sure:
                payticket();
                break;
            case R.id.rl_youhui:
                startActivity(new Intent(BuyTicketsActivity.this,DiscountActivity.class));
                break;
        }
    }

//    private void youhui(){
//        Dialog dialog = new Dialog(this);
//        dialog.show();
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View viewDialog = inflater.inflate(R.layout.item_discount, null);
//        Display display = this.getWindowManager().getDefaultDisplay();
//        int width = display.getWidth();
//        int height = display.getHeight();
//        //设置dialog的宽高为屏幕的宽高
//        ViewGroup.LayoutParams layoutParams = new  ViewGroup.LayoutParams(width, height);
//        dialog.setContentView(viewDialog, layoutParams);
//    }
    private void payticket(){
        String now = buyInfo.getMoney();
        if (Double.parseDouble(now)>=getprice()){
            showNormalDialog();
            //SpUtil.putString(BuyTicketsActivity.this,"money",Integer.parseInt(now)-getprice()+"");
        }else {
            ToastUtil.showToast(BuyTicketsActivity.this,"钱包余额不足，请及时充值");
        }
    }
    private double getprice(){
        if (SpUtil.getString(BuyTicketsActivity.this,"discounttype","").equals("")){
            return price;
        }
        String discount ="";
        String dis= SpUtil.getString(BuyTicketsActivity.this,"discounttype","");
        for (int i = 0;i<dis.length();i++){
            if (dis.charAt(i)>='0'&&dis.charAt(i)<='9'){
                discount = discount + dis.charAt(i);
            }
        }
        if (Double.parseDouble(discount)>=price){
            return 0;
        }else {
            return price-Double.parseDouble(discount);
        }
    }

    private void pay(){
        map.put("seat_id",selectedseatid.substring(1,selectedseatid.length()-1));
        map.put("money",Double.parseDouble(buyInfo.getMoney())-getprice()+"");
        map.put("youhuiquan",SpUtil.getString(BuyTicketsActivity.this,"discountid",""));
        OkHttpUtil.doPost("http://192.168.43.240:8080/movie/info_forbuyorder", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(3);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    Gson gson = new Gson();
                    Result result =gson.fromJson(response.body().string(),Result.class);
                    if (result.getStatus().equals("success")) {
                        handler.sendEmptyMessage(2);
                    }else {
                        handler.sendEmptyMessage(3);
                    }


                }
            }
        });
    }
    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(BuyTicketsActivity.this);
        normalDialog.setTitle("购买");
        normalDialog.setMessage("你确定购买票么?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pay();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
    private void buysuccess(){
        ToastUtil.showToast(BuyTicketsActivity.this,"购买成功");
        SpUtil.putString(BuyTicketsActivity.this,"buy","ok");
        finish();
    }
    private void buyfail(){
        ToastUtil.showToast(BuyTicketsActivity.this,"错误，请退出重试");
    }
}
