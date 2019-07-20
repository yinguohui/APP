package com.example.ygh.app.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/11 0011.
 */

public class SeatInfo {

    /**
     * sections : [{"rows":11,"sectionId":"01","sectionName":"普通座","columns":18,"seatRows":[{"columns":18,"rowId":"1","rowNum":1,"seats":[{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":0,"type":"E"},{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":1,"type":"E"},{"rowId":"1","rowNum":1,"columnId":"1","seatNo":"4101590102#01#01","columnNum":2,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"2","seatNo":"4101590102#01#02","columnNum":3,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"3","seatNo":"4101590102#01#03","columnNum":4,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"4","seatNo":"4101590102#01#04","columnNum":5,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"5","seatNo":"4101590102#01#05","columnNum":6,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"6","seatNo":"4101590102#01#06","columnNum":7,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"7","seatNo":"4101590102#01#07","columnNum":8,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"8","seatNo":"4101590102#01#08","columnNum":9,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"9","seatNo":"4101590102#01#09","columnNum":10,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"10","seatNo":"4101590102#01#10","columnNum":11,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"11","seatNo":"4101590102#01#11","columnNum":12,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"12","seatNo":"4101590102#01#12","columnNum":13,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":14,"type":"E"},{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":15,"type":"E"},{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":16,"type":"E"},{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":17,"type":"E"}]},{},{},{},{},{},{},{},{},{},{}]}]
     * showInfo : {"desc":"","cinemaName":"CGV星聚汇影城(大卫城店)","hallName":"2厅","hallId":"0000000000000002","movieName":"蜘蛛侠：英雄归来","showTime":"今天 9月11日11:00","buyNumLimit":5,"tp":"3D","lang":"英语","price":36,"cinemaId":14497,"seqNo":"201709110094997","movieId":334620,"showId":94997,"showDate":"2017-09-11"}
     * user :
     */

    private ShowInfo showInfo;
    private String user;
    private Sections section;

