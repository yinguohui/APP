package com.example.ygh.app.pro.newpost.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ygh.app.R;
import com.example.ygh.app.bean.DiscountResult;
import com.example.ygh.app.bean.MyDiscountResult;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.ToastUtil;

import java.util.List;

public class DiscountUseAdapter extends RecyclerView.Adapter{
    //数据
    private List<MyDiscountResult.MyDiscountBean> list;
    //上下文
    private Context context;
    //存储选择的优惠券


    public DiscountUseAdapter(Context context,List<MyDiscountResult.MyDiscountBean> list){
        this.context = context;
        this.list = list;
    }

    //初始化
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_discount,viewGroup,false);
        return new ViewHolder(view);
    }

    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position) {
        if (holder instanceof ViewHolder) {
            //discount_name, discount_type, discount_id, discount_time;
            ((ViewHolder) holder).discount_name.setText(list.get(position).getDiscountName());
            ((ViewHolder) holder).discount_type.setText(list.get(position).getDiscountType());
            ((ViewHolder) holder).discount_id.setText(list.get(position).getDiscountId());
            ((ViewHolder) holder).discount_time.setText("有效期："+list.get(position).getDiscountStart()+"至"+list.get(position).getDiscountEnd());
            ((ViewHolder) holder).ll_discount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SpUtil.putString(context,"discountid",list.get(position).getDsId());
                    ToastUtil.showToast(context,"你选择了"+list.get(position).getDiscountName());
                    SpUtil.putString(context,"discounttype",list.get(position).getDiscountType());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //viewholder类
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView discount_name, discount_type, discount_id, discount_time;
        private LinearLayout ll_discount;
        public ViewHolder(View itemView) {
            super(itemView);
            discount_name =  itemView.findViewById(R.id.discount_name);
            discount_type =  itemView.findViewById(R.id.discount_type);
            discount_id =  itemView.findViewById(R.id.discount_id);
            discount_time =  itemView.findViewById(R.id.discount_time);
            ll_discount = itemView.findViewById(R.id.ll_discount);
        }
    }
}
