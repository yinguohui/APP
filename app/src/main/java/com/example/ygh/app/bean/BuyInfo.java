package com.example.ygh.app.bean;

public class BuyInfo {
    private String money;
    private Order order;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public static class Order  {
        private String mh_time ;
        private String mh_start;
        private String mh_end ;
        private String mh_price;
        private String space_address ;
        private String hall_name ;
        private String movie_name ;
        private String movie_description ;
        private String space_cinemaname ;

        public String getMh_price() {
            return mh_price;
        }

        public void setMh_price(String mh_price) {
            this.mh_price = mh_price;
        }

        public void setMh_time (String mh_time ) {
            this.mh_time  = mh_time ;
        }
        public String getMh_time () {
            return mh_time ;
        }

        public void setMh_start(String mh_start) {
            this.mh_start = mh_start;
        }
        public String getMh_start() {
            return mh_start;
        }

        public void setMh_end (String mh_end ) {
            this.mh_end  = mh_end ;
        }
        public String getMh_end () {
            return mh_end ;
        }

        public void setSpace_address (String space_address ) {
            this.space_address  = space_address ;
        }
        public String getSpace_address () {
            return space_address ;
        }

        public void setHall_name (String hall_name ) {
            this.hall_name  = hall_name ;
        }
        public String getHall_name () {
            return hall_name ;
        }

        public void setMovie_name (String movie_name ) {
            this.movie_name  = movie_name ;
        }
        public String getMovie_name () {
            return movie_name ;
        }

        public void setMovie_description (String movie_description ) {
            this.movie_description  = movie_description ;
        }
        public String getMovie_description () {
            return movie_description ;
        }

        public void setSpace_cinemaname (String space_cinemaname ) {
            this.space_cinemaname  = space_cinemaname ;
        }
        public String getSpace_cinemaname () {
            return space_cinemaname ;
        }

}
}
