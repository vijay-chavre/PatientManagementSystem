package com.example.vijayc.patientmanagementsystem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vijayc.patientmanagementsystem.data.LoginDataBaseAdapter;

import java.util.ArrayList;

import static com.example.vijayc.patientmanagementsystem.R.layout.activity_main;

public class MainActivity extends Activity {

    Button btnSignIn, btnSignUp,btnUsers;
    LoginDataBaseAdapter loginDataBaseAdapter;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        // create the instance of Databse
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        // Get The Refference Of Buttons
        img = (ImageView)findViewById(R.id.imageView);
       // btnSignIn = (Button) findViewById(R.id.buttonSignIN);
        btnSignUp = (Button) findViewById(R.id.buttonSignUP);
        btnUsers = (Button) findViewById(R.id.buttonGetAllUser);
        // Set OnClick Listener on SignUp button
        btnSignUp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                /// Create Intent for SignUpActivity  abd Start The Activity
                Intent intentSignUP = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intentSignUP);
            }
        });


        // Set OnClick Listener on GetAlluser button
        btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignUP = new Intent(getApplicationContext(), PatientList.class);
                startActivity(intentSignUP);
            }
        });

    }


    // Methos to handleClick Event of Sign In Button
    public void signIn(View V) {

        final Dialog dialog = new Dialog(MainActivity.this);

        dialog.setContentView(R.layout.login);
        dialog.setTitle("Login");

        // get the Refferences of views
        final EditText editTextUserName = (EditText) dialog.findViewById(R.id.editTextUserNameToLogin);
        final EditText editTextPassword = (EditText) dialog.findViewById(R.id.editTextPasswordToLogin);

        Button btnSignIn = (Button) dialog.findViewById(R.id.buttonSignIn);

        // Set On ClickListener
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                // get The User name and Password
                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();

               // fetch the Password form database for respective user name
                String storedPassword = loginDataBaseAdapter.getSinlgeEntry(userName);

                // check if the Stored password matches with  Password entered by user
                if (password.equals(storedPassword)) {
                    Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    Intent intentSignUP = new Intent(getApplicationContext(), PatientList.class);
                    startActivity(intentSignUP);
                } else {

                    Toast.makeText(MainActivity.this, "User Name and Does Not Matches", Toast.LENGTH_LONG).show();
                }

            }
        });


        dialog.show();


    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        // Close The Database
        loginDataBaseAdapter.close();
    }

}