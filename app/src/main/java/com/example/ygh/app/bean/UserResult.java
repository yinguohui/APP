package com.example.ygh.app.bean;

import java.util.List;

public class UserResult {
    private int code;
    private String message;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private UserInfoBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public UserInfoBean getList() {
        return data;
    }

    public void setList(UserInfoBean list) {
        this.data = list;
    }

    public static class UserInfoBean{
        private String userId;

        private String userName;

        private String userPassword;

        private String userDescription;

        private String userTel;

        private String userImg;

        private String userSex;

        private Integer userScore;

        private String userMoney;

        private String userSign;

        private Integer userRole;

        private Byte userStatus;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getUserDescription() {
            return userDescription;
        }

        public void setUserDescription(String userDescription) {
            this.userDescription = userDescription;
        }

        public String getUserTel() {
            return userTel;
        }

        public void setUserTel(String userTel) {
            this.userTel = userTel;
        }
        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getUserSex() {
            return userSex;
        }

        public void setUserSex(String userSex) {
            this.userSex = userSex;
        }

        public Integer getUserScore() {
            return userScore;
        }

        public void setUserScore(Integer userScore) {
            this.userScore = userScore;
        }

        public String getUserMoney() {
            return userMoney;
        }

        public void setUserMoney(String userMoney) {
            this.userMoney = userMoney;
        }

        public String getUserSign() {
            return userSign;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public Integer getUserRole() {
            return userRole;
        }

        public void setUserRole(Integer userRole) {
            this.userRole = userRole;
        }

        public Byte getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(Byte userStatus) {
            this.userStatus = userStatus;
        }
    }

}
