package com.example.ygh.app.pro.newpost.view.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.ygh.app.R;
import com.example.ygh.app.pro.newpost.view.ChoseSeatActivity;
import com.example.ygh.app.pro.newpost.view.YingYuanMovieInfoActivity;

import java.util.List;

public class HallAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<YingYuanMovieInfoActivity.MovieShow> list;

    public HallAdapter(Context context, List<YingYuanMovieInfoActivity.MovieShow> movieShows) {
        this.context = context;
        this.list = movieShows;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hall, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).hl_time.setText(list.get(position).getTm());
            ((ViewHolder) holder).hl_end.setText(list.get(position).getEnd() + "散场");
            ((ViewHolder) holder).hl_num.setText(list.get(position).getTh());
            ((ViewHolder) holder).hl_buy.setVisibility(list.get(position).getSell()==1 ? View.VISIBLE : View.GONE);
            ((ViewHolder) holder).hl_stop.setVisibility(list.get(position).getSell()==1 ? View.GONE : View.VISIBLE);
            if (list.get(position).getSell()==1) {
                ((ViewHolder) holder).hl_buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ChoseSeatActivity.class);
                        intent.putExtra("showId", list.get(position).getShowId());
                        context.startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView hl_time, hl_end, hl_type, hl_num;
        private Button hl_buy, hl_stop;

        public ViewHolder(View itemView) {
            super(itemView);
            hl_time = (TextView) itemView.findViewById(R.id.hl_time);
            hl_end = (TextView) itemView.findViewById(R.id.hl_end);
            hl_type = (TextView) itemView.findViewById(R.id.hl_type);
            hl_num = (TextView) itemView.findViewById(R.id.hl_num);
            hl_buy = (Button) itemView.findViewById(R.id.hl_buy);
            hl_stop = (Button) itemView.findViewById(R.id.hl_stop);
        }
    }
}
