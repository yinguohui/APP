package com.example.ygh.app.pro.mine.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ygh.app.R;
import com.example.ygh.app.bean.PostsListBean;
import com.example.ygh.app.pro.essence.view.CommentDetailActivity;
import com.example.ygh.app.pro.essence.view.views.CircleNetworkImageImage;
import com.example.ygh.app.util.DateUtils;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.ToastUtil;
import com.example.ygh.app.util.VolleyUtils;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TitleAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private List<PostsListBean.Info.PostList> list;
    //九宫格图片
    private NineGridImageViewAdapter nineGridImageViewAdapter = new NineGridImageViewAdapter<String>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String o) {
            Glide.with(context).load(o).into(imageView);
        }
    };

    public TitleAdapter(List<PostsListBean.Info.PostList> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_title,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PostsListBean.Info.PostList postList = list.get(position);
        VolleyUtils.loadImage(context,((ViewHolder) holder).iv_header,postList.getUser_img());
        ((ViewHolder) holder).tv_name.setText(postList.getUser_name());
        ((ViewHolder) holder).tv_time.setText(DateUtils.parseDate(postList.getTitle_create_time()));
        ((ViewHolder) holder).tv_content.setText(postList.getTitle_content());
        ((ViewHolder) holder).iv_video.setAdapter(nineGridImageViewAdapter);
        List<String> stringList= new ArrayList<>();
        String[] strings = postList.getTitle_img().split("&");
        for (String s:strings) {
            stringList.add(s);
        }
        ((ViewHolder) holder).iv_video.setImagesData(stringList);
        ((ViewHolder) holder).tv_like.setText(postList.getTitle_love());
        ((ViewHolder) holder).tv_dislike.setText(postList.getTitle_hate());
        ((ViewHolder) holder).tv_comment.setText(postList.getTitle_comment());
        ((ViewHolder) holder).tv_forword.setText(postList.getTitle_forword());
        ((ViewHolder) holder).ll_like.setOnClickListener(this);
        ((ViewHolder) holder).ll_dislike.setOnClickListener(this);
        ((ViewHolder) holder).ll_forword.setOnClickListener(this);
        ((ViewHolder) holder).ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CommentDetailActivity.class);
                intent.putExtra("titleid",postList.getTitle_id());
                context.startActivity(intent);
            }
        });
        //删除主题
        ((ViewHolder) holder).tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shhowAlertDialog(postList.getTitle_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_like:
                ToastUtil.showToast(context,"点赞加+1");
                break;
            case R.id.ll_dislike:
                ToastUtil.showToast(context,"讨厌加+1");
                break;
            case  R.id.ll_forword:
                ToastUtil.showToast(context,"分享加");
                break;
        }
    }

    private void shhowAlertDialog(final String title_id){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context);
        normalDialog.setTitle("删除主题");
        normalDialog.setMessage("确定要删除这个主题么?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletetitle(title_id);
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // 显示
        normalDialog.show();
    }
    private void deletetitle(String title_id){
        OkHttpUtil.doGet("http://192.168.43.240:8080/title/deltitle?title_status=-1&title_id="+title_id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtil.showToast(context,"网络错误");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    ToastUtil.showToast(context,"删除成功");
                }
            }
        });
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleNetworkImageImage iv_header;
        public TextView tv_name;
        public TextView tv_time;
        public TextView tv_content;
        public NineGridImageView iv_video;

        public LinearLayout ll_like;
        public TextView tv_like;

        public LinearLayout ll_dislike;
        public TextView tv_dislike;

        public LinearLayout ll_forword;
        public TextView tv_forword;

        public LinearLayout ll_comment;
        public TextView tv_comment;

        private TextView tv_delete;

        public int position;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_header = itemView .findViewById(R.id.iv_header);
            tv_name =  itemView.findViewById(R.id.tv_name);
            tv_time =  itemView.findViewById(R.id.tv_time);
            tv_content = itemView.findViewById(R.id.tv_content);
            iv_video =  itemView.findViewById(R.id.iv_video);

            ll_like = itemView.findViewById(R.id.ll_like);
            tv_like =  itemView.findViewById(R.id.tv_like);
            ll_dislike = itemView.findViewById(R.id.ll_dislike);
            tv_dislike = itemView.findViewById(R.id.tv_dislike);
            ll_forword = itemView.findViewById(R.id.ll_forword);
            tv_forword = itemView.findViewById(R.id.tv_forword);
            ll_comment = itemView.findViewById(R.id.ll_comment);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }
}
