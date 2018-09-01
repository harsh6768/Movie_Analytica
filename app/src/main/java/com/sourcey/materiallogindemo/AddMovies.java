package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddMovies extends AppCompatActivity {

    public static String BASE_URL="https://api.themoviedb.org/3/";
    public static String API_KEY="2d3e3995bfdaf5c190e7abac26e49040";

    private EditText searchText;
    private List<RecyclerMovieData> recyclerMovieDataList;
    private RecyclerView myRecyclerView ;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private Retrofit retrofit;

    private Button searchMovie;

    private FirebaseUser myUser;

    private FirebaseFirestore myFireStore;

    private Button addMovieBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movies);

        myUser= FirebaseAuth.getInstance().getCurrentUser();
        myFireStore=FirebaseFirestore.getInstance();

        addMovieBtn=findViewById(R.id.addMovieBtnId);

        searchText=findViewById(R.id.search_TextId);
        myRecyclerView=findViewById(R.id.recyclerViewId);
        searchMovie=findViewById(R.id.searchImageId);

        recyclerMovieDataList=new ArrayList<>();


        searchMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerMovieDataList.clear();

                retrofit=new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TmdbApi tmdbApi=retrofit.create(TmdbApi.class);

                Call<MovieSearchResponse>  movieSearchResponseCall= tmdbApi.getResultFromSearchMovie(API_KEY,searchText.getText().toString().trim());


                movieSearchResponseCall.enqueue(new Callback<MovieSearchResponse>() {
                    @Override
                    public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {

                        MovieSearchResponse movieSearchResponse=response.body();

                        List<MovieSearchResponse.ResultsBean> resultsBeanList=movieSearchResponse.getResults();

                        for(int i=0;i<1;i++){

                            String movieName=resultsBeanList.get(i).getOriginal_title();
                            String moviePath=resultsBeanList.get(i).getPoster_path();
                            String movieRatings=String.valueOf(resultsBeanList.get(i).getVote_average());
                            String desc=resultsBeanList.get(i).getOverview();
                            String releaseDate=resultsBeanList.get(i).getRelease_date();
                            final String movieId=String.valueOf(resultsBeanList.get(i).getId());

                            //List<Integer> genreList=resultsBeanList.get(i).getGenre_ids();


                            recyclerMovieDataList.add(new RecyclerMovieData(movieName,moviePath,movieRatings,desc,releaseDate));

                            addMovieBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent=new Intent(AddMovies.this,DetailsMovie.class);

                                    //intent.putExtra("movie_name",movieName);
                                    //intent.putExtra("movie_path",moviePath);
                                    //intent.putExtra("movie_rating",movieRatings);
                                    //intent.putExtra("desc",desc);
                                    //intent.putExtra("release_date",releaseDate);

                                    intent.putExtra("movie_id",movieId);
                                    startActivity(intent);
                                    //finish();

                                }
                            });


                        }

                    }
                    @Override
                    public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

                        Toast.makeText(AddMovies.this,"Failed to fetch the data!!!",Toast.LENGTH_LONG).show();
                        //finish();
                    }
                });

            }
        });


       searchRecyclerViewAdapter=new SearchRecyclerViewAdapter(AddMovies.this,recyclerMovieDataList);
       myRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
       myRecyclerView.setAdapter(searchRecyclerViewAdapter);
       searchRecyclerViewAdapter.notifyDataSetChanged();


    }


}
