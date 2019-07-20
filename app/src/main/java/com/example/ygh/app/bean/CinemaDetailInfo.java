package com.example.ygh.app.bean;

import java.util.List;


public class CinemaDetailInfo {

    private String status;
    private String message;
    private Result data;
    private int code;

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

    public void setResult(Result result) {
        this.data = result;
    }
    public Result getResult() {
        return data;
    }



    public static class Result {
        private CurrentMovie currentMovie;
        private CinemaDetail cinemaDetailModel;
        private List<String> dates;
        private List<Movies> movies;
        public void setCinemaDetail(CinemaDetail CinemaDetail) {
            this.cinemaDetailModel = CinemaDetail;
        }
        public CinemaDetail getCinemaDetail() {
            return cinemaDetailModel;
        }

        public void setDates(List<String> dates) {
            this.dates = dates;
        }
        public List<String> getDates() {
            return dates;
        }

        public void setMovies(List<Movies> movies) {
            this.movies = movies;
        }
        public List<Movies> getMovies() {
            return movies;
        }

        public CurrentMovie getCurrentMovie() {
            return currentMovie;
        }

        public void setCurrentMovie(CurrentMovie currentMovie) {
            this.currentMovie = currentMovie;
        }

        public static class CurrentMovie {

            private String sc;
            private String ver;
            private String isShowing;
            private String img;
            private String nm;
            private String id;
            public void setsc(String  sc) {
                this. sc =  sc;
            }
            public String getsc() {
                return  sc;
            }

            public void setVer(String ver) {
                this.ver = ver;
            }
            public String getVer() {
                return ver;
            }

            public void setIsShowing(String isShowing) {
                this.isShowing = isShowing;
            }
            public String getIsShowing() {
                return isShowing;
            }

            public void setImg(String  img) {
                this. img =  img;
            }
            public String getImg() {
                return  img;
            }

            public void setNm(String nm) {
                this.nm = nm;
            }
            public String getNm() {
                return nm;
            }

            public void setId(String  id) {
                this. id =  id;
            }
            public String getId() {
                return  id;
            }

        }

        public static class Movies {

            private String movieId;

            private String movieName;

            private String movieDescription;

            private String movieActor;

            private String movieImg;

            private String movieCreate;

            private Integer movieStatus;

            private String movieUpdate;
            private String movieScore;

            public String getMovieScore() {
                return movieScore;
            }

            public void setMovieScore(String movieScore) {
                this.movieScore = movieScore;
            }

            private static final long serialVersionUID = 1L;

            public String getMovieId() {
                return movieId;
            }

            public void setMovieId(String movieId) {
                this.movieId = movieId;
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

            public String getMovieCreate() {
                return movieCreate;
            }

            public void setMovieCreate(String movieCreate) {
                this.movieCreate = movieCreate;
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


        public class CinemaDetail {
            private String spaceId;
            private String spaceCityname;
            private String spaceCinemaname;
            private String spaceAddress;
            private String spaceTelephone;
            private String spaceLatitude;
            private String spaceLongitude;
            private String spaceTrafficroutes;
            private Double spaceDistance;
            private String spaceCreate;

            public String getSpaceId() {
                return spaceId;
            }

            public void setSpaceId(String spaceId) {
                this.spaceId = spaceId;
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

            public String getSpaceTelephone() {
                return spaceTelephone;
            }

            public void setSpaceTelephone(String spaceTelephone) {
                this.spaceTelephone = spaceTelephone;
            }

            public String getSpaceLatitude() {
                return spaceLatitude;
            }

            public void setSpaceLatitude(String spaceLatitude) {
                this.spaceLatitude = spaceLatitude;
            }

            public String getSpaceLongitude() {
                return spaceLongitude;
            }

            public void setSpaceLongitude(String spaceLongitude) {
                this.spaceLongitude = spaceLongitude;
            }

            public String getSpaceTrafficroutes() {
                return spaceTrafficroutes;
            }

            public void setSpaceTrafficroutes(String spaceTrafficroutes) {
                this.spaceTrafficroutes = spaceTrafficroutes;
            }

            public Double getSpaceDistance() {
                return spaceDistance;
            }

            public void setSpaceDistance(Double spaceDistance) {
                this.spaceDistance = spaceDistance;
            }

            public String getSpaceCreate() {
                return spaceCreate;
            }

            public void setSpaceCreate(String spaceCreate) {
                this.spaceCreate = spaceCreate;
            }
        }

    }

}