package com.example.ygh.app.pro.newpost.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.CinemaInfo;
import com.example.ygh.app.pro.newpost.view.YingYuanMovieInfoActivity;

import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter {

    private List<CinemaInfo.Data> list;
    private Context context;

    public CinemaAdapter(Context context, List<CinemaInfo.Data> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cinema, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).cm_name.setText(list.get(position).getSpaceCinemaname());
            ((ViewHolder) holder).cm_address.setText(list.get(position).getSpaceAddress());
            ((ViewHolder) holder).cm_site.setVisibility(true ? View.VISIBLE : View.GONE);
            ((ViewHolder) holder).cm_imax.setVisibility(true ? View.VISIBLE : View.GONE);
            double distance = list.get(position).getSpaceDistance();
            if (distance > 0) {
                if (distance < 1000) {
                    ((ViewHolder) holder).cm_distance.setText(distance + "m");
                } else {
                    float f = ((float) (distance / 100)) / 10;
                    ((ViewHolder) holder).cm_distance.setText(f + "km");
                }
            }
            ((ViewHolder) holder).rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, YingYuanMovieInfoActivity.class);
                    intent.putExtra("cinemaId", list.get(position).getSpaceId());
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

        private TextView cm_name, cm_price, cm_address, cm_site, cm_imax, cm_distance;
        private RelativeLayout rl_item;

        public ViewHolder(View itemView) {
            super(itemView);
            cm_name = (TextView) itemView.findViewById(R.id.cm_name);
            cm_price = (TextView) itemView.findViewById(R.id.cm_price);
            cm_address = (TextView) itemView.findViewById(R.id.cm_address);
            cm_site = (TextView) itemView.findViewById(R.id.cm_site);
            cm_imax = (TextView) itemView.findViewById(R.id.cm_imax);
            cm_distance = (TextView) itemView.findViewById(R.id.cm_distance);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);
        }
    }
}