    public ShowInfo getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(ShowInfo showInfo) {
        this.showInfo = showInfo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Sections getSections() {
        return section;
    }

    public void setSections(Sections sections) {
        this.section = sections;
    }

    public static class ShowInfo {
        /**
         * desc :
         * cinemaName : CGV星聚汇影城(大卫城店)
         * hallName : 2厅
         * hallId : 0000000000000002
         * movieName : 蜘蛛侠：英雄归来
         * showTime : 今天 9月11日11:00
         * buyNumLimit : 5
         * tp : 3D
         * lang : 英语
         * price : 36
         * cinemaId : 14497
         * seqNo : 201709110094997
         * movieId : 334620
         * showId : 94997
         * showDate : 2017-09-11
         */

        private String mhId;
        private String mhHallId;
        private String mhMovieId;
        private String mhTime;
        private String mhStart;
        private String mhEnd;
        private Integer mhPrice;
        private Integer mhStatus;

        //大厅
        private String hallName;
        private Integer hallType;
        private String hallSapceId;

        //电影
        private String movieName;
        private String movieDescription;
        private String movieActor;
        private String movieImg;
        private Integer movieStatus;
        private String movieUpdate;
        private Float movieScore;

        private String spaceCinemaname;

        public String getSpaceCinemaname() {
            return spaceCinemaname;
        }

        public void setSpaceCinemaname(String spaceCinemaname) {
            this.spaceCinemaname = spaceCinemaname;
        }
        public Float getMovieScore() {
            return movieScore;
        }

        public void setMovieScore(Float movieScore) {
            this.movieScore = movieScore;
        }

        public String getMhId() {
            return mhId;
        }

        public void setMhId(String mhId) {
            this.mhId = mhId;
        }

        public String getMhHallId() {
            return mhHallId;
        }

        public void setMhHallId(String mhHallId) {
            this.mhHallId = mhHallId;
        }

        public String getMhMovieId() {
            return mhMovieId;
        }

        public void setMhMovieId(String mhMovieId) {
            this.mhMovieId = mhMovieId;
        }

        public String getMhTime() {
            return mhTime;
        }

        public void setMhTime(String mhTime) {
            this.mhTime = mhTime;
        }

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

        public Integer getMhPrice() {
            return mhPrice;
        }

        public void setMhPrice(Integer mhPrice) {
            this.mhPrice = mhPrice;
        }

        public Integer getMhStatus() {
            return mhStatus;
        }

        public void setMhStatus(Integer mhStatus) {
            this.mhStatus = mhStatus;
        }

        public String getHallName() {
            return hallName;
        }

        public void setHallName(String hallName) {
            this.hallName = hallName;
        }

        public Integer getHallType() {
            return hallType;
        }

        public void setHallType(Integer hallType) {
            this.hallType = hallType;
        }

        public String getHallSapceId() {
            return hallSapceId;
        }

        public void setHallSapceId(String hallSapceId) {
            this.hallSapceId = hallSapceId;
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

    public static class Sections {
        /**
         * rows : 11
         * sectionId : 01
         * sectionName : 普通座
         * columns : 18
         * seatRows : [{"columns":18,"rowId":"1","rowNum":1,"seats":[{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":0,"type":"E"},{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":1,"type":"E"},{"rowId":"1","rowNum":1,"columnId":"1","seatNo":"4101590102#01#01","columnNum":2,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"2","seatNo":"4101590102#01#02","columnNum":3,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"3","seatNo":"4101590102#01#03","columnNum":4,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"4","seatNo":"4101590102#01#04","columnNum":5,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"5","seatNo":"4101590102#01#05","columnNum":6,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"6","seatNo":"4101590102#01#06","columnNum":7,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"7","seatNo":"4101590102#01#07","columnNum":8,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"8","seatNo":"4101590102#01#08","columnNum":9,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"9","seatNo":"4101590102#01#09","columnNum":10,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"10","seatNo":"4101590102#01#10","columnNum":11,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"11","seatNo":"4101590102#01#11","columnNum":12,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"12","seatNo":"4101590102#01#12","columnNum":13,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":14,"type":"E"},{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":15,"type":"E"},{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":16,"type":"E"},{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":17,"type":"E"}]},{},{},{},{},{},{},{},{},{},{}]
         */

        private List<SeatRows> seatRows;
        private Sectioninfo sectioninfo;
        public void setSeatRows(List<SeatRows> seatRows) {
            this.seatRows = seatRows;
        }
        public List<SeatRows> getSeatRows() {
            return seatRows;
        }

        public void setSectioninfo(Sectioninfo sectioninfo) {
            this.sectioninfo = sectioninfo;
        }
        public Sectioninfo getSectioninfo() {
            return sectioninfo;
        }

        public static class Sectioninfo {

            private String sectionId;
            private String sectionMhId;
            private int sectionRow;
            private int sectionCol;
            private String sectionName;
            private String sectionType;
            private int sectionStatus;

            public void setSectionId(String sectionId) {
                this.sectionId = sectionId;
            }

            public String getSectionId() {
                return sectionId;
            }

            public void setSectionMhId(String sectionMhId) {
                this.sectionMhId = sectionMhId;
            }

            public String getSectionMhId() {
                return sectionMhId;
            }

            public void setSectionRow(int sectionRow) {
                this.sectionRow = sectionRow;
            }

            public int getSectionRow() {
                return sectionRow;
            }

            public void setSectionCol(int sectionCol) {
                this.sectionCol = sectionCol;
            }

            public int getSectionCol() {
                return sectionCol;
            }

            public void setSectionName(String sectionName) {
                this.sectionName = sectionName;
            }

            public String getSectionName() {
                return sectionName;
            }

            public void setSectionType(String sectionType) {
                this.sectionType = sectionType;
            }

            public String getSectionType() {
                return sectionType;
            }

            public void setSectionStatus(int sectionStatus) {
                this.sectionStatus = sectionStatus;
            }

            public int getSectionStatus() {
                return sectionStatus;
            }
        }
        public static class SeatRows {
        /**
         * columns : 18
         * rowId : 1
         * rowNum : 1
         * seats : [{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":0,"type":"E"},{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":1,"type":"E"},{"rowId":"1","rowNum":1,"columnId":"1","seatNo":"4101590102#01#01","columnNum":2,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"2","seatNo":"4101590102#01#02","columnNum":3,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"3","seatNo":"4101590102#01#03","columnNum":4,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"4","seatNo":"4101590102#01#04","columnNum":5,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"5","seatNo":"4101590102#01#05","columnNum":6,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"6","seatNo":"4101590102#01#06","columnNum":7,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"7","seatNo":"4101590102#01#07","columnNum":8,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"8","seatNo":"4101590102#01#08","columnNum":9,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"9","seatNo":"4101590102#01#09","columnNum":10,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"10","seatNo":"4101590102#01#10","columnNum":11,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"11","seatNo":"4101590102#01#11","columnNum":12,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"12","seatNo":"4101590102#01#12","columnNum":13,"type":"N"},{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":14,"type":"E"},{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":15,"type":"E"},{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":16,"type":"E"},{"rowId":"1","rowNum":1,"columnId":"","seatNo":"","columnNum":17,"type":"E"}]
         */
             private int columns;
            private int rowNum;
            private List<Seats> seats;

            public void setColumns(int columns) {
                this.columns = columns;
            }

            public int getColumns() {
                return columns;
            }

            public void setRowNum(int rowNum) {
                this.rowNum = rowNum;
            }

            public int getRowNum() {
                return rowNum;
            }

            public void setSeats(List<Seats> seats) {
                this.seats = seats;
            }

            public List<Seats> getSeats() {
                return seats;
            }
        }

        public static class Seats {
            /**
             * rowId : 1
             * rowNum : 1
             * columnId :
             * seatNo :
             * columnNum : 0
             * type : E
             */

            /**
             * Copyright 2019 bejson.com
             */

            /**
             * Copyright 2019 bejson.com
             */

            private String seatId;
            private int seatRow;
            private int seatColumn;
            private int seatStatus;
            private String seatHallId;
            private String seatType;
            public void setSeatId(String seatId) {
                this.seatId = seatId;
            }
            public String getSeatId() {
                return seatId;
            }

            public void setSeatRow(int seatRow) {
                this.seatRow = seatRow;
            }
            public int getSeatRow() {
                return seatRow;
            }

            public void setSeatColumn(int seatColumn) {
                this.seatColumn = seatColumn;
            }
            public int getSeatColumn() {
                return seatColumn;
            }

            public void setSeatStatus(int seatStatus) {
                this.seatStatus = seatStatus;
            }
            public int getSeatStatus() {
                return seatStatus;
            }

            public void setSeatHallId(String seatHallId) {
                this.seatHallId = seatHallId;
            }
            public String getSeatHallId() {
                return seatHallId;
            }

            public void setSeatType(String seatType) {
                this.seatType = seatType;
            }
            public String getSeatType() {
                return seatType;
            }
        }
    }
}

