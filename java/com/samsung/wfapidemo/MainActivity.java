package com.samsung.wfapidemo;

import android.*;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {

    private TextView txtLongitude;

    private TextView txtLatitude;

    // GPSTracker class
    GPSTracker gps;

    private static final int PERMISSION_REQUEST_CODE = 1;

    private String url;

    ProgressDialog pDialog;

    ListView listView;

    private TextView txtCurrData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLongitude = (TextView) findViewById(R.id.longitude);
        txtLatitude = (TextView) findViewById(R.id.latitude);
        txtCurrData = (TextView) findViewById(R.id.currentData);
        listView = (ListView) findViewById(R.id.listView);
        pDialog = new ProgressDialog(this);
        if (!checkPermission()) {
            requestPermission();
        }
        ShowGPSLocation();


    }

    private void ShowGPSLocation() {

        // Create class object

        gps = new GPSTracker(MainActivity.this);

        // Check if GPS enabled
        if(gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            url = "https://api.darksky.net/forecast/96fd73a17cce0cdef62b8e6dfe02ddf4/"+latitude+","+longitude+"?extend=hourly";
            txtLongitude.setText(String.valueOf(longitude));
            txtLatitude.setText(String.valueOf(latitude));
            callHttpRequest(url);
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();
        }
    }


    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    private void requestPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){

            Toast.makeText(this,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this,"Permission Granted, Now you can access location data.",Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(this,"Permission Denied, You cannot access location data.",Toast.LENGTH_LONG).show();

                }
                break;
        }
    }


    private void callHttpRequest(String mUrl){
        // Tag used to cancel the request
        String  tag_string_req = "string_req";

        pDialog.setMessage("Loading ...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.GET, mUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                Log.d("Response",response);

                if(response.equals(" ") || response == null){

                }else{
                    showJSON(response);
                }

                //pDialog.cancel();
                //Toast.makeText(MainActivity.this,"Request Successfull",Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();

                pDialog.cancel();


            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }


    private void showJSON(String json){
        ParseData pd = new ParseData(json);
        pd.parseJSONData();
        String time = "", summary = "";
        time = DateTimeConverter.convertTime(Long.parseLong(ParseData.currentData[0]));
        summary = time + " " + ParseData.currentData[2].toUpperCase();
        txtCurrData.setText(summary);
        CustomList cl = new CustomList(MainActivity.this, ParseData.time,ParseData.summary,ParseData.icon);
        listView.setAdapter(cl);
        for (int i=0;i<ParseData.mDates.length;i++){
            Log.d("Day = "+i,String.valueOf(ParseData.mDates[i]));
        }

        pDialog.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pDialog.cancel();
    }
}
