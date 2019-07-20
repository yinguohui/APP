package com.example.ygh.app.bean;

import java.util.List;

public class OrderResult {
    private String status;
    private String message;
    private Integer code;
    private List<OrderInfo> data;

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<OrderInfo> getData() {
        return data;
    }

    public void setData(List<OrderInfo> data) {
        this.data = data;
    }
}
