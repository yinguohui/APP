package com.example.ygh.app.bean;

import java.util.List;

public class CommentDetailBean {
//   String  "id": 42, 评论id
//   String  "titleid",主题id
//   String "nickName": "程序猿", 评论人昵称
//   String "userLogo": "http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png", 图片地址
//   String "conten": "时间是一切财富中最宝贵的财富。", 评论内容
//   String "imgId": "xcclsscrt0tev11ok364", 评论图片id
//   int "replyTotal": 1, 回复总数
//   String "createDate": "三分钟前", 评论时间
//   List<ReplyDetailBean> "replyList":回复列表


    public CommentDetailBean(String userName, String reviewContent, String reviewCreate,String userImg) {
        this.userName = userName;
        this.reviewContent = reviewContent;
        this.reviewCreate = reviewCreate;
        this.userImg = userImg;
    }
    private String userName;
    private String userImg;

    private String reviewId;
    private String reviewContent;
    private String reviewCreate;
    private String reviewStatus;
    private String reviewTitleId;
    private int replyTotal;
    private List<ReplyDetailBean> replyList;

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

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewCreate() {
        return reviewCreate;
    }

    public void setReviewCreate(String reviewCreate) {
        this.reviewCreate = reviewCreate;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getReviewTitleId() {
        return reviewTitleId;
    }

    public void setReviewTitleId(String reviewTitleId) {
        this.reviewTitleId = reviewTitleId;
    }

    public int getReplyTotal() {
        return replyTotal;
    }

    public void setReplyTotal(int replyTotal) {
        this.replyTotal = replyTotal;
    }

    public List<ReplyDetailBean> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyDetailBean> replyList) {
        this.replyList = replyList;
    }
}