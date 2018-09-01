package com.sourcey.materiallogindemo;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProfileDetails extends AppCompatActivity {

    public static String TAG="MainActivity";

    private float[] yData={25.3f,10.6f,66.76f,44.32f,46.01f,16.89f,23.9f};
    private String[] xData={"Harsh","Shubham","Balendra","Adarsh","Vaishali","Kamini","Omkar"};

    private String[] genreXData={"Action","Adventure","Animation","Comedy","Crime","Documentary","Drama","Family","Fantasy","History", "Horror",
            "Music","Mystery", "Romance","Science Fiction","TV Movie","Thriller","War","Western"};

    Map<String,Integer> movieCountMap;

    PieChart pieChart;

    FirebaseAuth mAuth;
    FirebaseUser myUser;
    FirebaseFirestore firebaseFirestore;

    private static int increment=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        mAuth=FirebaseAuth.getInstance();
        myUser=mAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();

        movieCountMap=new HashMap<String ,Integer>();

        movieCount(movieCountMap);

        Log.d(TAG,"onCreate:starting to create chart");

        pieChart=findViewById(R.id.pieChartId);

        //pieChart.setDescription("Sales by employee (In Thousands $)");
        pieChart.setRotationEnabled(true);  //so that we can rotate the pie chart
        pieChart.setHoleRadius(25f);       //radius of the center chart
        pieChart.setTransparentCircleAlpha(0);   //set the solid
        pieChart.setCenterText("Super Color Chart");
        pieChart.setCenterTextSize(10);     //textSize

        pieChart.setDrawEntryLabels(true);   // to take the permission so that  we can draw the chart

        addDataSet();        //to set the data into the Chart



        //logic for fetching the genres from the firebasefirestore


        String userId=mAuth.getUid();
        firebaseFirestore.collection("MovieDetails/"+userId+"/Movie").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                //for getting the size of the collection
                int size=task.getResult().size();

                if(!task.getResult().isEmpty()){

                   List<DocumentChange> documentChangeList=task.getResult().getDocumentChanges();

                    for(int i=0;i<documentChangeList.size();i++){

                        String genre=documentChangeList.get(i).getDocument().getString("genre");

                        counterMovie(genre);
                    }
                }
                else{
                    Toast.makeText(ProfileDetails.this,"Database don't any collection!!!!",Toast.LENGTH_LONG).show();

                }
            }
        });

        //set the value when you will try

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {


                Log.d(TAG,"onValueSelected: Value select from chart.");
                Log.d(TAG,"onValueSelected:"+e.toString());
                Log.d(TAG,"onValueSelected:"+h.toString());

                /*
                int pos1=e.toString().indexOf("(sum):");
                String sales=e.toString().substring(pos1+7);

                for(int i=0;i<yData.length;i++){
                    if(yData[i]==Float.parseFloat(sales)){
                        pos1=i;
                        break;
                    }
                }
                */
                //String employee=xData[pos1+1];
                //Toast.makeText(MainActivity.this,"Employee"+employee+"\n"+"Sales: $"+sales+"K",Toast.LENGTH_LONG).show();
                Toast.makeText(ProfileDetails.this,"Chart Clicked",Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected() {

            }
        });

    }

    //creating default map to count the genre
    private void movieCount(Map<String, Integer> movieCountMap) {

        for(int i=0;i<genreXData.length;i++){
            movieCountMap.put(genreXData[i],0);
        }

        /*
        for ( Map.Entry<String, Integer> entry : movieCountMap.entrySet()) {

            Log.i("Tag:",entry.getKey()+" "+entry.getValue());
        }
        */

    }


    //count the genres
    private void counterMovie(String genre) {

        int count = movieCountMap.containsKey(genre) ? movieCountMap.get(genre) : 0;
        movieCountMap.put(genre, count + 1);

        for ( Map.Entry<String, Integer> entry : movieCountMap.entrySet()) {

            Log.i("Tag:",entry.getKey()+" "+entry.getValue());
        }

        Set<Map.Entry<String, Integer>> mapEntries=movieCountMap.entrySet();

        List<Map.Entry<String,Integer>> aList=new LinkedList<Map.Entry<String, Integer>>(mapEntries);

        Collections.sort(aList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> ele1, Map.Entry<String, Integer> ele2) {
                return ele2.getValue().compareTo(ele1.getValue());

            }
        });

        Map<String,Integer> aMap2=new LinkedHashMap<String,Integer>();
        for(Map.Entry<String,Integer>  entry: aList){

            aMap2.put(entry.getKey(),entry.getValue());

        }


    }

    private void addDataSet() {

        Log.d(TAG,"addDataSet Started");

        ArrayList<PieEntry> pieEntries=new ArrayList<>();
        ArrayList<String> xEntrys=new ArrayList<>();

        for(int i=0;i<yData.length;i++){
            //add the
            pieEntries.add(new PieEntry(yData[i],i));      //adding the data into the PieChart Entry at related index
        }

        for(int i=1;i<xData.length;i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet=new PieDataSet(pieEntries,"Favourite Genre");
        pieDataSet.setSliceSpace(2);  //to set the padding between different charts
        pieDataSet.setValueTextSize(12);   //to set the size of the text

        //add colors to data set
        //number of colors should be the number of entries that you are going to put into the Pie chart
        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);
          /* colors.add(Color.rgb());
        colors.add(Color.rgb());
        colors.add(Color.rgb());
        colors.add(Color.rgb());
        colors.add(Color.rgb());
        colors.add(Color.rgb());
        colors.add(Color.rgb());
        colors.add(Color.rgb());
        colors.add(Color.rgb());
        colors.add(Color.rgb());
        colors.add(Color.rgb());
        colors.add(Color.rgb());

*/

        //to set the color in pieChart
        pieDataSet.setColors(colors);

        //add legend to the chart
        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);    //the chart as a circle
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);


        //create pie data object
        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }
}
