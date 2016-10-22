package com.example.vijayc.patientmanagementsystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vijayc on 22/10/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME = "patientmanagementsystem";
    private static final String TABLE_NAME = "login";
    private static final String COLUMON_ID ="id";
    private static final String COLUMON_NAME ="name";
    private static final String COLUMON_EMAIL="email";
    private static final String COLUMON_UNAME ="uname";
    private static final String COLUMON_PASS = "pass";
    SQLiteDatabase db;

    private static  final String TABLE_CREATE = "create table login (id integer primary key not null auto_increment , " + "name text not null, email text not null, uname text not null, pass text not null)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db=db;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query ="DROP TABLE IF EXIST" +TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

    }
}
