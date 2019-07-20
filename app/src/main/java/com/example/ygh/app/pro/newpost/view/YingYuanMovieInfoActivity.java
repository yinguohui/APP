package com.example.ygh.app.pro.newpost.view;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.CinemaDetailInfo;
import com.example.ygh.app.pro.newpost.view.adapter.HallAdapter;
import com.example.ygh.app.pro.newpost.view.views.AutoLinearLayoutManager;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.PicassoUtil;
import com.example.ygh.app.util.ToastUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//点击电影院时的详细信息
public class YingYuanMovieInfoActivity extends AppCompatActivity {

    private CinemaDetailInfo cinemaDetailInfo;
    private String cinemaId;
    private TextView tv_title, tv_name, tv_address;
    private LinearLayout ll_tag;
    private RelativeLayout rl_viewPager;
    private ViewPager viewPager;
    private List<CinemaDetailInfo.Result.Movies> movies;
    private TextView mv_name, mv_score, mv_var;
    private RadioGroup rg_date;
    private RadioButton rb_1, rb_2, rb_3, rb_4;
    private List<RadioButton> radioButtons;
    private RecyclerView rl_hall;
    private Map<String, List<MovieShow>> dateShows;
    private HallAdapter hallAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    showData();
                    break;
                case 2:
                    for (int i = 0; i < movies.size(); i++) {
                        String s =cinemaDetailInfo.getResult().getCurrentMovie().getId();
                        String s1 = movies.get(i).getMovieId();
                        if (cinemaDetailInfo.getResult().getCurrentMovie().getId().equals( movies.get(i).getMovieId())) {
                            showSelectMovie(i);
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yingyuan_movie_info_layout);

        initView();
        cinemaId = getIntent().getStringExtra("cinemaId");
        initData(cinemaId,"");
    }

