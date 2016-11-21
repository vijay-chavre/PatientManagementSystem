package com.example.vijayc.patientmanagementsystem;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.vijayc.patientmanagementsystem.data.LoginDataBaseAdapter;
import com.example.vijayc.patientmanagementsystem.data.PatientListDataAdapter;
import com.example.vijayc.patientmanagementsystem.models.PatientProvider;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

public class PatientList extends Activity{
    private static final int SELECT_PHOTO = 1;
    LoginDataBaseAdapter loginDataBaseAdapter;
    Cursor cursor;
    ImageView image;
    byte[] filemanagerstring;
    ListView listView;
    private SearchView mSearchView;
    PatientListDataAdapter patientListDataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listt);




        //get the cursor
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        cursor = loginDataBaseAdapter.getAllPatients();

        ArrayList<PatientProvider> pp =new ArrayList<PatientProvider>();

        if(cursor==null){
            System.out.println("cursor is null");
        }else {
            if (cursor.moveToFirst()) {
                do {
                    String fname, mname, lname, village, mobile;
                    byte[] imagpath;
                    int pid;
                    pid = cursor.getInt(cursor.getColumnIndex("P_ID"));
                    fname = cursor.getString(cursor.getColumnIndex("P_FNAME")).toLowerCase();
                    mname = cursor.getString(cursor.getColumnIndex("P_MNAME"));
                    lname = cursor.getString(cursor.getColumnIndex("P_LNAME"));
                    village = cursor.getString(cursor.getColumnIndex("P_VILLAGE"));
                    imagpath = cursor.getBlob(cursor.getColumnIndex("P_IMAGEPATH"));
                    mobile = cursor.getString(cursor.getColumnIndex("P_MOBILE"));
                    PatientProvider patientProvider = new PatientProvider(pid, fname, mname, lname, village, mobile, imagpath);
                   // patientListDataAdapter.add(patientProvider);
                    pp.add(patientProvider);

                } while (cursor.moveToNext());
            }
        }
        patientListDataAdapter =new PatientListDataAdapter(this,pp);
        listView.setAdapter(patientListDataAdapter);
       // listView.setTextFilterEnabled(true);
       // setupSearchView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getBaseContext(), "item selected is"+ parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                PatientProvider obj = (PatientProvider)parent.getItemAtPosition(position);
                int pid=obj.getId();
               Intent i =new Intent(getApplicationContext(),PatientDetails.class);
             i.putExtra("pid",String.valueOf(pid));
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.patientlist_activity, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                patientListDataAdapter.getFilter().filter(newText);
                return false;
            }
        });




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.addpatient:

                final Dialog dialog = new Dialog(PatientList.this);

                dialog.setContentView(R.layout.activity_add_patient_details);
                dialog.setTitle("AddPatientDetails");

                image = (ImageView) dialog.findViewById(R.id.image);
                final EditText fname  = (EditText) dialog.findViewById(R.id.fname);
                final EditText mname = (EditText) dialog.findViewById(R.id.mname);
                final EditText lname = (EditText) dialog.findViewById(R.id.lname);
                final EditText village= (EditText) dialog.findViewById(R.id.village);
                final EditText mobile= (EditText) dialog.findViewById(R.id.mobile);
                final Button addPatient = (Button) dialog.findViewById(R.id.addPatient);
                //Method to get image from image gallary

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // in onCreate or any event where your want the user to
                        // select a file
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                    }
                });

                // create the instance of Databse
                loginDataBaseAdapter = new LoginDataBaseAdapter(this);
                loginDataBaseAdapter = loginDataBaseAdapter.open();

                // Set On ClickListener on AddPatient BUtton on dialog box
                addPatient.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        // get The User name and Password from Dialogue Layout file
                        String Fname= fname.getText().toString();
                        String Mname= mname.getText().toString();
                        String Lname= lname.getText().toString();
                        String Village= village.getText().toString();
                        String Mobile= mobile.getText().toString();


                        //code to insert Patient in database
                        if(filemanagerstring==null || Fname.equals("") || Mname.equals("") || Lname.equals("") || Village.equals("") || Mobile.equals(""))
                        {
                            if(filemanagerstring==null){

                                Toast.makeText(getApplicationContext(),"Please Insert The Image",Toast.LENGTH_LONG).show();

                            }
                            if(Fname.equals("")){
                              //  Toast.makeText(getApplicationContext(),"Please Insert the value of Fname",Toast.LENGTH_SHORT).show();
                                fname.setError("Enter First Name");


                            }
                            if (Mname.equals("")) {

                               // Toast.makeText(getApplicationContext(),"Please Insert the value of Mname",Toast.LENGTH_SHORT).show();
                                mname.setError("Enter Middel Name");

                            }
                            if (Lname.equals("")) {

                                //Toast.makeText(getApplicationContext(),"Please Insert the value of Lname",Toast.LENGTH_SHORT).show();
                                lname.setError("Enter Last Name");

                            }
                            if (Village.equals("")) {

                                //Toast.makeText(getApplicationContext(),"Please Insert The vavue of Villag",Toast.LENGTH_SHORT).show();
                                village.setError("Enter  Village");

                            }
                            if (Mobile.equals("")) {

                                //Toast.makeText(getApplicationContext(),"Please Insert The Mobile Number",Toast.LENGTH_SHORT).show();
                                mobile.setError("Enter Mobile Number");

                            }
                        }else {
                            loginDataBaseAdapter.addPatient(Fname, Mname, Lname, Village, Mobile, filemanagerstring);
                            dialog.dismiss();

                            Intent intentSignUP = new Intent(getApplicationContext(), PatientList.class);
                            startActivity(intentSignUP);
                        }
                    }
                });
                dialog.show();
                return true;
            case R.id.deleteAll:
                loginDataBaseAdapter.deleteAllPatients();
            case R.id.refresh:
                Intent intentSignUP = new Intent(getApplicationContext(), PatientList.class);
                startActivity(intentSignUP);
            case R.id.search:
                mSearchView.setVisibility(View.VISIBLE);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    //OI FILE Manager
                   // filemanagerstring = selectedImage.getEncodedPath();
                    image.setImageURI(selectedImage);
                    System.out.println("path"+filemanagerstring);
//                    InputStream imageStream = null;
//                    try {
//                        imageStream = getContentResolver().openInputStream(selectedImage);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                    try {
                        Bitmap yourSelectedImage = decodeUri(selectedImage);
                        // convert from bitmap to byte array

                        filemanagerstring=getBytes(yourSelectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    //UPDATED!
    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 20;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }



    //Set up SearchVew
    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
       // mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }


}

