package com.example.ygh.app.bean;

import java.util.List;

public class SellInfo {
    private int code;
    private String result;
    private List<Info> info;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setResult(String result) {
        this.result = result;
    }
    public String getResult() {
        return result;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }
    public List<Info> getInfo() {
        return info;
    }

    public static class Info {

        private String cinemaName;
        private String hallName;
        private String hallId;
        private String movieName;
        private String showTime;
        private String tp;
        private String lang;
        private int price;
        private String cinemaId;
        private String id;
        private String movieId;
        private String showId;
        private String showDate;
        private String paydata;
        private String movieAddress;
        public void setCinemaName(String cinemaName) {
            this.cinemaName = cinemaName;
        }
        public String getCinemaName() {
            return cinemaName;
        }

        public void setHallName(String hallName) {
            this.hallName = hallName;
        }
        public String getHallName() {
            return hallName;
        }

        public void setHallId(String hallId) {
            this.hallId = hallId;
        }
        public String getHallId() {
            return hallId;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }
        public String getMovieName() {
            return movieName;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }
        public String getShowTime() {
            return showTime;
        }

        public void setTp(String tp) {
            this.tp = tp;
        }
        public String getTp() {
            return tp;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }
        public String getLang() {
            return lang;
        }

        public void setPrice(int price) {
            this.price = price;
        }
        public int getPrice() {
            return price;
        }

        public void setCinemaId(String cinemaId) {
            this.cinemaId = cinemaId;
        }
        public String getCinemaId() {
            return cinemaId;
        }

        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setMovieId(String movieId) {
            this.movieId = movieId;
        }
        public String getMovieId() {
            return movieId;
        }

        public void setShowId(String showId) {
            this.showId = showId;
        }
        public String getShowId() {
            return showId;
        }

        public void setShowDate(String showDate) {
            this.showDate = showDate;
        }
        public String getShowDate() {
            return showDate;
        }

        public void setPaydata(String paydata) {
            this.paydata = paydata;
        }
        public String getPaydata() {
            return paydata;
        }

        public String getMovieAddress() {
            return movieAddress;
        }

        public void setMovieAddress(String movieAddress) {
            this.movieAddress = movieAddress;
        }
    }
}
