package com.samsung.wfapidemo;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by gurunath on 8/30/17.
 */

public class CustomList extends ArrayAdapter<String>{

    private String[] time;
    private String[] summary;
    private String[] icon;

    private String mTime;

    private Activity context;


    public CustomList(Activity context, String[] time, String[] summary, String[] icon) {
        super(context, R.layout.list_view_layout, time);
        this.context = context;
        this.time = time;
        this.summary = summary;
        this.icon = icon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_view_layout, null, true);

        TextView textViewTime = (TextView) listViewItem.findViewById(R.id.textViewTime);
        TextView textViewSummary = (TextView) listViewItem.findViewById(R.id.textViewSummary);
        TextView textViewIcon = (TextView) listViewItem.findViewById(R.id.textViewIcon);

        mTime = DateTimeConverter.convertTime(Long.parseLong(time[position]));
        textViewTime.setText(mTime);
        textViewSummary.setText(summary[position]);
        textViewIcon.setText(icon[position]);

        return listViewItem;


    }
}
