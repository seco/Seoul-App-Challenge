package com.sejongsoftware.seoulappproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by choyoushin on 2018. 7. 14..
 */

public class DetailActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Log.i("detail", intent.getStringExtra("SVCID"));
    }

    public class GetSVCdetail extends AsyncTask<Void, Void, Void>
    {
        String API_KEY = "414f414a6963686f33316d65547677";

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
