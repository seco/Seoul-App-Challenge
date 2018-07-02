package com.sejongsoftware.seoulappproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by choyoushin on 2018. 7. 2..
 */

public class LoginActivity extends Activity {
    EditText Edit_ID, Edit_PASS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Edit_ID = (EditText) findViewById(R.id.input_id);
        Edit_PASS = (EditText) findViewById(R.id.input_pass);
    }

    public void tryLogin(View v)
    {
        String ID = Edit_ID.getText().toString();
        String PASS = Edit_PASS.getText().toString();

        try {
            URL obj = new URL("http://localhost:8080/public");
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        } catch (Exception e) {
            Log.i("Web", "실패");
        }

        Log.i("LOGIN_INFO", ID);
        Log.i("LOGIN_INFO", PASS);
    }

    public void goToRegister(View v)
    {
        Intent intent = new Intent( getApplicationContext(), RegisterActivity.class );
        startActivity(intent);
    }

}
