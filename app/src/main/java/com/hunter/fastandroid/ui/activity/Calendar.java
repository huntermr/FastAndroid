package com.hunter.fastandroid.ui.activity;

import java.util.Date;

public class Calendar {
    public Date date;
    public String price;
    public boolean check;
    public String holiday;
    public boolean rest;
    public String lunar;
    public boolean isUse = true;

    public Calendar(Date date, String price) {
        this.date = date;
        this.price = price;
    }
}
