package com.sejongsoftware.seoulappproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by choyoushin on 2018. 7. 14..
 */

public class DetailActivity extends NMapActivity {
    String SVCID;
    JSONObject data = null;
    NMapView mMapView;
    private NMapResourceProvider nMapResourceProvider;
    private NMapOverlayManager mapOverlayManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        SVCID = intent.getStringExtra("SVCID");

        mMapView = (NMapView) findViewById(R.id.nmap_view);
        mMapView.setClientId("IaMjtxVpIlW6_R7PlD4p");
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();
        mMapView.setScalingFactor(1.7f);

        nMapResourceProvider = new NMapViewerResourceProvider(this);
        mapOverlayManager = new NMapOverlayManager(this, mMapView, nMapResourceProvider);

        //mMapView.getMapController().animateTo(new NGeoPoint( 127.036976, 37.473049 ))
        GetSVCdetail getSVCdetail = new GetSVCdetail();
        getSVCdetail.execute();

    }

    public class GetSVCdetail extends AsyncTask<Void, Void, Void>
    {
        String API_KEY = "414f414a6963686f33316d65547677";

        @Override
        protected void onPostExecute(Void aVoid) {
            //
            Log.i("datail_data", data.toString());

            TextView tv_detail_SVCNM = (TextView) findViewById(R.id.tv_detail_SVCNM);
            TextView tv_detail_SVCSTTUS_NM = (TextView) findViewById(R.id.tv_detail_SVCSTTUS_NM);

            try {
                tv_detail_SVCNM.setText( data.get("SVCNM").toString() );

                if ( data.has("SVCSTTUS_NM") ) {
                    tv_detail_SVCSTTUS_NM.setText( data.get("SVCSTTUS_NM").toString() );
                }
                else {
                    tv_detail_SVCSTTUS_NM.setText( data.get("SVCSTATNM").toString() );
                }


                if ( !data.get("X").toString().equals("0") ) {
                    NGeoPoint point = new NGeoPoint(
                            Double.parseDouble(data.get("Y").toString()),
                            Double.parseDouble(data.get("X").toString())
                    );

                    int markerId = NMapPOIflagType.PIN;

                    NMapPOIdata poIdata = new NMapPOIdata(1, nMapResourceProvider);
                    poIdata.beginPOIdata(1);
                    poIdata.addPOIitem(
                            Double.parseDouble(data.get("Y").toString()),
                            Double.parseDouble(data.get("X").toString()),
                            data.get("PLACENM").toString(),
                            markerId, 0
                    );
                    poIdata.endPOIdata();

                    NMapPOIdataOverlay poIdataOverlay = mapOverlayManager.createPOIdataOverlay(poIdata, null);
                    poIdataOverlay.showAllPOIdata(0);

                    mMapView.getMapController().setMapCenter( point, 10 );
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            try {
                URL JSON_URL = new URL("http://openAPI.seoul.go.kr:8088/"+API_KEY+"/json/ListPublicReservationDetail/1/1/"+SVCID);

                HttpURLConnection conn = (HttpURLConnection) JSON_URL.openConnection();
                InputStream is = conn.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                String strJson = br.readLine();


                JSONObject jsonObj = new JSONObject(strJson);
                if ( jsonObj.has("ListPublicReservationDetail") ) {
                    data = (JSONObject) jsonObj.getJSONObject("ListPublicReservationDetail").getJSONArray("row").get(0);
                }
                else {
                    URL JSON_URL1 = new URL("http://10.0.2.2:8080/public/service/" + SVCID);

                    Log.d("JSON_URL", JSON_URL1.toString());

                    HttpURLConnection conn1 = (HttpURLConnection) JSON_URL1.openConnection();
                    InputStream is1 = conn1.getInputStream();
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(is1, "UTF-8"));
                    strJson = br1.readLine();

                    Log.i("strJson", strJson);

                    data = new JSONObject(strJson);
                }
                //Log.i("jsonObj", jsonObj.get("row").toString());

                //data = new JSONObject(jsonObj.get("row").toString());
            } catch (Exception e) {
                Log.d("strJson", "실패");
            }

            return null;
        }
    }

}
