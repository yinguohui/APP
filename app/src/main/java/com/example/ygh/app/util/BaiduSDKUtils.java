package com.example.ygh.app.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class BaiduSDKUtils {
    private Context context;
    private LocationClient locationClient;
    private BDLocationListener bdLocationListener;

    public BaiduSDKUtils(Context context){
        this.context = context;
    }
    public  void  init(){
        locationClient=new LocationClient(context);
        bdLocationListener=new MyLocationListener();
        locationClient.registerLocationListener(bdLocationListener);
        initLocation();
        locationClient.start();
    }
    /**
     * 设置定位参数包括：定位模式（高精度定位模式、
     * 低功耗定位模式和仅用设备定位模式），返回坐标类型，是否打开GPS，
     * 是否返回地址信息、位置语义化信息、POI信息等等。
     */
    private void initLocation() {
        LocationClientOption option=new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span=5000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        locationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            StringBuffer sb = new StringBuffer(256);
            String strLocation = "";

            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //获取经度信息
            SpUtil.putString(context, "latitude", location.getLatitude() + "");
            SpUtil.putString(context, "longitude", location.getLongitude() + "");

            if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息
                strLocation = location.getAddrStr();
                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");

                locationClient.stop();
                Intent intent = new Intent();
                intent.setAction("com.goldou.location");
                intent.putExtra("location", strLocation);
                context.sendBroadcast(intent);
            }

            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息
            Log.i("BaiduLocationApiDem", sb.toString());
        }
    }

}
