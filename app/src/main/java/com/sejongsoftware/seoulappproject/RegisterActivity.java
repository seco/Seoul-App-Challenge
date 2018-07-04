package com.sejongsoftware.seoulappproject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

        /*
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
        */

        HttpController httpController = new HttpController();
        httpController.execute(id, pass);
    }

    class HttpController extends AsyncTask<String, Void, Void> {
        HttpClient httpclient = new DefaultHttpClient();
        String url = "http://10.0.2.2:8080/public/auth/register";
        HttpPost httppost = new HttpPost(url);
        HttpResponse response;
        String result;

        @Override
        protected void onPostExecute(Void aVoid) {
            HttpEntity resEntity = response.getEntity();
            String result = "";
            try {
                result = EntityUtils.toString(resEntity);
            } catch( Exception e ) {

            }
            Log.i("response", result );


            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... strings) {

            try {
                // 아래처럼 적절히 응용해서 데이터형식을 넣으시고

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("id", strings[0]));
                nameValuePairs.add(new BasicNameValuePair("pass", strings[1]));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                Log.i("Http", "여기까지 실행됨");
                //HTTP Post 요청 실행
                response = httpclient.execute(httppost);
                Log.i("response", response.toString());
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                Log.i("Http", "안됨");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.i("Http", "안됨");
            } catch (Exception e) {
                Log.i("Http", "안됨");
            }


            return null;
        }
    }

    private static String convertStreamToString(InputStream is)

    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                is.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }



    public void sendDataToServer()
    {
        String URL = "http://localhost:8080/public";
    }
}
