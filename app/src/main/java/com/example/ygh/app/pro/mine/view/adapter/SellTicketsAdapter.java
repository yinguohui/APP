package com.example.ygh.app.pro.mine.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.SellInfo;
import com.example.ygh.app.bean.TicketResult;
import com.example.ygh.app.util.SpUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellTicketsAdapter extends RecyclerView.Adapter{
    private List<TicketResult.TicketInfo> list;
    private Context context;
    public SellTicketsAdapter(Context context, List<TicketResult.TicketInfo> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selltickets, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).cinema_name.setText(list.get(position).getSpaceCinemaname());
            ((ViewHolder) holder).cinema_address.setText("地址"+list.get(position).getSpaceAddress());
            ((ViewHolder) holder).cinema_starttime.setText("放映时间:"+list.get(position).getMhTime().substring(0,10)+"   "+list.get(position).getMhStart()+"---"+list.get(position).getMhEnd());
            ((ViewHolder) holder).movie_mame.setText(list.get(position).getMovieName());
            ((ViewHolder) holder).movie_address.setText(list.get(position).getHallName());
            ((ViewHolder) holder).movie_site.setText(list.get(position).getSeatRow()+"排"+list.get(position).getSeatColumn()+"座");
            ((ViewHolder) holder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked==true){
                        SpUtil.putString(context,"seatid",list.get(position).getSeatId());
                    }else {
                        SpUtil.putString(context,"seatid","");
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cinema_name, cinema_address, cinema_starttime, movie_address,movie_mame, movie_site;
        private RelativeLayout rl_item_selltickets;
        private CheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);
            cinema_name = (TextView) itemView.findViewById(R.id.cinema_name);
            cinema_address = (TextView) itemView.findViewById(R.id.cinema_address);
            cinema_starttime = (TextView) itemView.findViewById(R.id.cinema_starttime);
            movie_mame = (TextView) itemView.findViewById(R.id.movie_mame);
            movie_address = (TextView) itemView.findViewById(R.id.movie_address);
            movie_site = (TextView) itemView.findViewById(R.id.movie_site);
            checkBox = itemView.findViewById(R.id.bb);
            rl_item_selltickets = (RelativeLayout) itemView.findViewById(R.id.rl_item_selltickets);
        }
    }
}
