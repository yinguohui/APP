package com.example.ygh.app.pro.essence.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ygh.app.R;
import com.example.ygh.app.bean.CommentBean;
import com.example.ygh.app.bean.CommentDetailBean;
import com.example.ygh.app.bean.PostsListBean;
import com.example.ygh.app.bean.ReplyDetailBean;
import com.example.ygh.app.bean.Result;
import com.example.ygh.app.bean.Title;
import com.example.ygh.app.bean.TitleList;
import com.example.ygh.app.bean.UtilResult;
import com.example.ygh.app.pro.essence.view.adapter.CommentExpandAdapter;
import com.example.ygh.app.pro.essence.view.adapter.ShouyeAdapter;
import com.example.ygh.app.pro.essence.view.views.CircleNetworkImageImage;
import com.example.ygh.app.pro.essence.view.views.CommentExpandableListView;
import com.example.ygh.app.pro.mine.view.TitleActivity;
import com.example.ygh.app.pro.mine.view.adapter.TitleAdapter;
import com.example.ygh.app.util.DateUtils;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.ToastUtil;
import com.example.ygh.app.util.VolleyUtils;
import com.google.gson.Gson;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//评论详情
public class CommentDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CommentDetailActivity";
    private android.support.v7.widget.Toolbar toolbar;
    private TextView tv_comment;
    private CommentExpandableListView expandableListView;
    private CommentExpandAdapter adapter;
    private CommentBean commentBean;
    private List<CommentDetailBean> commentsList = new ArrayList<>();
    private BottomSheetDialog dialog;
    private Gson gson=new Gson();
    private TitleList title;
    private CircleNetworkImageImage userLogo;
    private TextView time;
    private TextView detail_page_content;
    private TextView detail_page_userName;
    private NineGridImageView detail_page_img;
    private Map<String,String> map = new HashMap<>();
    //九宫格图片
    private NineGridImageViewAdapter nineGridImageViewAdapter = new NineGridImageViewAdapter<String>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String o) {
            Glide.with(CommentDetailActivity.this).load(o).into(imageView);
        }
    };

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ToastUtil.showToast(CommentDetailActivity.this,"评论成功");
                    break;
                case 2:
                    ToastUtil.showToast(CommentDetailActivity.this,"评论失败");
                    break;
                case 4:
                    inittitle();
                case 5:
                    initExpandableListView(commentsList);
                    break;
                case 6:
                    ToastUtil.showToast(CommentDetailActivity.this,"回复成功");
                    finish();
                    break;
                case 7:
                    ToastUtil.showToast(CommentDetailActivity.this,"回复失败");
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);
        initView();
    }

    private void initView() {
        map.put("userid",SpUtil.getString(CommentDetailActivity.this,"userid",""));
        map.put("titleid",getIntent().getStringExtra("titleid"));
        //初始化视图
        toolbar =  findViewById(R.id.toolbar);
        expandableListView = findViewById(R.id.detail_page_lv_comment);
        tv_comment = findViewById(R.id.detail_page_do_comment);
        //
        userLogo = findViewById(R.id.detail_page_userLogo);
        detail_page_userName = findViewById(R.id.detail_page_userName);
        time = findViewById(R.id.detail_page_time);
        detail_page_content = findViewById(R.id.detail_page_content);
        detail_page_img =findViewById(R.id.detail_page_img);
        getCommentsList(map.get("titleid"));
        initdata();

        tv_comment.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("详情");

    }
    //初始化发布者信息
    private void initdata(){
        OkHttpUtil.doGet("http://192.168.43.240:8080/title/gettitlebyid?title_id="+map.get("titleid"), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                ToastUtil.showToast(CommentDetailActivity.this,"访问错误");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    title = gson.fromJson(response.body().string(),TitleList.class);
                    handler.sendEmptyMessage(4);
                }else {
                    ToastUtil.showToast(CommentDetailActivity.this,"访问错误");
                }
            }
        });
    }
    private void inittitle(){
        VolleyUtils.loadImage(CommentDetailActivity.this,userLogo,title.getUser_img());
        detail_page_userName.setText(title.getUser_name());
        time.setText(title.getTitle_create_time());
        detail_page_content.setText(title.getTitle_content());
        List<String> stringList= new ArrayList<>();
        String[] strings = title.getTitle_img().split("&");
        for (String s:strings) {
            stringList.add(s);
        }
        detail_page_img.setAdapter(nineGridImageViewAdapter);
        detail_page_img.setImagesData(stringList);


    }

    //初始化评论信息
    private void getCommentsList(String titleid){

        OkHttpUtil.doGet("http://192.168.43.240:8080/title/reply?title_id="+titleid, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                ToastUtil.showToast(CommentDetailActivity.this,"加载失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    commentBean = gson.fromJson(response.body().string(), CommentBean.class);
                    if (commentBean.getData().getList().size()>0){
                        commentsList = commentBean.getData().getList();
                    }else {
                        ToastUtil.showToast(CommentDetailActivity.this,"暂无评论");
                    }
                    handler.sendEmptyMessage(5);
                   // initExpandableListView(commentsList);
                }
            }
        });
    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final List<CommentDetailBean> commentList){
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new CommentExpandAdapter(this, commentList);
        expandableListView.setAdapter(adapter);
        for(int i = 0; i<commentList.size(); i++){
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                Log.e(TAG, "onGroupClick: 当前的评论id>>>"+commentList.get(groupPosition).getReviewId());
                if(isExpanded){
                    expandableListView.collapseGroup(groupPosition);
                }else {
                    expandableListView.expandGroup(groupPosition, true);
                }
                showReplyDialog(groupPosition);
                return true;
            }
        });


        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detail_page_do_comment:
                showCommentDialog();
                break;

        }
    }

    /**
     * func:弹出评论框
     */
    private void showCommentDialog(){
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){
                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    CommentDetailBean detailBean = new CommentDetailBean(SpUtil.getString(CommentDetailActivity.this,"user_name",""), commentContent,"刚刚",SpUtil.getString(CommentDetailActivity.this,"user_img",""));
                    HashMap<String,String> commentdata = new HashMap<>();
                    adapter.addTheCommentData(detailBean);
                    //本地文件取
                    commentdata.put("reviewUserId",map.get("userid"));
                    //评论
                    commentdata.put("reviewTitleId",map.get("titleid"));
                    commentdata.put("reviewContent",commentContent);
                    OkHttpUtil.doPost("http://192.168.43.240:8080/title/addcomment", commentdata, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            ToastUtil.showToast(CommentDetailActivity.this,"评论失败");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                                if (response.isSuccessful()){
                                    commentresult(response.body().string());
                                }
                        }
                    });
                    Toast.makeText(CommentDetailActivity.this,"评论成功",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(CommentDetailActivity.this,"评论内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence)){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    /**
     * func:弹出回复框
     */
    private void showReplyDialog(final int position){
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment =  commentView.findViewById(R.id.dialog_comment_bt);
        map.put("reviewId",commentsList.get(position).getReviewId());
        commentText.setHint("回复 " + commentsList.get(position).getUserName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(replyContent)){

                    dialog.dismiss();
                    ReplyDetailBean detailBean = new ReplyDetailBean(map.get("user_name"),replyContent);
                    adapter.addTheReplyData(detailBean, position);
                    //adapter.notifyDataSetChanged();
                    HashMap<String,String> replydata = new HashMap<>();
                    //本地文件取
                    replydata.put("rereviewUserId",map.get("userid"));
                    //评论id
                    replydata.put("rereviewReviewId",map.get("reviewId"));
                    replydata.put("rereviewContent",replyContent);
                    replydata.put("create",DateUtils.timeParse(new Date().getTime()));
                    OkHttpUtil.doPost("http://192.168.43.240:8080/title/addreview", replydata, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            ToastUtil.showToast(CommentDetailActivity.this,"回复失败");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            replyresult(response.body().string());
                        }
                    });
                }else {
                    Toast.makeText(CommentDetailActivity.this,"回复内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence)){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }


    /*
    * 添加回复结果
    * */

    private void commentresult(String data){
        try {
            UtilResult utilResult = gson.fromJson(data,UtilResult.class);
            if (utilResult.getStatus().equals("success")){
                handler.sendEmptyMessage(1);
            }else
                handler.sendEmptyMessage(2);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void replyresult(String data){
        try {
            UtilResult utilResult = gson.fromJson(data,UtilResult.class);
            if (utilResult.getStatus().equals("success")){
                handler.sendEmptyMessage(6);
            }else
                handler.sendEmptyMessage(7);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}