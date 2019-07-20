package com.example.ygh.app.pro.mine.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.SellInfo;
import com.example.ygh.app.bean.TicketResult;
import com.example.ygh.app.pro.mine.view.BuyTicketActivity;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MallAdapter extends RecyclerView.Adapter{
    private List<TicketResult.TicketInfo> list;
    private Context context;
    private Map<String,String> map =new HashMap<>();

    public MallAdapter(Context context, List<TicketResult.TicketInfo> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mall, parent, false);
        return new MallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MallViewHolder) {
            ((MallViewHolder) holder).cinema_name.setText(list.get(position).getSpaceCinemaname());
            ((MallViewHolder) holder).cinema_address.setText("地址："+list.get(position).getSpaceAddress());
            ((MallViewHolder) holder).cinema_starttime.setText("放映时间:"+list.get(position).getMhTime().substring(0,10)+"   "+list.get(position).getMhStart()+"---"+list.get(position).getMhEnd());
            ((MallViewHolder) holder).movie_mame.setText(list.get(position).getMovieName());
            ((MallViewHolder) holder).movie_address.setText(list.get(position).getHallName());
            ((MallViewHolder) holder).movie_site.setText(list.get(position).getSeatRow()+"排"+list.get(position).getSeatColumn()+"座");
            ((MallViewHolder) holder).mall_price.setText(list.get(position).getSeatHallId()+"");
            ((MallViewHolder) holder).bt_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buyticket();
                }

                private void buyticket() {
                    if (SpUtil.getString(context,"userid","").equals(list.get(position).getSeatType())){
                        ToastUtil.showToast(context,"不能购买自己的票");
                    }else {
                        Intent intent = new Intent(context,BuyTicketActivity.class);
                        intent.putExtra("sellmoney",list.get(position).getSeatHallId());
                        intent.putExtra("selluserid",list.get(position).getSeatType());
                        intent.putExtra("seatid",list.get(position).getSeatId());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MallViewHolder extends RecyclerView.ViewHolder {

        private TextView cinema_name, cinema_address, cinema_starttime, cinema_endtime, movie_mame, movie_site,mall_price,movie_address;
        private RelativeLayout rl_item_mall;
        private Button bt_buy;
        public MallViewHolder(View itemView) {
            super(itemView);
            cinema_name = (TextView) itemView.findViewById(R.id.cinema_name);
            cinema_address = (TextView) itemView.findViewById(R.id.cinema_address);
            cinema_starttime = (TextView) itemView.findViewById(R.id.cinema_starttime);
            cinema_endtime = (TextView) itemView.findViewById(R.id.cinema_endtime);
            movie_mame = (TextView) itemView.findViewById(R.id.movie_mame);
            movie_site = (TextView) itemView.findViewById(R.id.movie_site);
            mall_price = (TextView) itemView.findViewById(R.id.mall_price);
            movie_address = (TextView) itemView.findViewById(R.id.movie_address);
            bt_buy = itemView.findViewById(R.id.bt_buy);
            rl_item_mall = (RelativeLayout) itemView.findViewById(R.id.rl_item_mall);
        }
    }
}
