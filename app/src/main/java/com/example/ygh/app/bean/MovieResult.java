package com.example.ygh.app.bean;

import java.util.List;

public class MovieResult {
    private int code;
    private String status;
    private String message;
    private List<Movie> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public List<Movie> getData() {
        return data;
    }

    public void setData(List<Movie> data) {
        this.data = data;
    }

    public class Movie{
        private String movieId;
        private String movieName;
        private String movieDescription;
        private String movieActor;
        private String movieImg;
        private String movieCreate;
        private Integer movieStatus;
        private String movieUpdate;
        private String movieScore;

        public String getMovieScore() {
            return movieScore;
        }

        public void setMovieScore(String movieScore) {
            this.movieScore = movieScore;
        }

        public String getMovieId() {
            return movieId;
        }

        public void setMovieId(String movieId) {
            this.movieId = movieId;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public String getMovieDescription() {
            return movieDescription;
        }

        public void setMovieDescription(String movieDescription) {
            this.movieDescription = movieDescription;
        }

        public String getMovieActor() {
            return movieActor;
        }

        public void setMovieActor(String movieActor) {
            this.movieActor = movieActor;
        }

        public String getMovieImg() {
            return movieImg;
        }

        public void setMovieImg(String movieImg) {
            this.movieImg = movieImg;
        }

        public String getMovieCreate() {
            return movieCreate;
        }

        public void setMovieCreate(String movieCreate) {
            this.movieCreate = movieCreate;
        }

        public Integer getMovieStatus() {
            return movieStatus;
        }

        public void setMovieStatus(Integer movieStatus) {
            this.movieStatus = movieStatus;
        }

        public String getMovieUpdate() {
            return movieUpdate;
        }

        public void setMovieUpdate(String movieUpdate) {
            this.movieUpdate = movieUpdate;
        }
    }
}