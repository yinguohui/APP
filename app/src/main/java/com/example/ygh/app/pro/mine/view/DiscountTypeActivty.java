package com.example.ygh.app.pro.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ygh.app.R;

public class DiscountTypeActivty extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back;
    private Button tv_pass,tv_used,tv_use;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discounttype);
        init();
    }
    private void init(){
        iv_back = findViewById(R.id.iv_back);
        tv_pass = findViewById(R.id.tv_pass);
        tv_used = findViewById(R.id.tv_used);
        tv_use = findViewById(R.id.tv_use);

        iv_back.setOnClickListener(this);
        tv_pass.setOnClickListener(this);
        tv_used.setOnClickListener(this);
        tv_use.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_pass:
                intent =new Intent(DiscountTypeActivty.this,DiscountAcyivity.class);
                intent.putExtra("discount_status","-1");
                intent.putExtra("ds_type","0");
                startActivity(intent);
                break;
            case R.id.tv_used:
                intent =new Intent(DiscountTypeActivty.this,DiscountAcyivity.class);
                intent.putExtra("ds_type","-1");
                startActivity(intent);
                break;
            case R.id.tv_use:
                intent =new Intent(DiscountTypeActivty.this,DiscountAcyivity.class);
                intent.putExtra("discount_status","0");
                intent.putExtra("ds_type","0");
                startActivity(intent);
                break;
        }
    }
}
