package com.example.vijayc.patientmanagementsystem;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vijayc.patientmanagementsystem.data.LoginDataBaseAdapter;
import com.example.vijayc.patientmanagementsystem.data.PatientListDataAdapter;
import com.example.vijayc.patientmanagementsystem.data.VisitsListAdapter;
import com.example.vijayc.patientmanagementsystem.models.PatientProvider;
import com.example.vijayc.patientmanagementsystem.models.VisitsProvider;

public class VisitsList extends Activity {
    LoginDataBaseAdapter loginDataBaseAdapter;
    Cursor cursor;
    ListView listView;
    VisitsListAdapter visitsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visits_list);

        String pid=getIntent().getStringExtra("Pid");

        listView = (ListView) findViewById(R.id.visitslistt);
        visitsListAdapter =new VisitsListAdapter(getApplicationContext(),R.layout.visit_list_item);

       listView.setAdapter(visitsListAdapter);

        //get the cursor
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        cursor = loginDataBaseAdapter.getAllVisits(pid);
        if(cursor==null){
            System.out.println("cursor is null");
        }else {
            if (cursor.moveToFirst()) {
                System.out.println("correct here here");
                do {
                    String name, discription, date;
                    int totalamount,amountpaid;
                    int vpid ,vid;

                    vid = cursor.getInt(cursor.getColumnIndex("V_ID"));

                    vpid = cursor.getInt(cursor.getColumnIndex("VP_ID"));
                    name = cursor.getString(cursor.getColumnIndex("NAME"));
                    discription = cursor.getString(cursor.getColumnIndex("DISCRIPTION"));
                    totalamount = cursor.getInt(cursor.getColumnIndex("TOTAL_AMOUNT"));
                    amountpaid = cursor.getInt(cursor.getColumnIndex("AMOUNT_PAID"));
                    date= cursor.getString(cursor.getColumnIndex("DATE"));
                    VisitsProvider visitsProvider= new VisitsProvider(vid,vpid,name,totalamount,amountpaid,date);
                    visitsListAdapter.add(visitsProvider);


                } while (cursor.moveToNext());
            }
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "item selected is"+ parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
//                PatientProvider obj = (PatientProvider)parent.getItemAtPosition(position);
//                int pid=obj.getId();
//                Intent i =new Intent(getApplicationContext(),PatientDetails.class);
//                i.putExtra("pid",String.valueOf(pid));
//                startActivity(i);
            }
        });

    }
}
