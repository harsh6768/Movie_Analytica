package com.sourcey.materiallogindemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileMain extends AppCompatActivity {

    private static final int GALLERY =4;

    private Toolbar myToolbar;
     private FirebaseAuth mAuth;

     private CircleImageView setProfileImage;

     private ImageView viewProfile;

     private Uri imageUri;

    private ImageView profileRemindBtn;

    private TextView  cataLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        myToolbar=findViewById(R.id.profile_toolbarId);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setProfileImage=findViewById(R.id.profile_imageId);
        profileRemindBtn=findViewById(R.id.remindBtn);
        cataLog=findViewById(R.id.profile_catlogId);

        viewProfile=findViewById(R.id.view_profileId);

        mAuth=FirebaseAuth.getInstance();

        //for catalog
        cataLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   startActivity(new Intent(ProfileMain.this,Catalog.class));

            }
        });

        //for reminder
        profileRemindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileMain.this,MyReminder.class));

            }
        });

        //for seeing the profile info
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileMain.this,ProfileDetails.class));

            }
        });


        setProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //check if permission granted or not
                    if (ContextCompat.checkSelfPermission(ProfileMain.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(ProfileMain.this, "Permission Denied!!!", Toast.LENGTH_LONG).show();

                        ActivityCompat.requestPermissions(ProfileMain.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {
                        //if permission granted then we need to set the image
                        setProfileImage();
                    }
                } else {

                    //android version is less than 6.0
                    setProfileImage();

                }
            }
        });
    }

    //set profileImage
    private void setProfileImage() {
        Intent setImage=new Intent(Intent.ACTION_PICK);
        setImage.setType("image/*");
        startActivityForResult(setImage,GALLERY);

    }

    //set the image to the circleImageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY){

            Uri imageUri = data.getData();
            setProfileImage.setImageURI(imageUri);
            //image is changed so weed to changed the boolean value
            //profileImageChanged=true;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profile_menu,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.profile_logoutId:
                onLogout();
                return true;
                     default:
                         return false;
        }
    }

    private void onLogout() {

        mAuth.signOut();
        if(mAuth.getCurrentUser()==null){

            startActivity(new Intent(ProfileMain.this,LoginActivity.class));
            finish();

        }

    }

    //
    public void onAddMovies(View view){
        startActivity(new Intent(ProfileMain.this,AddMovies.class));

    }



}
