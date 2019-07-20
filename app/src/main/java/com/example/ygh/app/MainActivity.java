package com.example.ygh.app;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.ygh.app.pro.attention.view.FunctionsFragment;
import com.example.ygh.app.pro.essence.view.EssenceFragment;
import com.example.ygh.app.pro.mine.view.MineFragment;
import com.example.ygh.app.pro.newpost.view.CinemaFragment;
import com.example.ygh.app.pro.publish.view.PublishFragment;
import com.example.ygh.app.util.SpUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener{
    FragmentTabHost fragmentTabHost;
    private List<TabItem> tabItemList;
    public LocationClient mLocationClient = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabData();
        initTabHost();
    }
    //初始化Tab数据
    private void initTabData(){
        tabItemList = new ArrayList<TabItem>();
        //添加首页Tab
        tabItemList.add(new TabItem(R.drawable.main_bottom_essence_normal
                ,R.drawable.main_bottom_essence_press,R.string.main_essence_text, FunctionsFragment.class));
        //添加影片Tab
        tabItemList.add(new TabItem(R.drawable.main_bottom_newpost_normal
                ,R.drawable.main_bottom_newpost_press,R.string.main_newpost_text, CinemaFragment.class));
        //添加发布Tab
        tabItemList.add(new TabItem(R.drawable.main_bottom_public_normal
                ,R.drawable.main_bottom_public_press,0, PublishFragment.class));
        //添加推荐Tab
        tabItemList.add(new TabItem(R.drawable.main_bottom_attention_normal
                ,R.drawable.main_bottom_attention_press,R.string.main_attention_text, EssenceFragment.class));
        //添加我的Tab
        tabItemList.add(new TabItem(R.drawable.main_bottom_mine_normal
                ,R.drawable.main_bottom_mine_press,R.string.main_mine_text, MineFragment.class));

    }

    //初始化主页选项卡视图
    private void initTabHost(){
        //获取FragmentTabHost
        fragmentTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        //绑定TabHost(绑定我们的body)
        fragmentTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        //去掉分割线
        fragmentTabHost.getTabWidget().setDividerDrawable(null);
        for (int i= 0; i<tabItemList.size() ;i++){
            TabItem tabItem = tabItemList.get(i);
            //绑定Fragment(将Fragment添加到FragmentTabHost组件上面)
            //newTabSpec:代表Tab名字
            //setIndicator:图片(今天我们采用布局文件--Tab到样式我们自己做)
            TabHost.TabSpec tabSpec = fragmentTabHost.
                    newTabSpec(tabItem.getTitleString())
                    .setIndicator(tabItem.getView());
            //添加Fragment
            //tabSpec:选项卡
            //tabItem.getFragmentClass():具体的Fragment
            //tabItem.getBundle():给我们的具体的Fragment传参数
            fragmentTabHost.addTab(tabSpec,tabItem.getFragmentClass(),tabItem.getBundle());
            //给我们的Tab按钮设置背景
            fragmentTabHost.getTabWidget()
                    .getChildAt(i)
                    .setBackgroundColor(getResources().getColor(R.color.main_bottom_bg));
            //监听点击Tab
            fragmentTabHost.setOnTabChangedListener(this);
            //默认选中第一个Tab
            if (i == 0){
                tabItem.setChecked(true);
            }
        }
    }

    public void onTabChanged(String tabId) {
        //ToastUtil.showToast(this,tabId);
        //重置Tab样式
        for (int i = 0;i < tabItemList.size();i++){
            TabItem tabItem = tabItemList.get(i);
            if (tabId.equals(tabItem.getTitleString())){
                //选中设置为选中壮体啊
                tabItem.setChecked(true);
            }else {
                //没有选择Tab样式设置为正常
                tabItem.setChecked(false);
            }
        }
    }

    //代表每一个Tab
    class TabItem{
        //正常情况下显示的图片
        private int imageNormal;
        //选中情况下显示的图片
        private int imagePress;
        //tab的名字
        private int title;
        private String titleString;
        private Class<? extends Fragment> fragmentClass;

        private View view;
        private ImageView imageView;
        private TextView textView;
        private Bundle bundle;

        public TabItem(int imageNormal,int imagePress,int title,Class<? extends Fragment> fragmentClass){
            this.imageNormal = imageNormal;
            this.imagePress = imagePress;
            this.title = title;
            this.fragmentClass = fragmentClass;
        }

        public Class<? extends Fragment> getFragmentClass() {
            return fragmentClass;
        }

        public int getImageNormal() {
            return imageNormal;
        }

        public int getImagePress() {
            return imagePress;
        }

        public int getTitle() {
            return title;
        }

        public String getTitleString() {
            if (title == 0){
                return "";
            }
            if (TextUtils.isEmpty(titleString)){
                titleString = getString(title);
            }
            return titleString;
        }

        public Bundle getBundle(){
            if (bundle == null){
                bundle = new Bundle();
            }
            bundle.putString("title",getTitleString());
            return bundle;
        }

        //还需要提供一个切换Tab方法---改变Tab样式
        public void setChecked(boolean isChecked){
            if(imageView != null){
                if (isChecked){
                    imageView.setImageResource(imagePress);
                }else {
                    imageView.setImageResource(imageNormal);
                }
            }
            if (textView != null && title != 0){
                if (isChecked){
                    textView.setTextColor(getResources().getColor(R.color.main_bottom_text_select));
                }else {
                    textView.setTextColor(getResources().getColor(R.color.main_bottom_text_normal));
                }
            }
        }

        public View getView(){
            if (this.view == null){
                this.view = getLayoutInflater().inflate(R.layout.view_tab_indicator,null);
                this.imageView = (ImageView)this.view.findViewById(R.id.iv_tab);
                this.textView = (TextView) this.view.findViewById(R.id.tv_tab);
                //判断资源是否存在,不再我就因此
                if (this.title == 0){
                    this.textView.setVisibility(View.GONE);
                }else {
                    this.textView.setVisibility(View.VISIBLE);
                    this.textView.setText(getTitleString());
                }
                //绑定图片默认资源
                this.imageView.setImageResource(imageNormal);
            }
            return this.view;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        int id = getIntent().getIntExtra("userloginflag", 0);
        if (id == 1) {
            fragmentTabHost.setCurrentTab(1);
            onTabChanged("影片");
        }
        getIntent().putExtra("userloginflag",0);
    }
    public void getLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(new MyLocationListener());
        //注册监听函数
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(3000);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuffer sb = new StringBuffer(256);
            String strLocation = "";

            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型

            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //获取经度信息
            SpUtil.putString(MainActivity.this, "CITY", location.getCity() + "");
            SpUtil.putString(MainActivity.this, "latitude", location.getLatitude() + "");
            SpUtil.putString(MainActivity.this, "longitude", location.getLongitude() + "");

            if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息
                strLocation = location.getAddrStr();

                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息

                sb.append("\ndescribe : ");
                sb.append("网络定位成功");

                mLocationClient.stop();
                Intent intent = new Intent();
                intent.setAction("com.goldou.location");
                intent.putExtra("location", strLocation);
                MainActivity.this.sendBroadcast(intent);
            }

            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息

            Log.i("BaiduLocationApiDem", sb.toString());
        }
    }

}
