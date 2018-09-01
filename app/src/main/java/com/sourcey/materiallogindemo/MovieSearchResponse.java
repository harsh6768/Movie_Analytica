package com.sourcey.materiallogindemo;

import java.util.List;

public class MovieSearchResponse {


    /**
     * page : 1
     * total_results : 2
     * total_pages : 1
     * results : [{"vote_count":3671,"id":75780,"video":false,"vote_average":6.4,"title":"Jack Reacher","popularity":13.421,"poster_path":"/38bmEXmuJuInLs9dwfgOGCHmZ7l.jpg","original_language":"en","original_title":"Jack Reacher","genre_ids":[80,18,53],"backdrop_path":"/ezXodpP429qK0Av89pVNlaXWJkQ.jpg","adult":false,"overview":"When a gunman takes five lives with six shots, all evidence points to the suspect in custody. On interrogation, the suspect offers up a single note: \"Get Jack Reacher!\" So begins an extraordinary chase for the truth, pitting Jack Reacher against an unexpected enemy, with a skill for violence and a secret to keep.","release_date":"2012-12-20"},{"vote_count":2404,"id":343611,"video":false,"vote_average":5.5,"title":"Jack Reacher: Never Go Back","popularity":10.548,"poster_path":"/IfB9hy4JH1eH6HEfIgIGORXi5h.jpg","original_language":"en","original_title":"Jack Reacher: Never Go Back","genre_ids":[28,12,80],"backdrop_path":"/nDS8rddEK74HfAwCC5CoT6Cwzlt.jpg","adult":false,"overview":"Jack Reacher must uncover the truth behind a major government conspiracy in order to clear his name. On the run as a fugitive from the law, Reacher uncovers a potential secret from his past that could change his life forever.","release_date":"2016-10-19"}]
     */

    private int page;
    private int total_results;
    private int total_pages;
    private List<ResultsBean> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * vote_count : 3671
         * id : 75780
         * video : false
         * vote_average : 6.4
         * title : Jack Reacher
         * popularity : 13.421
         * poster_path : /38bmEXmuJuInLs9dwfgOGCHmZ7l.jpg
         * original_language : en
         * original_title : Jack Reacher
         * genre_ids : [80,18,53]
         * backdrop_path : /ezXodpP429qK0Av89pVNlaXWJkQ.jpg
         * adult : false
         * overview : When a gunman takes five lives with six shots, all evidence points to the suspect in custody. On interrogation, the suspect offers up a single note: "Get Jack Reacher!" So begins an extraordinary chase for the truth, pitting Jack Reacher against an unexpected enemy, with a skill for violence and a secret to keep.
         * release_date : 2012-12-20
         */

        private int vote_count;
        private int id;
        private boolean video;
        private double vote_average;
        private String title;
        private double popularity;
        private String poster_path;
        private String original_language;
        private String original_title;
        private String backdrop_path;
        private boolean adult;
        private String overview;
        private String release_date;
        private List<Integer> genre_ids;

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isVideo() {
            return video;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public double getVote_average() {
            return vote_average;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public boolean isAdult() {
            return adult;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        public void setGenre_ids(List<Integer> genre_ids) {
            this.genre_ids = genre_ids;
        }

    }
}
