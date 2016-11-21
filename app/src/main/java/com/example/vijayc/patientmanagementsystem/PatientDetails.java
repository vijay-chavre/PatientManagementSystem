package com.example.vijayc.patientmanagementsystem;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.vijayc.patientmanagementsystem.data.LoginDataBaseAdapter;
import com.example.vijayc.patientmanagementsystem.models.PatientProvider;

public class PatientDetails extends Activity {
    PatientProvider patientProvider;
    ImageView img;
    TextView fname,mname,lname,village,mobile;
    Button allvisits,addvisits,calculatebill;
    LoginDataBaseAdapter loginDataBaseAdapter;
    Cursor cursor;
    String pid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        //get  Layout References..............................................
        img=(ImageView)findViewById(R.id.photo);
        fname=(TextView)findViewById(R.id.tfname);
        mname=(TextView)findViewById(R.id.tmname);
        lname=(TextView)findViewById(R.id.tlname);
        village=(TextView)findViewById(R.id.tvillage);
        mobile=(TextView)findViewById(R.id.tmobile);

        //get Button reference...............................................

        allvisits=(Button)findViewById(R.id.allvisits);
        addvisits=(Button)findViewById(R.id.addvisits);
        calculatebill=(Button)findViewById(R.id.calculatebill);

        //Get ID from PatientList activity..........................................
        pid = getIntent().getStringExtra("pid");


        // get Patient Details by ID.................................
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        cursor=loginDataBaseAdapter.getSinglePatient(pid);
        if(cursor==null){
            System.out.println("cursor is null");
        }
        if(cursor.moveToFirst()) {
            do{
                String fname,mname,lname,village,mobile;
                byte[] imagpath;
                int pid;
                pid =cursor.getInt(cursor.getColumnIndex("P_ID"));
                fname=cursor.getString(cursor.getColumnIndex("P_FNAME"));
                mname=cursor.getString(cursor.getColumnIndex("P_MNAME"));
                lname=cursor.getString(cursor.getColumnIndex("P_LNAME"));
                village=cursor.getString(cursor.getColumnIndex("P_VILLAGE"));
                imagpath=cursor.getBlob(cursor.getColumnIndex("P_IMAGEPATH"));
                mobile=cursor.getString(cursor.getColumnIndex("P_MOBILE"));
                patientProvider= new PatientProvider(pid,fname,mname,lname,village,mobile,imagpath);

            }while (cursor.moveToNext());
        }


        //Set Layout data with Patient Details from cursor...............
        if(patientProvider==null)
        {
            System.out.println("object is null");
        }else {

            System.out.println(patientProvider.toString());
            System.out.println(patientProvider.getFname());
            System.out.println(patientProvider.getMname());
            System.out.println(patientProvider.getLname());
            System.out.println(patientProvider.getVillage());

            fname.setText(toTitleCase(patientProvider.getFname()));
            mname.setText(toTitleCase(patientProvider.getMname()));
            lname.setText(toTitleCase(patientProvider.getLname()));
            village.setText(toTitleCase(patientProvider.getVillage()));
            mobile.setText(toTitleCase(patientProvider.getMobile()));
            Bitmap myBitmap = getImage(patientProvider.getImagepath());
            img.setImageBitmap(myBitmap);
        }


        //Method to Add visits...................................

        addvisits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),AddVisits.class);
                intent.putExtra("Pid",pid);
                startActivity(intent);

            }
        });

        //Method to get All visits...................................

        allvisits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),VisitsList.class);
                intent.putExtra("Pid",pid);
                startActivity(intent);

            }
        });
    }



    // Convert the bytearray TO BITMAP format
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    // metho to capitalise each word

    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
