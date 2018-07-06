package com.sejongsoftware.seoulappproject;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
