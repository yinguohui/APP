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

public class MyMoneyOrderAdapter extends RecyclerView.Adapter{
    //数据
    private List<OrderInfo> list;
    //上下文
    private Context context;
    //存储选择的优惠券


    public MyMoneyOrderAdapter(Context context, List<OrderInfo> list){
        this.context = context;
        this.list = list;
    }

    //初始化
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mymoneyorder,viewGroup,false);
        return new ViewHolderOrder(view);
    }

    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderOrder) {
            //discount_name, discount_type, discount_id, discount_time;
            try {
                list.get(position).getOrderLsh();
                ((ViewHolderOrder) holder).or_lsh.setText("流水号-----"+list.get(position).getOrderLsh());
                ((ViewHolderOrder) holder).or_price.setText("充值金额：  "+list.get(position).getOrderMoney());
                ((ViewHolderOrder) holder).or_createtime.setText("充值时间"+list.get(position).getOrderCreat());
                ((ViewHolderOrder) holder).or_type.setText("充值类型"+list.get(position).getOrderOr1());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //viewholder类
    public class ViewHolderOrder extends RecyclerView.ViewHolder {

        private TextView or_lsh, or_price,or_createtime,or_type;
        private LinearLayout ll_order;
        public ViewHolderOrder(View itemView) {
            super(itemView);
            or_lsh =  itemView.findViewById(R.id.or_lsh);
            or_price = itemView.findViewById(R.id.or_price);
            or_type = itemView.findViewById(R.id.or_type);
            or_createtime = itemView.findViewById(R.id.or_createtime);
            ll_order = itemView.findViewById(R.id.ll_order);
        }
    }
}
