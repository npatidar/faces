package com.faceoffaerie.activities;

import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SaveFaeryCurrentDate {

    public static String faerySavedDate (){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sDateF = new SimpleDateFormat("EEEE, dd MMMM yyyy : hh:mm:ss a");

        String currentDate = sDateF.format(calendar.getTime());
        Log.e("SavedFaeryCurrentDate ", "Current Day is :- " + currentDate);
        return currentDate;
    }
}
