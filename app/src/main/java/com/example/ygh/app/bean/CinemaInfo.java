package com.example.ygh.app.bean;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class CinemaInfo {

    private String status;
    private String message;
    private int code;
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class Data implements Comparable {
    //        error_code	int	返回码
    //        id	string	电影院ID
    //        cityName	string	影院所属城市
    //        cinemaName	string	影院名称
    //        address	string	影院地址
    //        telephone	string	联系电话
    //        price String 最低价格
    //        latitude	double	纬度，适合百度地图
    //        longitude	double	经度，适合百度地图
    //        trafficRoutes	string	交通路线
    //        distance	string	大概距离(米)可以为空
    //        issite   boolen    是否有座位
    //        isimax   boolen    是否imax
    //        qita     String    备用字段
    //        qita1     String   备用字段1
    private String spaceId;

        private String spaceCityname;

        private String spaceCinemaname;

        private String spaceAddress;

        private String spaceTelephone;

        private String spaceLatitude;

        private String spaceLongitude;

        private String spaceTrafficroutes;

        private Double spaceDistance;

        private String spaceCreate;

        private static final long serialVersionUID = 1L;

        public String getSpaceId() {
            return spaceId;
        }

        public void setSpaceId(String spaceId) {
            this.spaceId = spaceId;
        }

        public String getSpaceCityname() {
            return spaceCityname;
        }

        public void setSpaceCityname(String spaceCityname) {
            this.spaceCityname = spaceCityname;
        }

        public String getSpaceCinemaname() {
            return spaceCinemaname;
        }

        public void setSpaceCinemaname(String spaceCinemaname) {
            this.spaceCinemaname = spaceCinemaname;
        }

        public String getSpaceAddress() {
            return spaceAddress;
        }

        public void setSpaceAddress(String spaceAddress) {
            this.spaceAddress = spaceAddress;
        }

        public String getSpaceTelephone() {
            return spaceTelephone;
        }

        public void setSpaceTelephone(String spaceTelephone) {
            this.spaceTelephone = spaceTelephone;
        }

        public String getSpaceLatitude() {
            return spaceLatitude;
        }

        public void setSpaceLatitude(String spaceLatitude) {
            this.spaceLatitude = spaceLatitude;
        }

        public String getSpaceLongitude() {
            return spaceLongitude;
        }

        public void setSpaceLongitude(String spaceLongitude) {
            this.spaceLongitude = spaceLongitude;
        }

        public String getSpaceTrafficroutes() {
            return spaceTrafficroutes;
        }

        public void setSpaceTrafficroutes(String spaceTrafficroutes) {
            this.spaceTrafficroutes = spaceTrafficroutes;
        }

        public Double getSpaceDistance() {
            return spaceDistance;
        }

        public void setSpaceDistance(Double spaceDistance) {
            this.spaceDistance = spaceDistance;
        }

        public String getSpaceCreate() {
            return spaceCreate;
        }

        public void setSpaceCreate(String spaceCreate) {
            this.spaceCreate = spaceCreate;
        }
        @Override
        public int compareTo(@NonNull Object o) {
            if (this.getSpaceDistance() - ((Data) o).getSpaceDistance()>0){
                return 1;
            }else if(this.getSpaceDistance() - ((Data) o).getSpaceDistance()<0){
                return -1;
            }else {
                return 0;
            }
        }

    }
}
