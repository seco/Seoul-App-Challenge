package com.sejongsoftware.seoulappproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by choyoushin on 2018. 7. 14..
 */

public class DetailActivity extends Activity {
    String SVCID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        SVCID = intent.getStringExtra("SVCID");

        GetSVCdetail getSVCdetail = new GetSVCdetail();
        getSVCdetail.execute();
    }

    public class GetSVCdetail extends AsyncTask<Void, Void, Void>
    {
        String API_KEY = "414f414a6963686f33316d65547677";
        JSONObject data = null;

        @Override
        protected void onPostExecute(Void aVoid) {
            //
            Log.i("datail_data", data.toString());

            TextView tv_detail_SVCNM = (TextView) findViewById(R.id.tv_detail_SVCNM);
            TextView tv_detail_SVCSTTUS_NM = (TextView) findViewById(R.id.tv_detail_SVCSTTUS_NM);

            try {
                tv_detail_SVCNM.setText( data.get("SVCNM").toString() );
                tv_detail_SVCSTTUS_NM.setText( data.get("SVCSTTUS_NM").toString() );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            try {
                URL JSON_URL = new URL("http://openAPI.seoul.go.kr:8088/"+API_KEY+"/json/ListPublicReservationDetail/1/1/"+SVCID);

                Log.d("JSON_URL", JSON_URL.toString());

                HttpURLConnection conn = (HttpURLConnection) JSON_URL.openConnection();
                InputStream is = conn.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                String strJson = br.readLine();



                JSONObject jsonObj = new JSONObject(strJson);
                data = (JSONObject) jsonObj.getJSONObject("ListPublicReservationDetail").getJSONArray("row").get(0);

                //Log.i("jsonObj", jsonObj.get("row").toString());

                //data = new JSONObject(jsonObj.get("row").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}
