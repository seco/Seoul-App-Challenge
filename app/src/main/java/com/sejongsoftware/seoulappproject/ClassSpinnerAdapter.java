package com.sejongsoftware.seoulappproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by choyoushin on 2018. 7. 21..
 */

public class ClassSpinnerAdapter extends BaseAdapter {
    Context context;
    List<String> data;
    LayoutInflater inflater;

    public ClassSpinnerAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(data != null) return data.size();
        else             return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( convertView == null ) {
            convertView = inflater.inflate(R.layout.spinner_class_normal, parent, false);
        }

        if( data != null ) {
            String text = data.get(position);
            ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_class_dropdown, parent, false);
        }

        String text = data.get(position);
        ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
