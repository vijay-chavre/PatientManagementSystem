package com.example.vijayc.patientmanagementsystem.models;

/**
 * Created by vijayc on 19/11/16.
 */

public class VisitsProvider {

    private int vid;
    private int vpid;
    private  String name;
    private String discription;
    private int total_amount;
    private  int amount_paid;
    private  String date;

    public VisitsProvider(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public VisitsProvider(int vid, String name, int total_amount, int vpid, String discription, int amount_paid, String date) {
        this.vid = vid;
        this.name = name;
        this.total_amount = total_amount;
        this.vpid = vpid;
        this.discription = discription;
        this.amount_paid = amount_paid;
        this.date = date;
    }

    public VisitsProvider(int vid, int vpid, String name, int total_amount, int amount_paid, String date) {
        this.vid = vid;
        this.vpid = vpid;
        this.name = name;
        this.total_amount = total_amount;
        this.amount_paid = amount_paid;
        this.date = date;
    }

    public int getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(int amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getVpid() {
        return vpid;
    }

    public void setVpid(int vpid) {
        this.vpid = vpid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    @Override
    public String toString() {
        return "VisitsProvider{" +
                "vid=" + vid +
                ", vpid=" + vpid +
                ", name='" + name + '\'' +
                ", discription='" + discription + '\'' +
                ", total_amount=" + total_amount +
                ", amount_paid=" + amount_paid +
                ", date='" + date + '\'' +
                '}';
    }
}
