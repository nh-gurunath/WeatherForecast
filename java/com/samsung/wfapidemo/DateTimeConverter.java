package com.samsung.wfapidemo;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by gurunath on 8/30/17.
 */

public class DateTimeConverter {

    public static String convertTime(long time){
        String standardTime = "";

        Calendar myCalender = Calendar.getInstance();
        myCalender.setTimeZone(TimeZone.getDefault());
        myCalender.setTimeInMillis(time*1000);
//        String currTime = String.format("%02d:%02d",myCalender.get(Calendar.HOUR_OF_DAY), myCalender.get(Calendar.MINUTE));
        String Date = myCalender.get(Calendar.DAY_OF_MONTH)+"/"+(myCalender.get(Calendar.MONTH)+1)+"/"+myCalender.get(Calendar.YEAR);
        String currTime = onTimeSet(myCalender.get(Calendar.HOUR_OF_DAY),myCalender.get(Calendar.MINUTE));
        standardTime = Date + " " + currTime;


        return standardTime;
    }


    public static String convertDate(long time){
        String Date = "";
        Calendar myCalender = Calendar.getInstance();
        myCalender.setTimeZone(TimeZone.getDefault());
        myCalender.setTimeInMillis(time*1000);
//        String currTime = String.format("%02d:%02d",myCalender.get(Calendar.HOUR_OF_DAY), myCalender.get(Calendar.MINUTE));
        Date = myCalender.get(Calendar.DAY_OF_MONTH)+"/"+(myCalender.get(Calendar.MONTH)+1)+"/"+myCalender.get(Calendar.YEAR);
        return Date;
    }


    public static String onTimeSet( int hour, int minute) {

        String selecteTime;

        Calendar mCalen = Calendar.getInstance();;
        mCalen.set(Calendar.HOUR_OF_DAY, hour);
        mCalen.set(Calendar.MINUTE, minute);

        int hour12format_local = mCalen.get(Calendar.HOUR);
        int hourOfDay_local = mCalen.get(Calendar.HOUR_OF_DAY);
        int minute_local = mCalen.get(Calendar.MINUTE);
        int ampm = mCalen.get(Calendar.AM_PM);
        String minute1;
        if(minute_local<10){

            minute1="0"+minute_local;
        }
        else
            minute1=""+minute_local;

        String ampmStr = (ampm == 0) ? "AM" : "PM";
        // Set the Time String in Button

        if(hour12format_local==0) hour12format_local=12;



        selecteTime = hour12format_local+":"+ minute1+" "+ampmStr;

       return selecteTime;


    }
}
