package com.sejongsoftware.seoulappproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by choyoushin on 2018. 7. 2..
 */

public class RegisterActivity extends Activity {
    private String SCHOOL;
    private Spinner spinner;
    private EditText edit_id, edit_pass, edit_confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_school);

        spinner = (Spinner) findViewById(R.id.select_school_list);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.school_list, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }

    public void school_ok(View v)
    {
        SCHOOL = spinner.getSelectedItem().toString();

        setContentView(R.layout.activity_register_account);
    }

    public void tryRegister(View v)
    {
        edit_id = (EditText) findViewById(R.id.register_id);
        edit_pass = (EditText) findViewById(R.id.register_pass);
        edit_confirm = (EditText) findViewById(R.id.register_pass_confirm);

        String id = edit_id.getText().toString();
        String pass = edit_pass.getText().toString();
        String confirm = edit_confirm.getText().toString();

        if ( !pass.equals(confirm) ) {
            Toast.makeText( getApplicationContext(), "비밀번호가 서로 다릅니다", Toast.LENGTH_SHORT ).show();
            return;
        }

        Log.i("tryRegister_school", SCHOOL);
        Log.i("tryRegister_id", id);
        Log.i("tryRegister_pass", pass);

        try {
            URL url = new URL("http://localhost:8080/public/register");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if( !(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) ) {
                Toast.makeText(getApplicationContext(), "통신 에러", Toast.LENGTH_SHORT).show();
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            Log.i("Web : register", br.readLine().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
