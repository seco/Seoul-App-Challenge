package com.sejongsoftware.seoulappproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by choyoushin on 2018. 7. 10..
 */

public class ServicesListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    int layout;
    ArrayList<Item> alist;
    Bitmap bitmap;

    public ServicesListAdapter(Context context, int layout, ArrayList<Item> alist)
    {
        this.context = context;
        this.layout = layout;
        this.alist = alist;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return alist.size();
    }

    @Override
    public Object getItem(int position) {
        return alist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) convertView = inflater.inflate(layout, parent, false);

        TextView tv_item_name = (TextView) convertView.findViewById(R.id.item_label);
        TextView tv_item_state = (TextView) convertView.findViewById(R.id.item_state);
        ImageView tv_item_img = (ImageView) convertView.findViewById(R.id.item_img);
        LinearLayout item_layout = (LinearLayout) convertView.findViewById(R.id.item_layout);

        tv_item_name.setText(alist.get(position).getSVCNM());
        tv_item_state.setText(alist.get(position).getSVCSTATNM());

        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(alist.get(position).getIMGURL());

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        mThread.start();

        try {
            mThread.join();
            tv_item_img.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
