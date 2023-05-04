package com.example.kuknur_mycarfootprint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class GasVisit {
    private String name;
    private String fill_date;
    private int date_month;
    private int date_year;
    private int date_day;
    //private Calendar date;
    private String type;
    private int amount;
    private float price_per_litre;
    private float crb_footprint;


    public GasVisit(String name, int date_year, int date_month, int date_day, String type, int amount, float price_per_litre) {
        this.name = name;
        this.date_year = date_year;
        this.date_month = date_month;
        this.date_day = date_day;
        this.type = type;
        this.amount = amount;
        this.price_per_litre = price_per_litre;
    }

    public int getDate_month() {
        return date_month;
    }

    public void setDate_month(int date_month) {
        this.date_month = date_month;
    }

    public int getDate_year() {
        return date_year;
    }

    public void setDate_year(int date_year) {
        this.date_year = date_year;
    }

    public int getDate_day() {
        return date_day;
    }

    public void setDate_day(int date_day) {
        this.date_day = date_day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFill_date() {
        String date = String.valueOf(date_year)+"/"+String.valueOf(date_month)+"/"+String.valueOf(date_day);;
        return date;
    }

    public void setFill_date(String fill_date) {
        this.fill_date = fill_date;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getPrice_per_litre() {
        return price_per_litre;
    }

    public void setPrice_per_litre(float price_per_litre) {
        this.price_per_litre = price_per_litre;
    }

    public String getCrb_footprint() {
        if (Objects.equals(type, "Gasoline")){
            crb_footprint = amount *2.32f;
        }else{
            crb_footprint = amount *2.69f;
        }
        crb_footprint = Math.round(crb_footprint);
        String crb = String.valueOf(crb_footprint);
        return crb;
    }
}
