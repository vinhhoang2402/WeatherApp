package com.example.weatherapp.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static final String APP_ID="44ad030b91cdfff6ff5e00beb3fd30ae";
    public static Location current_location=null;

    public static String converDate(int dt) {
        Date date =new Date(dt*1000L);
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm EEE MM yyyy");
        String formated=sdf.format(date);
        return formated;

    }

    public static String convertHour(int sunrise) {
        Date date =new Date(sunrise*1000L);
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        String formated=sdf.format(date);
        return formated;

    }
}
