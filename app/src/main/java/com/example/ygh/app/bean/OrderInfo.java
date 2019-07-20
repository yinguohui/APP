package com.example.ygh.app.bean;

public class OrderInfo {
    private String orderId;
    private String orderLsh;
    private String orderUserId;
    private Integer orderStatus;
    private String orderCreat;
    private String orderMoney;
    private String orderMhId;
    private String orderSeatId;
    private String orderOr1;
    private String orderOr2;
    //电影院
    private String spaceCityname;
    private String spaceCinemaname;
    private String spaceAddress;
    //大厅
    private String hallName;
    //电影
    private String movieName;
    //上映时间
    private String mhTime;
    private String mhStart;
    private String mhEnd;
    //座位信息
    private String seatInfo;

    public String getMhStart() {
        return mhStart;
    }

    public void setMhStart(String mhStart) {
        this.mhStart = mhStart;
    }

    public String getMhEnd() {
        return mhEnd;
    }

    public void setMhEnd(String mhEnd) {
        this.mhEnd = mhEnd;
    }

    public String getSeatInfo() {
        return seatInfo;
    }

    public void setSeatInfo(String seatInfo) {
        this.seatInfo = seatInfo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderLsh() {
        return orderLsh;
    }

    public void setOrderLsh(String orderLsh) {
        this.orderLsh = orderLsh;
    }

    public String getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(String orderUserId) {
        this.orderUserId = orderUserId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderCreat() {
        return orderCreat;
    }

    public void setOrderCreat(String orderCreat) {
        this.orderCreat = orderCreat;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderMhId() {
        return orderMhId;
    }

    public void setOrderMhId(String orderMhId) {
        this.orderMhId = orderMhId;
    }

    public String getOrderSeatId() {
        return orderSeatId;
    }

    public void setOrderSeatId(String orderSeatId) {
        this.orderSeatId = orderSeatId;
    }

    public String getOrderOr1() {
        return orderOr1;
    }

    public void setOrderOr1(String orderOr1) {
        this.orderOr1 = orderOr1;
    }

    public String getOrderOr2() {
        return orderOr2;
    }

    public void setOrderOr2(String orderOr2) {
        this.orderOr2 = orderOr2;
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

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMhTime() {
        return mhTime;
    }

    public void setMhTime(String mhTime) {
        this.mhTime = mhTime;
    }

}