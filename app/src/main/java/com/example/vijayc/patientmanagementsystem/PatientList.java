package com.example.vijayc.patientmanagementsystem;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vijayc.patientmanagementsystem.data.LoginDataBaseAdapter;

public class PatientList extends Activity {
    LoginDataBaseAdapter loginDataBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.patientlist_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.addpatient:

                final Dialog dialog = new Dialog(PatientList.this);

                dialog.setContentView(R.layout.login);
                dialog.setTitle("Login");

                // get the Refferences of views
                final EditText editTextUserName = (EditText) dialog.findViewById(R.id.editTextUserNameToLogin);
                final EditText editTextPassword = (EditText) dialog.findViewById(R.id.editTextPasswordToLogin);

                Button btnSignIn = (Button) dialog.findViewById(R.id.buttonSignIn);
                // create the instance of Databse
                loginDataBaseAdapter = new LoginDataBaseAdapter(this);
                loginDataBaseAdapter = loginDataBaseAdapter.open();

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
                            Toast.makeText(PatientList.this, "Login Successfull", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            Intent intentSignUP = new Intent(getApplicationContext(), PatientList.class);
                            startActivity(intentSignUP);
                        } else {

                            Toast.makeText(PatientList.this, "User Name and Does Not Matches", Toast.LENGTH_LONG).show();
                        }

                    }
                });


                dialog.show();


                Toast.makeText(getApplicationContext(),"added record",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
