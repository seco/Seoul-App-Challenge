package com.sejongsoftware.seoulappproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by choyoushin on 2018. 7. 22..
 */

public class BookmarkActivity extends Activity {
    ListView bookmarkList;
    ArrayList<Item> Items;
    ServicesListAdapter ca;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        bookmarkList = (ListView) findViewById(R.id.list_bookmark);

        BookmarkListTask bookmarkListTask = new BookmarkListTask();
        bookmarkListTask.execute();

        Items = new ArrayList<Item>();
        ca = new ServicesListAdapter(getApplicationContext(), R.layout.services_list_item, Items);
        bookmarkList.setAdapter(ca);

        bookmarkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent_detail = new Intent( getApplicationContext(), DetailActivity.class );
                intent_detail.putExtra("SVCID", Items.get(position).getSVCID().toString());
                startActivity(intent_detail);
                finish();
            }
        });
    }

    public class BookmarkListTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPostExecute(Void aVoid) {
            ca.notifyDataSetChanged();
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            File file = new File(getFilesDir(), "user_bookmark.txt");
            FileReader fr = null;
            ArrayList<String> svcids = new ArrayList();

            try {
                fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String str = "";

                while ( (str = br.readLine()) != null ) {
                    svcids.add(str);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            String SERVICE_URL = "http://sejongsoftware.com/public/service/";

            for (String svcid : svcids )
            {
                try {
                    URL url = new URL(SERVICE_URL + svcid);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    InputStream is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                    String strJson = br.readLine();

                    JSONObject jsonObj = new JSONObject(strJson);

                    Item item = new Item(
                            jsonObj.get("SVCID").toString(),
                            jsonObj.get("SVCNM").toString(),
                            jsonObj.get("MAXCLASSNM").toString(),
                            jsonObj.get("MINCLASSNM").toString(),
                            jsonObj.get("AREANM").toString(),
                            jsonObj.get("SVCSTATNM").toString(),
                            jsonObj.get("PAYATNM").toString(),
                            jsonObj.get("PLACENM").toString(),
                            jsonObj.get("USETGTINFO").toString()
                    );
                    Items.add(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



            return null;
        }
    }
}
