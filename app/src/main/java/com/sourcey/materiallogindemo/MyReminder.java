package com.sourcey.materiallogindemo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyReminder extends AppCompatActivity {
ImageView imageView,imageView2,setBtn;
EditText movieName;
    String myhr,mymin;
    String myAlarmDate,b;
    SimpleDateFormat date,mynottime;
    String thisdate,thistime;
    Calendar calendar = null;
    Calendar mycalendar;
    TextView dateBox,timebox;
    private static MyReminder inst;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;


    public static MyReminder instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reminder);
        imageView=findViewById(R.id.imageView);
        imageView2=findViewById(R.id.imageView2);
        setBtn=findViewById(R.id.setBtn);
        dateBox=findViewById(R.id.dateBox);
        timebox=findViewById(R.id.timeBox);
        movieName=findViewById(R.id.movieName);
        Intent myIntent = new Intent(MyReminder.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, myIntent, 0);


        mycalendar=Calendar.getInstance();
        final int mYear=mycalendar.get(Calendar.YEAR);
        final int mMonth=mycalendar.get(Calendar.MONTH);
        final int mDay=mycalendar.get(Calendar.DAY_OF_MONTH);


        //imageView.setVisibility(View.INVISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mydialog= new DatePickerDialog(MyReminder.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String month2=((month+1)<10)? "0" + (month+1):String.valueOf(month+1);
                        String day2=(day<10)? "0" + day:String.valueOf(day);
                        //dateBox.setText(String.valueOf(year) + "-" + month2 + "-" + day2 );
                        dateBox.setText(day2+"-"+month2+"-"+String.valueOf(year));
                        myAlarmDate=day2+"/"+month2+"/"+year;

                    }
                }, mYear, mMonth, mDay);
              //  mydialog.getDatePicker().setMaxDate(new java.util.Date().getTime());
                mydialog.show();
            }
        });


        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(MyReminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int HOUR_OF_DAY, int minute) {
                        int hour=HOUR_OF_DAY%12;
                        if(hour==0)
                        {
                            hour=12;
                        }
                        String a=String.format("%02d:%02d%s",hour,minute,HOUR_OF_DAY<12?"am":"pm");
                        //b=String.format("%02d:%02d%s",hour,minute);
                        myhr=String.format("%02d",HOUR_OF_DAY);
                        mymin=String.format("%02d",minute);
                        timebox.setText(a);

                        //#########################################3
                        int myhour=Integer.parseInt(myhr),myminute=Integer.parseInt(mymin);
                        calendar.set(Calendar.HOUR_OF_DAY, myhour);
                        calendar.set(Calendar.MINUTE, myminute);
                        //calendar.set(Calendar.SECOND,0);
                    }
                }, mycalendar.get(Calendar.HOUR_OF_DAY), mycalendar.get(Calendar.MINUTE), false).show();
            }
        });

        calendar = Calendar.getInstance();
        Date d = new Date();
        date = new SimpleDateFormat("dd/MM/yyyy");
       // mynottime=new SimpleDateFormat("hh:mm a");
        thisdate=date.format(d);
        //thistime=mynottime.format(d);
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(thisdate.equalsIgnoreCase(myAlarmDate) ) {

                        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0, AlarmManager.INTERVAL_DAY,
                                pendingIntent);
                        //startActivity(new Intent(getBaseContext(),AlarmService.class).putExtra("moviename",movieName.getText().toString()));

                    Toast.makeText(MyReminder.this, "Reminder Set!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MyReminder.this, ""+thisdate+"\t"+myAlarmDate, Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    public void setAlarmText(String alarmText) {
        //alarmTextView.setText(alarmText);
        // Toast.makeText(inst, ""+alarmText, Toast.LENGTH_SHORT).show();
    }
}
