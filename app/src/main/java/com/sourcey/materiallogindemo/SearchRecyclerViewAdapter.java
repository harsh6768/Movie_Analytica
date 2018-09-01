package com.sourcey.materiallogindemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<RecyclerMovieData> recyclerMovieDataList;
    private String moviePath;
    private String movieName;
    private String movieRatings;
    private String desc;
    private String releaseDate;

    private FirebaseFirestore myFireStore;

    public SearchRecyclerViewAdapter(Context context, List<RecyclerMovieData> recyclerMovieDataList) {
        this.context = context;
        this.recyclerMovieDataList = recyclerMovieDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(context).inflate(R.layout.search_movie_list,parent,false);

        myFireStore=FirebaseFirestore.getInstance();

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.onImagePoster(recyclerMovieDataList.get(position).getMoviePath());
        holder.movieName.setText(recyclerMovieDataList.get(position).getMovieName());

        moviePath=recyclerMovieDataList.get(position).getMoviePath();
        movieName=recyclerMovieDataList.get(position).getMovieName();
        movieRatings=recyclerMovieDataList.get(position).getMovieRating();
        desc=recyclerMovieDataList.get(position).getMovieDesc();
        releaseDate=recyclerMovieDataList.get(position).getReleaseDate();


        /*
        holder.myCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId= FirebaseAuth.getInstance().getUid();

                Map<String ,Object > movieMap = new HashMap<String, Object>();

                movieMap.put("path",moviePath);
                movieMap.put("name",movieName);
                movieMap.put("desc",desc);
                movieMap.put("rating",movieRatings);
                movieMap.put("release_date",releaseDate);


                myFireStore.collection("MovieDetails").document(userId).set(movieMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(context,"Data Uploaded Successfully!!!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

           */

    }

    @Override
    public int getItemCount() {
        return recyclerMovieDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView moviePoster;
        TextView movieName;
        CardView myCardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            moviePoster=itemView.findViewById(R.id.recycler_posterId);
            movieName=itemView.findViewById(R.id.recycler_nameId);
            myCardView=itemView.findViewById(R.id.recycler_cardId);

        }

        public void onImagePoster(String imagePath){

            //placeholder image
            Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+imagePath).into(moviePoster);
        }
    }

}
