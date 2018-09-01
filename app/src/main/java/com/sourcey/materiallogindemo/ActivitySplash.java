package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sourcey.materiallogindemo.R;

public class ActivitySplash extends AppCompatActivity {

    private static int SPLASH_TIME = 3000; //This is 4 seconds

    FirebaseUser mUser;

    @Override
    protected void onStart() {
        super.onStart();
        mUser= FirebaseAuth.getInstance().getCurrentUser();
        if(mUser!=null){
            startActivity(new Intent(ActivitySplash.this,ProfileMain.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getActionBar().hide();
       // ActionBar a=getSupportActionBar();
       // a.hide();
        setContentView(R.layout.activity_splash);

        //Code to start timer and take action after the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                Intent mySuperIntent = new Intent(ActivitySplash.this,LoginActivity.class);
                startActivity(mySuperIntent);
                /* This 'finish()' is for exiting the app when back button pressed
                *  from Home page which is ActivityHome
                */
                finish();
            }
        }, SPLASH_TIME);

    }
}