    //初始化视图
    private void initView() {
        //返回图标
        ImageView iv_back = findViewById(R.id.iv_back);
        //返回点击事件
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //导航栏影院标题---》CG影院(高新店)
        tv_title = findViewById(R.id.tv_title_name);
        //影院标题---》CG影院(高新店)
        tv_name = findViewById(R.id.tv_name);
        //影院地址---》苏州市姑苏区广济南路19号西城永捷生活广场6楼
        tv_address = findViewById(R.id.tv_address);
        //电影店信息----》3d，wifi
        ll_tag = findViewById(R.id.ll_tag);

        //viewpager 放置电影图片
        rl_viewPager = findViewById(R.id.rl_viewPager);
        viewPager = findViewById(R.id.viewPager);
        // 动画
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setPageMargin(50);
        viewPager.setOffscreenPageLimit(3);
        rl_viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });

        //电影名称 复仇者联盟
        mv_name = (TextView) findViewById(R.id.mv_name);
        //电影评分 3.7
        mv_score = (TextView) findViewById(R.id.mv_score);
        //电影类型 3d，imax，全景音
        mv_var = (TextView) findViewById(R.id.mv_var);
        //放映日期  今天---12月1号
        rg_date = (RadioGroup) findViewById(R.id.rg_date);
        //按钮组
        rb_1 =  findViewById(R.id.rb_1);
        rb_2 =  findViewById(R.id.rb_2);
        rb_3 =  findViewById(R.id.rb_3);
        rb_4 =  findViewById(R.id.rb_4);
        radioButtons = new ArrayList<>();
        radioButtons.add(rb_1);
        radioButtons.add(rb_2);
        radioButtons.add(rb_3);
        radioButtons.add(rb_4);

        //刷新视图
        rl_hall =  findViewById(R.id.rl_hall);
        AutoLinearLayoutManager layoutManager = new AutoLinearLayoutManager(this);
        rl_hall.setNestedScrollingEnabled(false);
        rl_hall.setLayoutManager(layoutManager);
    }

    //得到电影数据
    private void initData(String space_id,final String movieid) {
        //"http://yapi.demo.qunar.com/mock/11910/cinemainfo?movieid="+movieid+"&cinemaid="+cinemaId
        //http://yapi.demo.qunar.com/mock/11910/cinemainfo
        OkHttpUtil.doGet("http://192.168.43.240:8080/movie/selectBySpaceId?space_id="+space_id+"&movie_id="+movieid, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    if (!TextUtils.isEmpty(json)) {
                        Gson gson = new Gson();
                        cinemaDetailInfo = gson.fromJson(json, CinemaDetailInfo.class);
                        getDateShow(json);
                        handler.sendEmptyMessage(TextUtils.isEmpty(movieid) ? 1 : 2);
                    }
                }
            }
        });
    }
    //影院电影详细展示（解析封装）
    private void getDateShow(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject dateShow = data.getJSONObject("DataShow");
            dateShows = new HashMap<>();
            for (int i = 0; i < cinemaDetailInfo.getResult().getDates().size(); i++) {
                JSONArray show = dateShow.getJSONArray(cinemaDetailInfo.getResult().getDates().get(i));
                List<MovieShow> shows = new ArrayList<>();
                MovieShow movieShow;
                for (int j = 0; j < show.length(); j++) {
                    movieShow = new MovieShow();
                    movieShow.setTh(show.getJSONObject(j).getString("hallName"));
                    movieShow.setSellprice(show.getJSONObject(j).getString("mhPrice"));
                    movieShow.setCinemaprice("70");
                    movieShow.setTm(show.getJSONObject(j).getString("mhStart"));
                    movieShow.setSell(show.getJSONObject(j).getInt("movieStatus"));
                    movieShow.setTp(show.getJSONObject(j).getString("movieDescription"));
                    movieShow.setEnd(show.getJSONObject(j).getString("mhEnd"));
                    movieShow.setShowId(show.getJSONObject(j).getString("mhId"));
                    movieShow.setShowDate(show.getJSONObject(j).getString("mhTime"));
                    shows.add(movieShow);
                }
                dateShows.put(cinemaDetailInfo.getResult().getDates().get(i), shows);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //影院电影详细展示
    private void showData() {
        CinemaDetailInfo.Result.CinemaDetail cinemaDetail = cinemaDetailInfo.getResult().getCinemaDetail();
        tv_title.setText(cinemaDetail.getSpaceCityname());
        tv_name.setText(cinemaDetail.getSpaceCinemaname());
        tv_address.setText(cinemaDetail.getSpaceAddress());
        //加载影院 3d，wifi外在条件信息
//        for (int i = 0; i < cinemaDetail.getFeatureTags().size(); i++) {
//            View view = View.inflate(getApplicationContext(), R.layout.item_tag, null);
//            TextView tv_tag = (TextView) view.findViewById(R.id.tv_tag);
//            tv_tag.setText(cinemaDetail.getFeatureTags().get(i).getTag());
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.setMargins(5, 0, 0, 0);
//            tv_tag.setLayoutParams(layoutParams);
//            ll_tag.addView(tv_tag);
//        }
        //得到电影信息
        movies = cinemaDetailInfo.getResult().getMovies();
        if (movies.size() > 0) {
            //加载电影图片
            viewPager.setAdapter(new MyPagerAdapter());
            //滑动电影图片事件
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    initData(cinemaId,movies.get(position).getMovieId() + "");
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            showSelectMovie(0);
        }else {
            ToastUtil.showToast(YingYuanMovieInfoActivity.this,"该影院暂无电影上映");
        }
    }

    private void showSelectMovie(int position) {
        //加载电影名称评分，外在条件
        mv_name.setText(movies.get(position).getMovieName());
        mv_score.setText(movies.get(position).getMovieScore() + "");
        mv_var.setText(movies.get(position).getMovieDescription());
        //加载日期
        for (int i = 0; i < radioButtons.size(); i++) {
            if (i < cinemaDetailInfo.getResult().getDates().size()) {
                radioButtons.get(i).setVisibility(View.VISIBLE);
                radioButtons.get(i).setText(cinemaDetailInfo.getResult().getDates().get(i));
            } else {
                radioButtons.get(i).setVisibility(View.GONE);
            }
        }

        rg_date.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_1:
                        checkedId = 0;
                        break;
                    case R.id.rb_2:
                        checkedId = 1;
                        break;
                    case R.id.rb_3:
                        checkedId = 2;
                        break;
                    case R.id.rb_4:
                        checkedId = 3;
                        break;
                }
                showSelectHall(checkedId);
            }
        });
        showSelectHall(0);
    }

    private void showSelectHall(int checkedId) {
        hallAdapter = new HallAdapter(YingYuanMovieInfoActivity.this, dateShows.get(cinemaDetailInfo.getResult().getDates().get(checkedId)));
        rl_hall.setAdapter(hallAdapter);
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return movies.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(getApplicationContext(), R.layout.item_img_movie, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
            PicassoUtil.load(YingYuanMovieInfoActivity.this, movies.get(position).getMovieImg(), imageView);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public class MovieShow {

        /**
         * String th : 三号激光厅(位置)
         * String sellprice : 91360  ---》销售价格
         * String cinemaprice : 69902 -----》影院价格
         * boolean sell : true ---->在售？
         * String tm : 09:50 ---》开始时间
         * String tp : 3D  ---》类型
         * String  lang : 英语 ---》语言
         * String end : 12:03  ---》结束时间
         * String showId : 242824 ----》场次id
         * String showDate : 2017-09-08 ---》放映时间
         */
        private String th;
        private String sellprice;
        private String cinemaprice;
        private String tm;
        private String tp;
        private String end;
        private String showId;
        private String showDate;
        private int sell;

        public int getSell() {
            return sell;
        }

        public void setSell(int sell) {
            this.sell = sell;
        }

        public String getSellprice() {
            return sellprice;
        }

        public void setSellprice(String sellprice) {
            this.sellprice = sellprice;
        }

        public String getCinemaprice() {
            return cinemaprice;
        }

        public void setCinemaprice(String cinemaprice) {
            this.cinemaprice = cinemaprice;
        }

        public String getTh() {
            return th;
        }

        public void setTh(String th) {
            this.th = th;
        }

        public String getTm() {
            return tm;
        }

        public void setTm(String tm) {
            this.tm = tm;
        }

        public String getTp() {
            return tp;
        }

        public void setTp(String tp) {
            this.tp = tp;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getShowId() {
            return showId;
        }

        public void setShowId(String showId) {
            this.showId = showId;
        }

        public String getShowDate() {
            return showDate;
        }

        public void setShowDate(String showDate) {
            this.showDate = showDate;
        }
    }

    //设置切换动画
    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MAX_SCALE = 1.2f;
        private static final float MIN_SCALE = 1.0f;//0.85f

        @Override
        public void transformPage(View page, float position) {
            if (position <= 1) {
                float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
                page.setScaleX(scaleFactor);
                if (position > 0) {
                    page.setTranslationX(-scaleFactor * 2);
                } else if (position < 0) {
                    page.setTranslationX(scaleFactor * 2);
                }
                page.setScaleY(scaleFactor);
            } else {
                page.setScaleX(MIN_SCALE);
                page.setScaleY(MIN_SCALE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
