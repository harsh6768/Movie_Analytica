package com.sourcey.materiallogindemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApi {

    @GET("search/movie")
    Call<MovieSearchResponse> getResultFromSearchMovie(

            @Query("api_key") String apiKey,
            @Query("query") String movie_name

    );


    @GET("movie/{movie_id}")
    Call<MovieDetailsResponse> getMovieDetails(

            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey

    );

}
