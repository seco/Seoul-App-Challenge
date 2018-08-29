package com.sejongsoftware.seoulappproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by choyoushin on 2018. 7. 8..
 * http://openAPI.seoul.go.kr:8088/(인증키)/xml/ * http://openAPI.seoul.go.kr:8088/(인증키)/xml/ListPublicReservationInstitution/1/5/(소분류)
 ListPublicReservationInstitution/1/5/(소분류)
 */

public class ListActivity extends Activity {
    TextView textView;
    String API_KEY = "414f414a6963686f33316d65547677";
    String MAXCLASSNM = "대분류", MINCLASSNM = "소분류", AREANM = "지역명";
    int LIST_IDX = 0;
    ListView servicesList;
    ServicesListAdapter ca;
    ArrayList<Item> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        MAXCLASSNM = intent.getStringExtra("service");
        servicesList = (ListView) findViewById(R.id.list_service);

        // 소분류 Spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinnerMinClass);
        List<String> minclass_list = new ArrayList<String>();
        switch (MAXCLASSNM) {
            case "체육시설":
                minclass_list = Arrays.asList( getResources().getStringArray(R.array.sport_list) );
                break;
            case "시설대관":
                minclass_list = Arrays.asList( getResources().getStringArray(R.array.institution_list) );
                break;
            case "문화행사":
                minclass_list = Arrays.asList( getResources().getStringArray(R.array.culture_list) );
                break;
            case "교육":
                minclass_list = Arrays.asList( getResources().getStringArray(R.array.education_list) );
                break;
            case "진료":
                minclass_list = Arrays.asList( getResources().getStringArray(R.array.medical_list) );
                break;
        }
        spinner.setAdapter( new ClassSpinnerAdapter(this, minclass_list) );

        TextView tv_activity_title = (TextView) findViewById(R.id.activity_title);
        tv_activity_title.setText(MAXCLASSNM);

        // 지역명 Spinner
        Spinner areaSpinner = (Spinner) findViewById(R.id.spinnerArea);
        List<String> area_list = new ArrayList<String>();
        area_list = Arrays.asList(getResources().getStringArray(R.array.area_list));
        areaSpinner.setAdapter( new ClassSpinnerAdapter(this, area_list) );

        ServciesListTask servciesListTask = new ServciesListTask();
        servciesListTask.execute(API_KEY, MAXCLASSNM, MINCLASSNM, AREANM);

        servicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent_detail = new Intent( getApplicationContext(), DetailActivity.class );
                intent_detail.putExtra("SVCID", items.get(position).getSVCID().toString());
                startActivity(intent_detail);
            }
        });
    }

    public class ServciesListTask extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONArray services) {
            items = new ArrayList<Item>();
            ca = new ServicesListAdapter(getApplicationContext(), R.layout.services_list_item, items);
            servicesList.setAdapter(ca);

            Log.i("JSON", services.toString());

            try {
                for (int i = 0; i < services.length(); i++) {
                    JSONObject order = services.getJSONObject(i);
                    //items.add(order.get("SVCNM").toString());
                    Item item = new Item(
                            order.get("SVCID").toString(),
                            order.get("SVCNM").toString(),
                            order.get("MAXCLASSNM").toString(),
                            order.get("MINCLASSNM").toString(),
                            order.get("AREANM").toString(),
                            order.get("SVCSTATNM").toString(),
                            order.get("PAYATNM").toString(),
                            order.get("PLACENM").toString(),
                            order.get("USETGTINFO").toString()
                            );
                    items.add(item);
                }
                ca.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected JSONArray doInBackground(String... strings) {

            //"http://openAPI.seoul.go.kr:8088/"+API_KEY+"/json/ListPublicReservation"+Service+"/1/5/";
            String JSON_URL = "http://sejongsoftware.com/public/servicies";
            HttpPost httppost = new HttpPost(JSON_URL);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            HttpEntity resEntity;
            JSONArray json = null;

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("MAXCLASSNM", strings[1]));
                if( !strings[2].equals("소분류") ) {
                    nameValuePairs.add(new BasicNameValuePair("MINCLASSNM", strings[2]));
                }
                if( !strings[3].equals("지역명") ) {
                    nameValuePairs.add(new BasicNameValuePair("AREANM", strings[3]));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
                response = httpclient.execute(httppost);
                resEntity = response.getEntity();

                //Log.i("Post", EntityUtils.toString(resEntity));
                json = new JSONArray(EntityUtils.toString(resEntity));
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("POST", "실패");
            }
            return json;
        }
    }

    public void searchByCategory(View view)
    {
        Spinner spinner_min_class = (Spinner) findViewById(R.id.spinnerMinClass);
        Spinner spinner_area = (Spinner) findViewById(R.id.spinnerArea);
        MINCLASSNM = spinner_min_class.getSelectedItem().toString();
        AREANM = spinner_area.getSelectedItem().toString();

        ServciesListTask servciesListTask = new ServciesListTask();
        servciesListTask.execute(API_KEY, MAXCLASSNM, MINCLASSNM, AREANM);
    }
}
