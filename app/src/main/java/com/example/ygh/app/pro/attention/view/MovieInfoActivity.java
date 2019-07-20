package com.example.ygh.app.pro.attention.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ygh.app.MainActivity;
import com.example.ygh.app.R;
import com.example.ygh.app.bean.MovieResult;
import com.example.ygh.app.bean.Result;
import com.example.ygh.app.pro.newpost.view.CinemaFragment;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.PicassoUtil;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieInfoActivity extends AppCompatActivity {
    ImageView iv_back,mv_icon;
    TextView mv_name,mv_actor,mv_description,mv_score,mv_jieshao;
    Button ticket_buy;
    String info="";
    Gson gson  = new Gson();
    private Map<String,String> map =new HashMap<>();
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 3:
                    ToastUtil.showToast(MovieInfoActivity.this,"网络错误");
                    break;
                case 2:
                    fromdata(info);
                    break;
                case 4:
                    ToastUtil.showToast(MovieInfoActivity.this,"暂未上映，请耐心等待");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailmovie);
        init();
    }
    private void init(){
        iv_back = findViewById(R.id.iv_back);
        mv_icon = findViewById(R.id.mv_icon);
        mv_name = findViewById(R.id.mv_name);
        mv_actor = findViewById(R.id.mv_actor);
        mv_description = findViewById(R.id.mv_description);
        mv_score = findViewById(R.id.mv_score);
        mv_jieshao = findViewById(R.id.mv_jieshao);
        ticket_buy = findViewById(R.id.ticket_buy);
        String s = SpUtil.getString(MovieInfoActivity.this,"CITY","");
        map.put("space_cityname",s.substring(0,s.length()-1));
        map.put("movie_id",getIntent().getStringExtra("movieId"));
        String c=SpUtil.getString(MovieInfoActivity.this,"CITY","");
        ticket_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcanbuy();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initdata();
    }
    private void initdata(){
        OkHttpUtil.doGet("http://192.168.43.240:8080/movie/getmoviebyid?movieid="+getIntent().getStringExtra("movieId"), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    info = response.body().string();
                    handler.sendEmptyMessage(2);
                }else {
                    handler.sendEmptyMessage(1);
                }
            }
        });
    }

    private void getcanbuy(){
        OkHttpUtil.doPost("http://192.168.43.240:8080/movie/hasbuy", map,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(3);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    Result result = gson.fromJson(response.body().string(),Result.class);
                    if (result.getStatus().equals("success")){
                        Intent intent = new Intent(MovieInfoActivity.this,MainActivity.class);
                        intent.putExtra("userloginflag", 1);
                        startActivity(intent);
                        finish();
                    }else {
                        handler.sendEmptyMessage(4);
                    }
                }else {
                    handler.sendEmptyMessage(3);
                }
            }
        });
    }
    private void fromdata(String data){
        try {
            MovieResult movieResult = gson.fromJson(data,MovieResult.class);
            if (movieResult.getStatus().equals("success")){
                PicassoUtil.loadRound(MovieInfoActivity.this, movieResult.getData().get(0).getMovieImg(), mv_icon, 5);
                mv_name.setText("电影名:"+movieResult.getData().get(0).getMovieName());
                mv_actor.setText("演员:"+movieResult.getData().get(0).getMovieActor());
                mv_description.setText("描述:"+movieResult.getData().get(0).getMovieDescription());
                mv_score.setText("评分:"+movieResult.getData().get(0).getMovieScore());
                mv_jieshao.setText(movieResult.getData().get(0).getMovieUpdate());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


}
