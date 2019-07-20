package com.example.ygh.app.pro.attention.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.MovieResult;
import com.example.ygh.app.pro.attention.view.MovieInfoActivity;
import com.example.ygh.app.util.PicassoUtil;

import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter {

    private List<MovieResult.Movie> list;
    private Context context;

    public RecommendAdapter(Context context, List<MovieResult.Movie> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            PicassoUtil.loadRound(context, list.get(position).getMovieImg(), ((ViewHolder) holder).mv_icon, 5);
            ((ViewHolder) holder).mv_name.setText(list.get(position).getMovieName());
            ((ViewHolder) holder).mv_name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            ((ViewHolder) holder).rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MovieInfoActivity.class);
                    intent.putExtra("movieId", list.get(position).getMovieId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mv_icon;
        private TextView mv_name, mv_score;
        private RelativeLayout rl_item;

        public ViewHolder(View itemView) {
            super(itemView);
            mv_icon =  itemView.findViewById(R.id.mv_icon);
            mv_name =  itemView.findViewById(R.id.mv_name);
            mv_score =  itemView.findViewById(R.id.mv_score);
            rl_item =  itemView.findViewById(R.id.rl_item);
        }
    }
}