package com.example.vijayc.patientmanagementsystem;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vijayc.patientmanagementsystem.data.LoginDataBaseAdapter;
import com.example.vijayc.patientmanagementsystem.models.PatientProvider;

import java.util.ArrayList;

public class AddVisits extends Activity {

    EditText name,total_amount,amount_paid,discription;
    Button save_visits;
    LoginDataBaseAdapter loginDataBaseAdapter;
    String intent;
    Cursor cursor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_add_visits);
        intent =getIntent().getStringExtra("Pid");
       // Toast.makeText(this,"Pid="+intent,Toast.LENGTH_LONG).show();
        save_visits=(Button)findViewById(R.id.savevisit);
        name=(EditText)findViewById(R.id.pname);
        total_amount=(EditText)findViewById(R.id.totalamount);
        amount_paid=(EditText)findViewById(R.id.amountpaid);
        discription=(EditText)findViewById(R.id.discription);

        //Open Database connection

        loginDataBaseAdapter =new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        save_visits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String Name =name.getText().toString();
               String Total_Amount=total_amount.getText().toString();
               String Amount_Paid=amount_paid.getText().toString();
               String Discription=discription.getText().toString();

                if(Name.equals("")||Total_Amount.equals("")||Amount_Paid.equals("")||Discription.equals("")){

                    if(Name.equals("")){
                        name.setError("Enter Patient Name");
                    }
                    if(Total_Amount.equals("")){
                        total_amount.setError("Enter Total Amount");
                    }
                    if(Amount_Paid.equals("")){
                        amount_paid.setError("Enter Please Enter Paid Amount Or Enter 0");
                    }
                    if(Discription.equals("")){
                        discription.setError("Enter Discription");
                    }

                }else{

                    int pid=Integer.parseInt(intent);


                    loginDataBaseAdapter.addVisits(Discription,Name,pid,Integer.parseInt(Total_Amount),Integer.parseInt(Amount_Paid));
                  cursor = loginDataBaseAdapter.getAllVisits();


                }


            }
        });











    }
}
