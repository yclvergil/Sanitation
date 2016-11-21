package com.songming.sanitation.sign.db;

/**
 * Created by Administrator on 2016/11/16.
 */

public class SignBean {
    private String name;
    private String address;
    private String date;
    private String time;
    private double longi;
    private double lati;
    private int type;

    public SignBean() {
    }

    public SignBean(String name, String address, String date, String time, double longi, double lati, int type) {
        this.name = name;
        this.address = address;
        this.date = date;
        this.time = time;
        this.longi = longi;
        this.lati = lati;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
