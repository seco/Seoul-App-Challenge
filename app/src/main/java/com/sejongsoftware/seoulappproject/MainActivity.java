package com.sejongsoftware.seoulappproject;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import kr.go.seoul.airquality.AirQualityTypeMini;

public class MainActivity extends AppCompatActivity {
    private AirQualityTypeMini typeMini;
    private TextView myTextView;
    private String OpenApiKey = "414f414a6963686f33316d65547677";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeMini = (AirQualityTypeMini) findViewById(R.id.type_mini);
        typeMini.setOpenAPIKey(OpenApiKey);

        myTextView = (TextView) findViewById(R.id.text_view);

        AirQuality Parser = new AirQuality();
        Parser.execute();
    }

    private class AirQuality extends AsyncTask<Void, Void, Void> {
//http://openAPI.seoul.go.kr:8088/sample/xml/ForecastWarningMinuteParticleOfDustService/1/1/

        private String rUrl = "http://openAPI.seoul.go.kr:8088/"+ OpenApiKey +"/json";
        private String DS = "/ForecastWarningMinuteParticleOfDustService/1/1"; // 미세먼지

        private JSONObject DustInfo;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                myTextView.setText(DustInfo.get("ALARM_CNDT").toString());
            } catch (Exception e) {

            }

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(rUrl + DS);
                HttpURLConnection conn;
                String protocol = "GET";
                BufferedReader br;

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(protocol);
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                JSONObject json = new JSONObject(br.readLine());
                JSONObject DustService = (JSONObject) json.get("ForecastWarningMinuteParticleOfDustService");
                JSONArray DustArr = (JSONArray) DustService.get("row");
                DustInfo = (JSONObject) DustArr.get(0);
                Log.i("Parser", DustInfo.toString());
            } catch (Exception e) {
                Log.i("Parser", "실패");
            }
            return null;
        }
    }
}
