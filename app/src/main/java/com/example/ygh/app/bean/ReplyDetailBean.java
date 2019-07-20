package com.example.ygh.app.bean;

public class ReplyDetailBean {

//    "nickName": "沐風",
//    "userLogo": "http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png",
//    "id": 40,
//    "commentId": "42",
//    "conten": "时间总是在不经意中擦肩而过,不留一点痕迹.",
//    "status": "01",
//    "createDate": "一个小时前"
    private String rereviewId;
    private String rereviewContent;
    private int rereviewStatus;
    private String rereviewCreate;
    private String reviewId;


    private String userName;
    private String userImg;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public ReplyDetailBean(String userName, String rereviewContent) {
        this.userName = userName;
        this.rereviewContent = rereviewContent;
    }

    public String getRereviewId() {
        return rereviewId;
    }

    public void setRereviewId(String rereviewId) {
        this.rereviewId = rereviewId;
    }

    public String getRereviewContent() {
        return rereviewContent;
    }

    public void setRereviewContent(String rereviewContent) {
        this.rereviewContent = rereviewContent;
    }

    public int getRereviewStatus() {
        return rereviewStatus;
    }

    public void setRereviewStatus(int rereviewStatus) {
        this.rereviewStatus = rereviewStatus;
    }

    public String getRereviewCreate() {
        return rereviewCreate;
    }

    public void setRereviewCreate(String rereviewCreate) {
        this.rereviewCreate = rereviewCreate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}