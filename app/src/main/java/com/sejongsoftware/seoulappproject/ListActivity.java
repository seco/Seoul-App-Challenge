package com.sejongsoftware.seoulappproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by choyoushin on 2018. 7. 8..
 * http://openAPI.seoul.go.kr:8088/(인증키)/xml/ListPublicReservationInstitution/1/5/(소분류)
 */

public class ListActivity extends Activity {
    TextView textView;
    String API_KEY = "414f414a6963686f33316d65547677";
    String Service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        Service = intent.getStringExtra("service");
        Log.i("List", Service);

        String URL = "http://openAPI.seoul.go.kr:8088/"+API_KEY+"/json/ListPublicReservation"+Service+"/1/5/";

        ServciesListTask servciesListTask = new ServciesListTask();
        servciesListTask.execute(URL);
    }

    public class ServciesListTask extends AsyncTask<String, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... strings) {
            Log.i("List", strings[0]);
            return null;
        }
    }
}
