package com.sejongsoftware.seoulappproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

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
                        Service = "체육시설";
                        break;
                    case R.id.btnInstitution:
                        Service = "시설대관";
                        break;
                    case R.id.btnEducation:
                        Service = "교육";
                        break;
                    case R.id.btnCulture:
                        Service = "문화행사";
                        break;
                    case R.id.btnMedical:
                        Service = "진료";
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
        Button btnBookmark = (Button) findViewById(R.id.btnBookmark);

        btnSport.setOnClickListener(onClickListener);
        btnInstitution.setOnClickListener(onClickListener);
        btnEducation.setOnClickListener(onClickListener);
        btnCulture.setOnClickListener(onClickListener);
        btnMedical.setOnClickListener(onClickListener);

        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), BookmarkActivity.class );
                startActivity(intent);
            }
        });
    }
}
