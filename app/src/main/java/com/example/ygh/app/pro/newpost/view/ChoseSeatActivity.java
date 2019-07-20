package com.example.ygh.app.pro.newpost.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.SeatInfo;
import com.example.ygh.app.pro.mine.view.LoginActivity;
import com.example.ygh.app.pro.newpost.view.views.SeatTable;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChoseSeatActivity extends AppCompatActivity {

    private SeatInfo seatInfo;
    private HashSet<String> selectedseatid = new HashSet<>();
    private HashSet<String> selectedseat = new HashSet<>();
    private String showId="";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    showSeatData();
                    break;
            }
        }
    };

    private TextView tv_title;
    private TextView tv_cinema;
    private TextView tv_info;
    private SeatTable seatTable;
    private TextView seat_buy_info;
    private Button seat_buy;
    private List<SeatInfo.Sections.SeatRows> seatRows;
    private SeatInfo.ShowInfo showInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_seat);

        showId = getIntent().getStringExtra("showId");

        initView();
        initData(showId);
    }

    private void initView() {
        SpUtil.putString(ChoseSeatActivity.this,"discountid","");
        SpUtil.putString(ChoseSeatActivity.this,"discounttype","");
        tv_title = (TextView) findViewById(R.id.tv_title_name);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoseSeatActivity.this.finish();
            }
        });
        tv_cinema = (TextView) findViewById(R.id.tv_cinema);
        tv_info = (TextView) findViewById(R.id.tv_info);
        seatTable = (SeatTable) findViewById(R.id.seatView);
        seat_buy_info = findViewById(R.id.seat_buy_info);
        seat_buy = findViewById(R.id.seat_buy);
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(selectedseat);
        seat_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SpUtil.getBoolean(ChoseSeatActivity.this,"isLogin",false)){
                    startActivity(new Intent(ChoseSeatActivity.this,LoginActivity.class));
                    ToastUtil.showToast(ChoseSeatActivity.this,"请先登录");
                    return;
                }
                if (selectedseat.isEmpty()){
                    ToastUtil.showToast(ChoseSeatActivity.this,"请选择电影票");
                    return;
                }
                Intent intent = new Intent(ChoseSeatActivity.this,BuyTicketsActivity.class);
                intent.putExtra("selectedseatid",selectedseatid.toString());
                intent.putExtra("selectedseat",selectedseat.toString());
                intent.putExtra("show_id", showId);
                //intent.putExtra("showId",showInfo.getShowId());
                //intent.putExtra("cinemaId",showInfo.getCinemaId());
                startActivity(intent);

            }
        });
    }

    private void initData(String showId) {
        OkHttpUtil.doGet("http://192.168.43.240:8080/movie/chooseseat?show_id="+showId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    if (!TextUtils.isEmpty(json)) {
                        Gson gson = new Gson();
                        try {
                            seatInfo = gson.fromJson(json, SeatInfo.class);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(1);
                    }
                }
            }
        });
    }

    private void showSeatData() {
        SeatInfo.ShowInfo showInfo = seatInfo.getShowInfo();
        if (showInfo != null) {
            tv_title.setText(showInfo.getSpaceCinemaname());
            tv_cinema.setText(showInfo.getMovieName());
            tv_info.setText(showInfo.getMhTime().substring(0,10)+"   "+showInfo.getMhStart()+"-----"+showInfo.getMhEnd());
            seatTable.setScreenName(showInfo.getHallName());
            seatTable.setMaxSelected(5);
        }
        final SeatInfo.Sections sections = seatInfo.getSections();
        seatRows = sections.getSeatRows();
        if (sections!=null) {
            seatTable.setSeatChecker(new SeatTable.SeatChecker() {

                @Override
                public boolean isValidSeat(int row, int column) {
                    return true;
                }
                //判断是否预定了
                @Override
                public boolean isSold(int row, int column) {
                    if (seatRows.get(row).getSeats().get(column).getSeatStatus()!=0) {
                        return true;
                    } else {
                        return false;
                    }
                }
                //选中时操作
                @Override
                public void checked(int row, int column) {
                    selectedseatid.add(sections.getSeatRows().get(row).getSeats().get(column).getSeatId());
                    String xz = (row+1)+","+(column+1);
                    selectedseat.add(xz);
                    String s ="";
                    for (String s1:selectedseat) {
                        s1 = s1.substring(0,1)+"排"+s1.substring(2)+"座";
                        s = s + s1;
                    }
                    seat_buy_info.setText(s);

                }
                //取消时操作
                @Override
                public void unCheck(int row, int column) {
                    selectedseatid.remove(sections.getSeatRows().get(row).getSeats().get(column).getSeatId());
                    selectedseat.remove((row+1)+","+(column+1));
                    String s ="";
                    for (String s1:selectedseat) {
                        s1 = s1.substring(0,1)+"排"+s1.substring(2)+"座";
                        s+=s1;
                    }
                    seat_buy_info.setText(s);
                }

                @Override
                public String[] checkedSeatTxt(int row, int column) {
                    return null;
                }

            });
            seatTable.setData(sections.getSectioninfo().getSectionRow(), sections.getSectioninfo().getSectionCol());

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (SpUtil.getString(ChoseSeatActivity.this,"buy","").equals("ok")){
            SpUtil.putString(ChoseSeatActivity.this,"buy","");
            finish();

        }
    }
}
