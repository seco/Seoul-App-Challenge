package com.sejongsoftware.seoulappproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by choyoushin on 2018. 7. 8..
 * http://openAPI.seoul.go.kr:8088/(인증키)/xml/ListPublicReservationInstitution/1/5/(소분류)
 */

public class ListActivity extends Activity {
    TextView textView;
    String API_KEY = "414f414a6963686f33316d65547677", Service;
    int LIST_IDX = 0;
    ListView servicesList;
    ArrayList<String> items;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        Service = intent.getStringExtra("service");

        items = new ArrayList<String>();
        adapter = new ArrayAdapter(getApplicationContext(), R.layout.services_list_item, R.id.item_label, items);
        servicesList = (ListView) findViewById(R.id.list_service);
        servicesList.setAdapter(adapter);

        ServciesListTask servciesListTask = new ServciesListTask();
        servciesListTask.execute(API_KEY, Service);
    }

    public class ServciesListTask extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONArray services) {
            super.onPostExecute(services);

            Log.i("JSON", services.toString());

            try {
                for (int i = 0; i < services.length(); i++) {
                    JSONObject order = services.getJSONObject(i);
                    items.add(order.get("SVCNM").toString());
                }

                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected JSONArray doInBackground(String... strings) {
            //"http://openAPI.seoul.go.kr:8088/"+API_KEY+"/json/ListPublicReservation"+Service+"/1/5/";
            String JSON_URL = "http://openapi.seoul.go.kr:8088/"+API_KEY+"/json/ListPublicReservation"+Service+"/"+ (1 + LIST_IDX*10) +"/" + (LIST_IDX+1)*10;
            JSONArray services = new JSONArray();
            try {
                URL url = new URL(JSON_URL);
                HttpURLConnection conn;
                String protocol = "GET";
                BufferedReader br;

                Log.i("JSON", JSON_URL);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(protocol);
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                Log.i("JSON", "여기까지됨");

                JSONObject json = new JSONObject(br.readLine());
                JSONObject status = (JSONObject) json.get("ListPublicReservation" + Service);
                services = (JSONArray) status.get("row");

            } catch (Exception e) {
                e.printStackTrace();
            }

            return services;
        }
    }
}
