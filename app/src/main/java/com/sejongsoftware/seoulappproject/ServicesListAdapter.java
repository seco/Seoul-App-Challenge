package com.sejongsoftware.seoulappproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by choyoushin on 2018. 7. 10..
 */

public class ServicesListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    int layout;
    ArrayList<Item> alist;

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
        LinearLayout item_layout = (LinearLayout) convertView.findViewById(R.id.item_layout);

        tv_item_name.setText(alist.get(position).getSVCNM());
        tv_item_state.setText(alist.get(position).getSVCSTATNM());

        return convertView;
    }
}
