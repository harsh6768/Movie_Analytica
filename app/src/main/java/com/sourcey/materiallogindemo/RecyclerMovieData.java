package com.sourcey.materiallogindemo;

public class RecyclerMovieData {

    String movieName;
    String moviePath;
    String movieRating;
    String movieDesc;
    String releaseDate;


    public RecyclerMovieData(String movieName, String moviePath,String movieRating,String movieDesc,String releaseDate) {
        this.movieName = movieName;
        this.moviePath = moviePath;
        this.movieRating=movieRating;
        this.movieDesc=movieDesc;
        this.releaseDate=releaseDate;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieDesc() {
        return movieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMoviePath() {
        return moviePath;
    }

    public void setMoviePath(String moviePath) {
        this.moviePath = moviePath;
    }
}
