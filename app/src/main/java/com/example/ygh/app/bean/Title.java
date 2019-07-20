package com.example.ygh.app.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * title
 * @author
 */
public class Title implements Serializable {
    private String title_id;

    private String title_content;

    private String title_headname;

    private String title_userId;

    private int title_status;

    private String title_createtime;

    private int title_love;

    private int title_hate;

    private int title_forword;

    private int title_comment;

    private String title_img;

    private static final long serialVersionUID = 1L;

    public String getTitle_id() {
        return title_id;
    }

    public void setTitle_id(String title_id) {
        this.title_id = title_id;
    }

    public String getTitle_content() {
        return title_content;
    }

    public void setTitle_content(String title_content) {
        this.title_content = title_content;
    }

    public String getTitle_headname() {
        return title_headname;
    }

    public void setTitle_headname(String title_headname) {
        this.title_headname = title_headname;
    }

    public String getTitle_userId() {
        return title_userId;
    }

    public void setTitle_userId(String title_userId) {
        this.title_userId = title_userId;
    }

    public int getTitle_status() {
        return title_status;
    }

    public void setTitle_status(int title_status) {
        this.title_status = title_status;
    }

    public String getTitle_createtime() {
        return title_createtime;
    }

    public void setTitle_createtime(String title_createtime) {
        this.title_createtime = title_createtime;
    }

    public int getTitle_love() {
        return title_love;
    }

    public void setTitle_love(int title_love) {
        this.title_love = title_love;
    }

    public int getTitle_hate() {
        return title_hate;
    }

    public void setTitle_hate(int title_hate) {
        this.title_hate = title_hate;
    }

    public int getTitle_foword() {
        return title_forword;
    }

    public void setTitle_foword(int title_foword) {
        this.title_forword = title_foword;
    }

    public int getTitle_comment() {
        return title_comment;
    }

    public void setTitle_comment(int title_comment) {
        this.title_comment = title_comment;
    }

    public String getTitle_img() {
        return title_img;
    }

    public void setTitle_img(String title_img) {
        this.title_img = title_img;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}