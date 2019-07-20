package com.example.ygh.app.pro.attention.view;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.DiscountResult;
import com.example.ygh.app.bean.EveryDayInfo;
import com.example.ygh.app.bean.MovieResult;
import com.example.ygh.app.pro.attention.view.adapter.DiscountAllAdapter;
import com.example.ygh.app.pro.attention.view.adapter.RecommendAdapter;
import com.example.ygh.app.pro.attention.view.views.BannerView;
import com.example.ygh.app.pro.base.view.BaseFragment;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.PicassoUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FunctionsFragment extends BaseFragment {
    private Gson gson = new Gson();
    private int height;
    //推荐电影
    private RecyclerView rl_movie;
    //轮播电影图片---广告
    int[] imageList = new int[]{R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4, R.drawable.banner5, R.drawable.banner6};
    //轮播
    private RelativeLayout rl_banner;

    private BannerView bannerView;

    private RecyclerView rl_news;
    //底线
    private LinearLayout ll_nomore;
    //
    private ViewFlipper viewFlipper;

    private TextView tv_hotMovie;
    //电影数据
    private List<MovieResult.Movie> movies;
    private List<DiscountResult.DiscountBean> discountBeans;
    private RecommendAdapter recommendAdapter;
    private DiscountAllAdapter discountAdapter;
    private EveryDayInfo everyDayInfo;

    private AlertDialog todayDialog;
    private RotateAnimation rotateAnimation;
    private ImageView iv_play;
    private ImageView iv_sing;
    private MediaPlayer mediaPlayer;
    private Handler moviehandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    recommendAdapter = new RecommendAdapter(getContext(),movies);
                    rl_movie.setAdapter(recommendAdapter);
                    break;
                case 2:
                    discountAdapter = new DiscountAllAdapter(getContext(),discountBeans);
                    rl_news.setAdapter(discountAdapter);
                    ll_nomore.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    showEveryDayInfo();
                    break;
            }
        }
    };
    @Override
    public int getContentView() {
        return R.layout.fragment_functions;
    }
    @Override
    public void initContentView(View view) {
        //轮播
        RelativeLayout rl_banner =  view.findViewById(R.id.rl_banner);
        bannerView = new BannerView(getActivity(), imageList);
        rl_banner.addView(bannerView.getBannerView());
        bannerView.start();

        //随即推荐
        rl_movie =  view.findViewById(R.id.rl_movie);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rl_movie.setLayoutManager(linearLayoutManager);

        //点击刷新
        tv_hotMovie = view.findViewById(R.id.tv_hotMovie);
        tv_hotMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        //兑换券
        rl_news = view.findViewById(R.id.rl_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rl_news.setLayoutManager(layoutManager);
        //底线
        ll_nomore = view.findViewById(R.id.ll_nomore);
        //广告
        viewFlipper = view.findViewById(R.id.vf_view);
        initData();
        initNews();
        initAd();
    }
    public void initData(){
        OkHttpUtil.doGet("http://192.168.43.240:8080/movie/randSelectMoviefromMovie", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    analyzeData(response.body().string());
                    moviehandler.sendEmptyMessage(1);
                }
            }
        });
    }
    private void analyzeData(String data){
        MovieResult re = gson.fromJson(data,MovieResult.class);
        if (re.getData().size()>0){
            movies = re.getData();
        }
    }
    private void initNews(){
        OkHttpUtil.doGet("http://192.168.43.240:8080/discount/selectAllDis", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    analyzeNewsData(response.body().string());
                }
            }
        });
    }
    private void analyzeNewsData(String newsdata){
        DiscountResult result = gson.fromJson(newsdata,DiscountResult.class);
        if (result.getList().size()>0){
            discountBeans = result.getList();
            moviehandler.sendEmptyMessage(2);

        }
        getEveryDayEnglish();
    }
    private void initAd() {
        View viewAd1 = View.inflate(getActivity(), R.layout.view_flipper, null);
        ImageView imageView1 = (ImageView) viewAd1.findViewById(R.id.iv_icon);
        TextView textView1 = (TextView) viewAd1.findViewById(R.id.tv_message);
        imageView1.setImageResource(R.drawable.icon_newad);
        textView1.setText("\"变形金刚\"系列外传电影\"大黄蜂\"正式杀青");
        viewFlipper.addView(viewAd1);
        View viewAd2 = View.inflate(getActivity(), R.layout.view_flipper, null);
        ImageView imageView2 = (ImageView) viewAd2.findViewById(R.id.iv_icon);
        TextView textView2 = (TextView) viewAd2.findViewById(R.id.tv_message);
        imageView2.setImageResource(R.drawable.icon_head);
        textView2.setText("《羞羞的铁拳》破20亿 田雨爆笑演绎“千人一面”");
        viewFlipper.addView(viewAd2);
    }

    private void getEveryDayEnglish() {
        OkHttpUtil.doGet("http://open.iciba.com/dsapi/", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    if (!TextUtils.isEmpty(result)) {
                        Gson gson = new Gson();
                        everyDayInfo = gson.fromJson(result, EveryDayInfo.class);
                        moviehandler.sendEmptyMessage(3);
                    }
                }
            }
        });
    }
    private void showEveryDayInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.custom_dialog);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.everyday_dialog, null);

        ImageView image = (ImageView) dialogView.findViewById(R.id.iv_image);
        ImageView iv_close = (ImageView) dialogView.findViewById(R.id.iv_close);
        TextView tv_chinese = (TextView) dialogView.findViewById(R.id.tv_chinese);
        TextView tv_english = (TextView) dialogView.findViewById(R.id.tv_english);
        PicassoUtil.load(getActivity(), everyDayInfo.getPicture2(), image);
        tv_chinese.setText("      " + everyDayInfo.getNote());
        tv_english.setText("      " + everyDayInfo.getContent());

        iv_play = (ImageView) dialogView.findViewById(R.id.iv_play);
        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVoice();
            }
        });
        iv_sing = (ImageView) dialogView.findViewById(R.id.iv_sing);
        iv_sing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVoice();
            }
        });
        rotateAnimation = new RotateAnimation(0, 3600, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(12000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setRepeatCount(-1);

        builder.setView(dialogView);
        todayDialog = builder.create();
        todayDialog.setCancelable(false);
        todayDialog.show();
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todayDialog.dismiss();
            }
        });
    }
    //播放语音
    public void playVoice() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            iv_play.setVisibility(View.VISIBLE);
            iv_sing.clearAnimation();
            iv_sing.setVisibility(View.GONE);
        } else {
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(getContext(), Uri.parse(everyDayInfo.getTts()));
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        iv_play.setVisibility(View.GONE);
                        iv_sing.setVisibility(View.VISIBLE);
                        iv_sing.startAnimation(rotateAnimation);
                        mediaPlayer.start();
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        iv_play.setVisibility(View.VISIBLE);
                        iv_sing.clearAnimation();
                        iv_sing.setVisibility(View.GONE);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
