package com.samsung.wfapidemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by gurunath on 8/30/17.
 */

public class ParseData {

    public static String[] time;
    public static String[] summary;
    public static String[] icon;
    public static String[] currentData;
    public static String[] mDates;


    public static final String JSON_ARRAY = "data";
    public static final String JSON_OBJECT = "hourly";
    public static final String KEY_TIME = "time";
    public static final String KEY_SUM = "summary";
    public static final String JSON_CUR = "currently";
    public static final String KEY_ICON = "icon";

    private String json;


    private JSONArray wData = null,wData1=null;


    public ParseData(String json){
        this.json = json;
    }


    protected void parseJSONData(){
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(json);
            JSONObject currently = jsonObject.getJSONObject(JSON_CUR);
            JSONObject hourly = jsonObject.getJSONObject(JSON_OBJECT);
            wData = hourly.getJSONArray(JSON_ARRAY);
            currentData = new String[3];
            currentData[0] = currently.getString(KEY_TIME);
            currentData[1] = currently.getString(KEY_SUM);
            currentData[2] = currently.getString(KEY_ICON);

            time = new String[wData.length()];
            summary = new String[wData.length()];
            icon = new String[wData.length()];

            HashSet<String> set = new HashSet<String>();


            for(int i=0;i<wData.length();i++){

                JSONObject jsonObject1 = wData.getJSONObject(i);
                time[i] = jsonObject1.getString(KEY_TIME);
                set.add(DateTimeConverter.convertDate(Long.parseLong(time[i])));
                summary[i] = jsonObject1.getString(KEY_SUM);
                icon[i] = jsonObject1.getString(KEY_ICON);

            }

            mDates = new String[set.size()];
            Iterator<String> it = set.iterator();
            int i=0;
            while (it.hasNext()){
                mDates[i] = it.next();
                i++;
            }




        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }



}
