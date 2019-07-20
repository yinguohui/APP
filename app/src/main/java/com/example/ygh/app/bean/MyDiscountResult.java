package com.example.ygh.app.bean;

import java.util.List;

public class MyDiscountResult {
    private int code;
    private String message;
    private String status;

    private List<MyDiscountBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MyDiscountBean> getList() {
        return data;
    }

    public void setList(List<MyDiscountBean> list) {
        this.data = list;
    }

    public static class MyDiscountBean{
        private String dsId;
        private String discountId;

        private String discountName;

        private String discountType;

        private String discountStart;

        private String discountEnd;

        private Integer discountStatus;

        private String discountUserId;

        private String discountScore;

        private String discountD1;

        public String getDiscountScore() {
            return discountScore;
        }

        public void setDiscountScore(String discountScore) {
            this.discountScore = discountScore;
        }

        public String getDiscountD1() {
            return discountD1;
        }

        public void setDiscountD1(String discountD1) {
            this.discountD1 = discountD1;
        }

        public String getDiscountId() {
            return discountId;
        }

        public void setDiscountId(String discountId) {
            this.discountId = discountId;
        }

        public String getDiscountName() {
            return discountName;
        }

        public void setDiscountName(String discountName) {
            this.discountName = discountName;
        }

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public String getDiscountStart() {
            return discountStart;
        }

        public void setDiscountStart(String discountStart) {
            this.discountStart = discountStart;
        }

        public String getDiscountEnd() {
            return discountEnd;
        }

        public void setDiscountEnd(String discountEnd) {
            this.discountEnd = discountEnd;
        }

        public Integer getDiscountStatus() {
            return discountStatus;
        }

        public void setDiscountStatus(Integer discountStatus) {
            this.discountStatus = discountStatus;
        }

        public String getDiscountUserId() {
            return discountUserId;
        }

        public void setDiscountUserId(String discountUserId) {
            this.discountUserId = discountUserId;
        }

        public String getDsId() {
            return dsId;
        }

        public void setDsId(String dsId) {
            this.dsId = dsId;
        }
    }
}
