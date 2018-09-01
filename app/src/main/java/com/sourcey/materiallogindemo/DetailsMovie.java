package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsMovie extends AppCompatActivity {

    public static String BASE_URL="https://api.themoviedb.org/3/";
    public static String API_KEY="2d3e3995bfdaf5c190e7abac26e49040";

    public static int increment=0;

    private Retrofit retrofit;


    private ImageView moviePoster;
    private TextView  movieName,movieRatings,movieDesc,movieGenre,movieReleaseDate,movieVideoLength;

    private Button movieSaveBtn;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        moviePoster=findViewById(R.id.detail_moviePosterId);
        movieName=findViewById(R.id.detail_movieNameId);
        movieDesc=findViewById(R.id.detail_descId);
        movieRatings=findViewById(R.id.detail_ratingId);
        movieGenre=findViewById(R.id.detail_genreId);
        movieReleaseDate=findViewById(R.id.detail_release_dateId);
        movieVideoLength=findViewById(R.id.details_movieLengthId);
        movieSaveBtn=findViewById(R.id.detail_savebtnId);


        firebaseFirestore=FirebaseFirestore.getInstance();

        Intent intent=getIntent();

        int movieId=Integer.parseInt(intent.getExtras().getString("movie_id"));

        final String[] title = new String[1];
        final String[] posterPath = new String[1];
        final String[] ratings = new String[1];
        final String[] desc = new String[1];
        final String[] releaseDate = new String[1];
        final String[] movieLength = new String[1];

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TmdbApi tmdbApi=retrofit.create(TmdbApi.class);

        Call<MovieDetailsResponse>  movieDetailsResponseCall=tmdbApi.getMovieDetails(movieId,API_KEY);

        movieDetailsResponseCall.enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {


              MovieDetailsResponse movieDetailsResponse= response.body();

            title[0] =movieDetailsResponse.getOriginal_title();
            posterPath[0] =movieDetailsResponse.getPoster_path();
            ratings[0] =String.valueOf(movieDetailsResponse.getVote_average());
            desc[0] =movieDetailsResponse.getOverview();
            releaseDate[0] =movieDetailsResponse.getRelease_date();
            movieLength[0] =String.valueOf(movieDetailsResponse.getRuntime());

            List<?> genres=movieDetailsResponse.getGenres();

            List<String> genre=new ArrayList<>();

            //just taking the only one genre from every movie
            Map<?,?> genreMap= (Map<?, ?>) genres.get(0);
            final String gen=String.valueOf(genreMap.get("name"));

            /*
            for(int i=0;i<genres.size();i++){

              Map<?,?> genreMap= (Map<?, ?>) genres.get(i);
              String gen=String.valueOf(genreMap.get("name"));
              genre.add(gen);

            }
            */

             // final String gen1= Arrays.toString(genre.toArray());


             Glide.with(DetailsMovie.this).load("https://image.tmdb.org/t/p/w500/"+ posterPath[0]).into(moviePoster);
             movieName.setText(title[0]);
             movieDesc.setText(desc[0]);
             movieRatings.setText("Rating: "+ratings[0]);
             movieGenre.setText("Genre: "+gen);
             movieReleaseDate.setText("Release Date: "+releaseDate[0]);
             movieVideoLength.setText("Movie Length: "+movieLength[0]);


             movieSaveBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     String userId= FirebaseAuth.getInstance().getUid();

                     Map<String ,Object > movieMap = new HashMap<String, Object>();

                     movieMap.put("path", posterPath[0]);
                     movieMap.put("name", title[0]);
                     movieMap.put("desc", desc[0]);
                     movieMap.put("rating", ratings[0]);
                     movieMap.put("release_date", releaseDate[0]);
                     movieMap.put("genre",gen);
                     movieMap.put("movie_length", movieLength[0]);


                     increment++;

                     firebaseFirestore.collection("MovieDetails/"+userId+"/Movie").document(String.valueOf(increment)).set(movieMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {

                             if(task.isSuccessful()){

                                   startActivity(new Intent(DetailsMovie.this,ProfileMain.class));
                                   finish();
                             }
                         }
                     });

                 }
             });



            }
            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {

                Toast.makeText(DetailsMovie.this, "Failed to save the Data!!!",Toast.LENGTH_SHORT).show();

            }
        });

    }
}
