package com.example.ygh.app.bean;

import java.util.Date;
import java.util.List;

public class TicketResult {
    private String status;
    private String message;
    private Integer code;
    private List<TicketInfo> data;

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

    public List<TicketInfo> getData() {
        return data;
    }

    public void setData(List<TicketInfo> data) {
        this.data = data;
    }

    public static class TicketInfo {
        private String seatId;
        private Integer seatRow;
        private Integer seatColumn;
        private Integer seatStatus;
        private String seatType;
        private String seatSt1;
        private String seatHallId;
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

        public String getSeatHallId() {
            return seatHallId;
        }

        public void setSeatHallId(String seatHallId) {
            this.seatHallId = seatHallId;
        }

        public String getSeatId() {
            return seatId;
        }

        public void setSeatId(String seatId) {
            this.seatId = seatId;
        }

        public Integer getSeatRow() {
            return seatRow;
        }

        public void setSeatRow(Integer seatRow) {
            this.seatRow = seatRow;
        }

        public Integer getSeatColumn() {
            return seatColumn;
        }

        public void setSeatColumn(Integer seatColumn) {
            this.seatColumn = seatColumn;
        }

        public Integer getSeatStatus() {
            return seatStatus;
        }

        public void setSeatStatus(Integer seatStatus) {
            this.seatStatus = seatStatus;
        }

        public String getSeatType() {
            return seatType;
        }

        public void setSeatType(String seatType) {
            this.seatType = seatType;
        }

        public String getSeatSt1() {
            return seatSt1;
        }

        public void setSeatSt1(String seatSt1) {
            this.seatSt1 = seatSt1;
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
}
