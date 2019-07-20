package com.example.ygh.app.pro.newpost.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.example.ygh.app.R;
import com.example.ygh.app.bean.YingYuanBean;
import com.example.ygh.app.pro.newpost.view.YingYuanMovieInfoActivity;

import java.util.List;

public class NewpostYingyuanAdapter extends BaseRecyclerAdapter<NewpostYingyuanAdapter.VideoAdapterViewHolder> {

    private Context context;
    private List<YingYuanBean.Result> list;

    public NewpostYingyuanAdapter(List<YingYuanBean.Result> list, Context context) {
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
                R.layout.cinema_fragment, parent, false);
        VideoAdapterViewHolder holder = new VideoAdapterViewHolder(v, true);
        return holder;
    }

    //给我们的视图绑定数据
    @Override
    public void onBindViewHolder(VideoAdapterViewHolder holder, int position, boolean isItem) {
//        YingYuanBean.Result postList = this.list.get(position);
//        holder.tv_yingyuanname.setText(postList.getCinemaName());
//        holder.tv_address.setText(postList.getAddress());
//        holder.tv_luxian.setText(postList.getTrafficRoutes());
    }

    @Override
    public int getAdapterItemCount() {
        return this.list.size();
    }

    public class VideoAdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_yingyuanname;
        public TextView tv_address;
        public TextView tv_luxian;
        public TextView tv_distance;

        public int position;

        public VideoAdapterViewHolder(final View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                tv_yingyuanname = itemView.findViewById(R.id.cm_name);
                tv_yingyuanname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(itemView.getContext(),YingYuanMovieInfoActivity.class);
                        itemView.getContext().startActivity(intent);
                    }
                });
                tv_address =  itemView.findViewById(R.id.tv_address);
                tv_luxian =  itemView.findViewById(R.id.cm_distance);
                tv_distance =  itemView.findViewById(R.id.cm_address);
            }

        }
    }
}
