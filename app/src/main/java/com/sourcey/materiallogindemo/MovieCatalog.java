package com.sourcey.materiallogindemo;

public class MovieCatalog {

    String moviePoster;
    String movieTitle;

    public MovieCatalog(String moviePoster, String movieTitle) {
        this.moviePoster = moviePoster;
        this.movieTitle = movieTitle;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
}
