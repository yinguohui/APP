package com.example.ygh.app.bean;

import java.util.List;

public class PostsListBean {

    private String message;
    private int code;
    private Info data;
    private String status;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Info getInfo() {
        return data;
    }

    public void setInfo(Info info) {
        this.data = info;
    }

    public static class Info{
        private int allNum;
        private int allPages;
        private int currentPage;
        private List<PostList> postList;

        public int getAllNum() {
            return allNum;
        }

        public void setAllNum(int allNum) {
            this.allNum = allNum;
        }

        public int getAllPages() {
            return allPages;
        }

        public void setAllPages(int allPages) {
            this.allPages = allPages;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public List<PostList> getPostList() {
            return postList;
        }

        public void setPostList(List<PostList> postList) {
            this.postList = postList;
        }

        public  class PostList{
            private String user_name;
            private String user_img;

            private String title_create_time;
            private String title_content;
            private String title_id;
            private String title_love;
            private String title_hate;
            private String title_comment;
            private String title_forword;
            private int title_status;
            private String title_img;

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_img() {
                return user_img;
            }

            public void setUser_img(String user_img) {
                this.user_img = user_img;
            }

            public String getTitle_create_time() {
                return title_create_time;
            }

            public void setTitle_create_time(String title_create_time) {
                this.title_create_time = title_create_time;
            }

            public String getTitle_content() {
                return title_content;
            }

            public void setTitle_content(String title_content) {
                this.title_content = title_content;
            }

            public String getTitle_id() {
                return title_id;
            }

            public void setTitle_id(String title_id) {
                this.title_id = title_id;
            }

            public String getTitle_love() {
                return title_love;
            }

            public void setTitle_love(String title_love) {
                this.title_love = title_love;
            }

            public String getTitle_hate() {
                return title_hate;
            }

            public void setTitle_hate(String title_hate) {
                this.title_hate = title_hate;
            }

            public String getTitle_comment() {
                return title_comment;
            }

            public void setTitle_comment(String title_comment) {
                this.title_comment = title_comment;
            }

            public String getTitle_forword() {
                return title_forword;
            }

            public void setTitle_forword(String title_forword) {
                this.title_forword = title_forword;
            }

            public int getTitle_status() {
                return title_status;
            }

            public void setTitle_status(int title_status) {
                this.title_status = title_status;
            }

            public String getTitle_img() {
                return title_img;
            }

            public void setTitle_img(String title_img) {
                this.title_img = title_img;
            }
        }
    }



}
