package com.example.ygh.app.pro.essence.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.example.ygh.app.R;
import com.example.ygh.app.bean.PostsListBean;
import com.example.ygh.app.pro.essence.view.CommentDetailActivity;
import com.example.ygh.app.pro.essence.view.views.CircleNetworkImageImage;
import com.example.ygh.app.util.DateUtils;
import com.example.ygh.app.util.ToastUtil;
import com.example.ygh.app.util.VolleyUtils;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示和缓存数据
 *
 * Created by Dream on 16/5/27.
 */
public class EssenceVideoAdapter extends BaseRecyclerAdapter<EssenceVideoAdapter.VideoAdapterViewHolder> implements View.OnClickListener{

    private Context context;
    private List<PostsListBean.Info.PostList> list;
    //九宫格图片
    private NineGridImageViewAdapter nineGridImageViewAdapter = new NineGridImageViewAdapter<String>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String o) {
            Glide.with(context).load(o).into(imageView);
        }
    };

    public EssenceVideoAdapter(List<PostsListBean.Info.PostList> list, Context context) {
        this.context = context;
        this.list = list;
    }


    /**
     * 配置ViewHoder
     * @param view
     * @return
     */
    @Override
    public VideoAdapterViewHolder getViewHolder(View view) {
        return new VideoAdapterViewHolder(view, false);
    }

    //创建布局
    @Override
    public VideoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.item_essence_video_layout, parent, false);
        VideoAdapterViewHolder holder = new VideoAdapterViewHolder(v, true);
        return holder;
    }

    //给我们的视图绑定数据
    @Override
    public void onBindViewHolder(VideoAdapterViewHolder holder, int position, boolean isItem) {
        PostsListBean.Info.PostList postList = this.list.get(position);
        VolleyUtils.loadImage(context,holder.iv_header,postList.getUser_img());
        holder.tv_name.setText(postList.getUser_name());
        holder.tv_time.setText(DateUtils.parseDate(postList.getTitle_create_time()));
        holder.tv_content.setText(postList.getTitle_content());
        holder.iv_video.setAdapter(nineGridImageViewAdapter);
        List<String> stringList= new ArrayList<>();
        String[] strings = postList.getTitle_img().split("&");
        for (String s:strings) {
            stringList.add(s);
        }
        holder.iv_video.setImagesData(stringList);
        holder.tv_like.setText(postList.getTitle_love());
        holder.tv_dislike.setText(postList.getTitle_hate());
        holder.tv_comment.setText(postList.getTitle_comment());
        holder.tv_forword.setText(postList.getTitle_forword());
        holder.ll_like.setOnClickListener(this);
        holder.ll_dislike.setOnClickListener(this);
        holder.ll_forword.setOnClickListener(this);
        holder.ll_comment.setOnClickListener(this);
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
            case R.id.ll_comment:
                Intent intent = new Intent(context,CommentDetailActivity.class);
                context.startActivity(intent);
                ToastUtil.showToast(context,"评论跳转");
                break;
        }
    }
    @Override
    public int getAdapterItemCount() {
        return this.list.size();
    }


    public class VideoAdapterViewHolder extends RecyclerView.ViewHolder {

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

        public int position;

        public VideoAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
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
            }

        }
    }
}
