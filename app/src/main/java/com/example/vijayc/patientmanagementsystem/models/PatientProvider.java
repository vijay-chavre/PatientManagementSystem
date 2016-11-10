package com.example.vijayc.patientmanagementsystem.models;

/**
 * Created by vijayc on 09/11/16.
 */

public class PatientProvider {
    private  String fname;
    private  String village;

    public PatientProvider(String fname, String village) {
        this.fname = fname;
        this.village = village;
    }

    public String getFname() {
        return fname;
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

    @Override
    public String toString() {
        return "PatientProvider{" +
                "fname='" + fname + '\'' +
                ", village='" + village + '\'' +
                '}';
    }
}
