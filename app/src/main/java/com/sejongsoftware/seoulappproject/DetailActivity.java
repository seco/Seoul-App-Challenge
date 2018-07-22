package com.sejongsoftware.seoulappproject;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
    JSONObject data1 = null;
    NMapView mMapView;
    private NMapResourceProvider nMapResourceProvider;
    private NMapOverlayManager mapOverlayManager;
    ImageButton btnBookmark;

    boolean isStarted = false;

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

        ButtonFloat btnRedirect = (ButtonFloat) findViewById(R.id.floatBtnRedirect);
        ButtonFloat btnCall = (ButtonFloat) findViewById(R.id.floatBtnCall);

        btnRedirect.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBrowser();
            }
        });
        btnCall.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String tel = "tel:" + data.get("TELNO").toString().replaceAll("-", "");
                    startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnBookmark = (ImageButton) findViewById(R.id.btn_bookmark);

        toggleBookmark();
        toggleBookmark();
        isStarted = true;

        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleBookmark();
            }
        });
    }

    public void callBrowser() {
        try {
            //http://yeyak.seoul.go.kr/mobile/detailView.web?rsvsvcid=S180618142530700410#
            String url = "http://yeyak.seoul.go.kr/mobile/detailView.web?rsvsvcid=" + data.get("SVCID").toString();
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class GetSVCdetail extends AsyncTask<Void, Void, Void>
    {
        String API_KEY = "414f414a6963686f33316d65547677";

        @Override
        protected void onPostExecute(Void aVoid) {

            Log.i("datail_data", data.toString());

            TextView tv_detail_SVCNM = (TextView) findViewById(R.id.tv_detail_SVCNM);
            TextView tv_detail_PLACENM = (TextView) findViewById(R.id.tv_detail_PLACENM);
            TextView tv_detail_PAYNM = (TextView) findViewById(R.id.tv_detail_PAYNM);
            TextView tv_detail_TELNO = (TextView) findViewById(R.id.tv_detail_TELNO);
            TextView tv_detail_ACCESS = (TextView) findViewById(R.id.tv_detail_ACCESS);
            TextView tv_detail_RCEPTMTH = (TextView) findViewById(R.id.tv_detail_RCEPTMTH);
            TextView tv_detail_RCEPTDATE = (TextView) findViewById(R.id.tv_detail_RCEPTDATE);
            TextView tv_detail_SVCDATE = (TextView) findViewById(R.id.tv_detail_SVCDATE);

  //          ImageView iv_detail_SVCSTTUS = (ImageView) findViewById(R.id.iv_detail_SVCSTTUS);

            try {
                tv_detail_SVCNM.setText( data.get("SVCNM").toString() );
                tv_detail_PLACENM.setText( data.get("ADRES").toString() );
                tv_detail_PAYNM.setText( data.get("PAYAT").toString() );
                tv_detail_TELNO.setText( data.get("TELNO").toString() );
                tv_detail_ACCESS.setText( data.get("SELMTHDCODE_NM").toString() + " " + String.valueOf( (int) Double.parseDouble(data.get("ONEREQMXMPR").toString())) + data.get("UNICODE_NM").toString() );
                tv_detail_RCEPTMTH.setText( data.get("RCEPTMTH_NM").toString() );
                tv_detail_RCEPTDATE.setText( data.get("RCEPTBEGDT").toString() + " ~ " + data.get("RCEPTENDDT").toString() );
                tv_detail_SVCDATE.setText( data.get("SVCBEGINDT").toString() + " ~ " + data.get("SVCENDDT").toString() );
                /*
                if ( data.get("SVCSTTUS_NM").toString().equals("접수중") ) {
                    iv_detail_SVCSTTUS.setImageResource(R.drawable.baseline_check_circle_outline_black_18dp);
                }
                else {
                    iv_detail_SVCSTTUS.setImageResource(R.drawable.baseline_error_outline_black_18dp);
                }
*/

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
                data = (JSONObject) jsonObj.getJSONObject("ListPublicReservationDetail").getJSONArray("row").get(0);

                URL JSON_URL1 = new URL("http://10.0.2.2:8080/public/service/" + SVCID);

                //Log.d("JSON_URL", JSON_URL1.toString());

                HttpURLConnection conn1 = (HttpURLConnection) JSON_URL1.openConnection();
                InputStream is1 = conn1.getInputStream();
                BufferedReader br1 = new BufferedReader(new InputStreamReader(is1, "UTF-8"));
                strJson = br1.readLine();

                //Log.i("strJson", strJson);

                data1 = new JSONObject(strJson);

            } catch (Exception e) {
                Log.d("strJson", "실패");
            }

            return null;
        }
    }
    public void toggleBookmark()
    {
        File file = new File(getFilesDir(), "user_bookmark.txt");
        FileReader fr = null;
        FileWriter fw = null;
        BufferedReader br = null;
        String data;
        String before = "";
        char ch;


        if( file.exists() == true ) {
            boolean flags = false;

            try {
                fr = new FileReader(file);
                br = new BufferedReader(fr);

                while ((data = br.readLine()) != null) {
                    if( data.equals(SVCID) ) {
                        flags = true;
                    }
                    else {
                        before += data + "\n";
                    }
                }
                Log.i("ch", "hello");
                fr.close();

                fw = new FileWriter(file);
                if( flags )
                {
                    fw.write(before);
                    if( isStarted ) {
                        Toast.makeText(getApplicationContext(), "즐겨찾기가 해제되었습니다", Toast.LENGTH_SHORT).show();
                    }
                    btnBookmark.setImageResource(R.drawable.bookmark_off);

                }
                else {
                    fw.write(before + SVCID);
                    if( isStarted ) {
                        Toast.makeText(getApplicationContext(), "즐겨찾기가 등록되었습니다", Toast.LENGTH_SHORT).show();
                    }
                    btnBookmark.setImageResource(R.drawable.bookmark_on);
                }
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                fw = new FileWriter(file);

                fw.write(SVCID);

                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
