package com.example.ygh.app.pro.mine.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.OrderInfo;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter{
    //数据
    private List<OrderInfo> list;
    //上下文
    private Context context;
    //存储选择的优惠券


    public MyOrderAdapter(Context context,List<OrderInfo> list){
        this.context = context;
        this.list = list;
    }

    //初始化
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_myorder,viewGroup,false);
        return new ViewHolderOrder(view);
    }

    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderOrder) {
            //discount_name, discount_type, discount_id, discount_time;
            try {
                list.get(position).getOrderLsh();
            }catch (Exception e){
                e.printStackTrace();
            }
            ((ViewHolderOrder) holder).or_lsh.setText("流水号-----"+list.get(position).getOrderLsh());
            ((ViewHolderOrder) holder).or_name.setText(list.get(position).getMovieName());
            ((ViewHolderOrder) holder).or_cinemaname.setText(list.get(position).getSpaceCinemaname()+"----地点:"+list.get(position).getSpaceAddress());
            ((ViewHolderOrder) holder).or_hall.setText(list.get(position).getHallName()+"---"+list.get(position).getSeatInfo());
            ((ViewHolderOrder) holder).or_time.setText("放映时间:"+list.get(position).getMhTime().substring(0,10)+"   "+list.get(position).getMhStart()+"---"+list.get(position).getMhEnd());
            ((ViewHolderOrder) holder).or_price.setText("价格：  "+list.get(position).getOrderMoney());
            ((ViewHolderOrder) holder).or_createtime.setText(""+list.get(position).getOrderCreat());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //viewholder类
    public class ViewHolderOrder extends RecyclerView.ViewHolder {

        private TextView or_lsh, or_name, or_cinemaname, or_time,or_price,or_createtime,or_hall;
        private LinearLayout ll_order;
        public ViewHolderOrder(View itemView) {
            super(itemView);
            or_lsh =  itemView.findViewById(R.id.or_lsh);
            or_name =  itemView.findViewById(R.id.or_name);
            or_cinemaname =  itemView.findViewById(R.id.or_cinemaname);
            or_hall =  itemView.findViewById(R.id.or_hall);
            or_time =  itemView.findViewById(R.id.or_time);
            or_price = itemView.findViewById(R.id.or_price);
            or_createtime = itemView.findViewById(R.id.or_createtime);
            ll_order = itemView.findViewById(R.id.ll_order);
        }
    }
}
