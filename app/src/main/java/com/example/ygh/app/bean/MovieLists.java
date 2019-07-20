package com.example.ygh.app.bean;


import java.util.List;

public class MovieLists {

    private String movieId;
    private String movieName;
    private String pic_url;
    private List<Broadcast> broadcast;
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
    public String getMovieId() {
        return movieId;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getMovieName() {
        return movieName;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
    public String getPic_url() {
        return pic_url;
    }

    public void setBroadcast(List<Broadcast> broadcast) {
        this.broadcast = broadcast;
    }
    public List<Broadcast> getBroadcast() {
        return broadcast;
    }

}