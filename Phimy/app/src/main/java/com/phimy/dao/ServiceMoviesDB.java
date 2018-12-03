package com.phimy.dao;

import com.phimy.model.MovieDBContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceMoviesDB {
    @GET("movie/popular")
    Call<MovieDBContainer> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MovieDBContainer> getNowPlaying(@Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<MovieDBContainer> getPopularTv(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Call<MovieDBContainer> getCredits(@Path("movie_id") String movieID, @Query("api_key") String apiKey);
}
