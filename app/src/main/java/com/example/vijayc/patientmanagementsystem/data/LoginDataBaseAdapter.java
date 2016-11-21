package com.example.vijayc.patientmanagementsystem.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by vijayc on 23/10/16.
 */
public class LoginDataBaseAdapter
{
    static int count;
    static final String DATABASE_NAME = "login.db";

    static final int DATABASE_VERSION = 1;

    //collumn names of Patient
    public  static final String P_ID="P_ID";
    public  static final String P_FNAME="P_FNAME";
    public  static final String P_MNAME="P_MNAME";
    public  static final String P_LNAME="P_LNAME";
    public  static final String P_VILLAGE="P_VILLAGE";
    public  static final String P_MOBILE="P_MOBILE";
    public  static final String P_IMAGEPATH="P_IMAGEPATH";

    // collumn names of VISITS Table

    public   static final String VP_ID="V_ID";
    public   static final String NAME="NAME";
    public   static final String DISCRIPTION="DESCRIPTION";
    public   static final String AMOUNT="TOTAL_AMOUNT";
    public   static final String AMOUNT_PAID="AMOUNT_PAID";
    public   static final String DATE="DATE";






    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database Tables.
    //LOGIN table Create Statement
    static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text,PASSWORD text); ";

    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DatabaseHelper dbHelper;
    public  LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method to openthe Database
    public  LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    // Method to close the Database
    public void close()
    {
        db.close();
    }

    // method returns an Instance of the Database
    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

  //.............End of General methods.........................................................



    // method to insert a record in Table LOGIN........................................
    public void insertEntry(String userName,String password)
    {


        ContentValues newValues = new ContentValues();
        // Assign values for each column.
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD",password);



        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        Toast.makeText(context, "User Info Saved", Toast.LENGTH_LONG).show();


    }

    // method to delete a Record of UserName in LOGIN Table
    public int deleteEntry(String UserName)
    {

        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
        Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;

    }

    // method to get the password  of userName from LOGIN Table
    public String getSinlgeEntry(String userName)
    {


        Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return "NOT EXIST";
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        return password;


    }

    // Method to Update an Existing Record In LOGIN Table
    public void  updateEntry(String userName,String password)
    {
        //  create object of ContentValues
        ContentValues updatedValues = new ContentValues();
        // Assign values for each Column.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD",password);

        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{userName});

    }

    //method to Get all Users FROM LOGIN Table
    public ArrayList<String> getAllUsers()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
      db = dbHelper.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from LOGIN", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("USERNAME")));
            res.moveToNext();
        }
        return array_list;
    }


    /* .........................Methods for PATIENT Table.............................................. */

    //PATIENT table Create Statement
    static final String PATIENT_TABLE = "create table "+"PATIENT"+
            "( " +"P_ID"+" integer primary key autoincrement,"+ "P_FNAME  text,P_MNAME  text,P_LNAME  text,P_VILLAGE text,P_MOBILE  text,P_IMAGEPATH BLOB); ";

    // method to insert a record in Table PATIENT
    public void addPatient(String fName, String mName, String lName, String village, String mobile, byte[] image)
    {


        ContentValues newValues = new ContentValues();
        // Assign values for each column.
        newValues.put("P_FNAME", fName);
        newValues.put("P_MNAME",mName);
        newValues.put("P_LNAME",lName);
        newValues.put("P_VILLAGE",village);
        newValues.put("P_MOBILE",mobile);
        newValues.put("P_IMAGEPATH",image);



        // Insert the row into your table
        db.insert("PATIENT", null, newValues);
        Toast.makeText(context, "Patient Info Saved", Toast.LENGTH_LONG).show();


    }


    //Method to get All patients
    public Cursor getAllPatients()
    {

        db = dbHelper.getReadableDatabase();
      String projection[]={"P_ID","P_FNAME","P_MNAME","P_LNAME","P_VILLAGE","P_IMAGEPATH","P_MOBILE"};

        Cursor res=db.query("PATIENT",projection,null,null,null,null,null);
        return res;
    }

    //Seradhing patients List Method
    public Cursor getAllPatients(String newText) {

        db = dbHelper.getReadableDatabase();
        String projection[]={"P_ID","P_FNAME","P_MNAME","P_LNAME","P_VILLAGE","P_IMAGEPATH","P_MOBILE"};
//        Cursor res=db.query("PATIENT",projection,null,null,null,null,null);

        String[]a=new String[1];
        a[0]= newText+"%";
        Cursor res=db.rawQuery("SELECT * FROM PATIENT WHERE P_FNAME LIKE ?", a);
        return res;


    }


    // method to get the password  of userName from LOGIN Table
    public Cursor getSinglePatient(String P_ID)
    {

        db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query("PATIENT", null, " P_ID=?", new String[]{P_ID}, null, null, null);
        if(cursor.getCount()<1){
            System.out.print("Not Exist");
        }
        return cursor;


    }


    //Method to delete All patients
    public int deleteAllPatients()
    {


        int numberOFEntriesDeleted= db.delete("PATIENT", null, null) ;
        Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;

    }


    /* .........................Methods for VISITS Table.............................................. */
    static final String VISITS_TABLEE = "create table "+"VISITS"+
            "( " +"V_ID"+" integer primary key autoincrement,"+ "VP_ID  integer not null,NAME  text,DISCRIPTION  text,TOTAL_AMOUNT integer,AMOUNT_PAID  integer,DATE text); ";



    // method to insert a record in Table VISITS
    public void addVisits(String discription,String pname,int vp_id, int amount, int amount_paid)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        ContentValues newValues = new ContentValues();
        // Assign values for each column.
        newValues.put("VP_ID", vp_id);
        newValues.put("NAME",pname);
        newValues.put("DISCRIPTION",discription);
        newValues.put("TOTAL_AMOUNT",amount);
        newValues.put("AMOUNT_PAID",amount_paid);
        newValues.put("DATE",date);




        // Insert the row into your table
        db.insert("VISITS", null, newValues);
        Toast.makeText(context, " Info Saved", Toast.LENGTH_LONG).show();


    }

    //Method to get All Visits
    public Cursor getAllVisits()
    {

        db = dbHelper.getReadableDatabase();
        String projection[]={"V_ID","VP_ID","NAME","DISCRIPTION","TOTAL_AMOUNT","AMOUNT_PAID","DATE"};

        Cursor res=db.query("VISITS",projection,null,null,null,null,null);
        return res;
    }

    // Method to get all vists of selected PID
    public  Cursor getAllVisits(String vp_id){
        System.out.println(vp_id);
        db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query("VISITS", null, " VP_ID=?", new String[]{vp_id}, null, null, null);
        if(cursor.getCount()<1){
            System.out.print("Not Exist");
        }
        return cursor;
    }



}
