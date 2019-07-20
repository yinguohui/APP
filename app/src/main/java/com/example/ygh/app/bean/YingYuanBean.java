package com.example.ygh.app.bean;

import java.util.List;

public class YingYuanBean {

    private String reason;
    private List<Result> result;
    private int error_code;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public int getError_code() {
        return error_code;
    }
    public class Result {

        private String id;
        private String cityName;
        private String cinemaName;
        private String address;
        private String telephone;
        private String latitude;
        private String longitude;
        private String trafficRoutes;
        private int distance;
        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
        public String getCityName() {
            return cityName;
        }

        public void setCinemaName(String cinemaName) {
            this.cinemaName = cinemaName;
        }
        public String getCinemaName() {
            return cinemaName;
        }

        public void setAddress(String address) {
            this.address = address;
        }
        public String getAddress() {
            return address;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }
        public String getTelephone() {
            return telephone;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
        public String getLatitude() {
            return latitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
        public String getLongitude() {
            return longitude;
        }

        public void setTrafficRoutes(String trafficRoutes) {
            this.trafficRoutes = trafficRoutes;
        }
        public String getTrafficRoutes() {
            return trafficRoutes;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }
        public int getDistance() {
            return distance;
        }

    }
}