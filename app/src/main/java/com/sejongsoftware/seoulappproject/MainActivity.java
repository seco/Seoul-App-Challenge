package com.sejongsoftware.seoulappproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/*
Example : http://openAPI.seoul.go.kr:8088/(인증키)/xml/ListPublicReservationInstitution/1/5/(소분류
체육 시설 : ListPublicReservationSport
시설 대관 : ListPublicReservationInstitution
교육 : ListPublicReservationEducation
문화 행사 : ListPublicReservationCulture
진료 서비스 : ListPublicReservationMedical
 */
public class MainActivity extends Activity {
    String Service = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()) {
                    case R.id.btnSport:
                        Service = "Sport";
                        break;
                    case R.id.btnInstitution:
                        Service = "Institution";
                        break;
                    case R.id.btnEducation:
                        Service = "Education";
                        break;
                    case R.id.btnCulture:
                        Service = "Culture";
                        break;
                    case R.id.btnMedical:
                        Service = "Medical";
                        break;
                }

                Intent intent = new Intent( getApplicationContext(), ListActivity.class );
                intent.putExtra("service", Service);
                startActivity(intent);
            }
        };

        Button btnSport = (Button) findViewById(R.id.btnSport);
        Button btnInstitution = (Button) findViewById(R.id.btnInstitution);
        Button btnEducation = (Button) findViewById(R.id.btnEducation);
        Button btnCulture = (Button) findViewById(R.id.btnCulture);
        Button btnMedical = (Button) findViewById(R.id.btnMedical);

        btnSport.setOnClickListener(onClickListener);
        btnInstitution.setOnClickListener(onClickListener);
        btnEducation.setOnClickListener(onClickListener);
        btnCulture.setOnClickListener(onClickListener);
        btnMedical.setOnClickListener(onClickListener);


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
