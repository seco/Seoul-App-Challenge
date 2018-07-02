package com.sejongsoftware.seoulappproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import kr.go.seoul.airquality.AirQualityTypeMini;

public class MainActivity extends Activity {
    //private String OpenApiKey = "414f414a6963686f33316d65547677";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()) {
                    case R.id.user_btn:
                        Toast.makeText( getApplicationContext(), "user", Toast.LENGTH_SHORT ).show();
                        break;

                    case R.id.manager_btn:
                        Intent intent = new Intent( getApplicationContext(), LoginActivity.class );
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        };

        Button userBtn = (Button) findViewById(R.id.user_btn);
        Button managerBtn = (Button) findViewById(R.id.manager_btn);

        userBtn.setOnClickListener(onClickListener);
        managerBtn.setOnClickListener(onClickListener);

    }

    /*

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
    */
}
