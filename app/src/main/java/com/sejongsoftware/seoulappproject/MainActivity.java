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

    private JSONObject MinuteParticle, UltrafinePartcile, Ozone;

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
        private String rUrl = "http://openAPI.seoul.go.kr:8088/"+ OpenApiKey +"/json";

        private String services[] = {
                "ForecastWarningMinuteParticleOfDustService",
                "ForecastWarningUltrafineParticleOfDustService",
                "ForecastWarningOzoneService",
        };

        //Json 이름
        //ForecastWarningMinuteParticleOfDustService
        //ForecastWarningUltrafineParticleOfDustService

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                myTextView.setText(UltrafinePartcile.get("ALARM_CNDT").toString());
            } catch (Exception e) {

            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                MinuteParticle = getJsonAtUrl(0);
                UltrafinePartcile = getJsonAtUrl(1);
                Ozone = getJsonAtUrl(2);

                Log.i("Parser", MinuteParticle.toString());
                Log.i("Parser", UltrafinePartcile.toString());
                Log.i("Parser", Ozone.toString());
            } catch (Exception e) {
                Log.i("Parser", "실패");
            }
            return null;
        }

        private JSONObject getJsonAtUrl(int service) {
            JSONObject result = new JSONObject();
            String input_url = rUrl + "/" + services[service] + "/1/1";
            try {
                URL url = new URL(input_url);
                HttpURLConnection conn;
                String protocol = "GET";
                BufferedReader br;

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(protocol);
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                JSONObject json = new JSONObject(br.readLine());
                JSONObject DustService = (JSONObject) json.get(services[service]);
                JSONArray DustArr = (JSONArray) DustService.get("row");
                result = (JSONObject) DustArr.get(0);

            } catch (Exception e) {
                Log.i("getJsonAtUrl", "실패");
            }

            return result;
        }
    }
}
