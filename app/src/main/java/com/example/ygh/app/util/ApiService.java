package com.example.ygh.app.util;

import com.example.ygh.app.bean.MovieDetailInfo;
import com.example.ygh.app.bean.MovieInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/23 0023.
 */

public interface ApiService {
    /*
   * 电影影院
   * http://m.maoyan.com/cinemas.json
   */
//    @GET("cinemas.json")
//    Observable<Cinema_info> getCinemas();

    /*
    * 获取电影列表
    * http://m.maoyan.com/movie/list.json?type=hot&offset=0&limit=1000
    */
    @GET("movie/list.json")
    Observable<MovieInfo> getMovie(@Query("type") String type, @Query("offset") int offset, @Query("limit") int limit);

    @GET("movie/{movieId}")
    Observable<MovieDetailInfo> getMovieDetail(@Path("movieId") String movieId);
}
