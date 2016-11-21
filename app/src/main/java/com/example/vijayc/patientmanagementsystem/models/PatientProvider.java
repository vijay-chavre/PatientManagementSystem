package com.example.vijayc.patientmanagementsystem.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by vijayc on 09/11/16.
 */

public class PatientProvider{
    private int id;
    private  String fname;
    private  String mname;
    private  String lname;
    private  String village;
    private  String mobile;
    private  byte[] imagepath;

    public PatientProvider(String fname, String village) {
        this.fname = fname;
        this.village = village;
    }
    public PatientProvider(int id, String fname, String village, String mobile, byte[] imagepath) {
        this.id = id;
        this.fname = fname;
        this.village = village;
        this.mobile = mobile;
        this.imagepath = imagepath;
    }

    public PatientProvider(int id, String fname, String mname, String lname, String village, String mobile, byte[] imagepath) {
        this.id = id;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.village = village;
        this.mobile = mobile;
        this.imagepath = imagepath;
    }

    @Override
    public String toString() {
        return "PatientProvider{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", mname='" + mname + '\'' +
                ", lname='" + lname + '\'' +
                ", village='" + village + '\'' +
                ", mobile='" + mobile + '\'' +
                ", imagepath=" + Arrays.toString(imagepath) +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public byte[] getImagepath() {
        return imagepath;
    }

    public void setImagepath(byte[] imagepath) {
        this.imagepath = imagepath;
    }

    public String getFname() {
        return fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setFname(String fname) {
        this.fname = fname;

    }



    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

}
