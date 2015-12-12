package com.example.joaquin.dijep_test;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by joaquin on 12/12/2015.
 */
public class CityCursorAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    public CityCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.list_item_city, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView cityName = (TextView) view.findViewById(R.id.list_item_cityName);
        TextView cityPopulation = (TextView) view.findViewById(R.id.list_item_cityPopulation);

        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String population = cursor.getString(cursor.getColumnIndex("population"));

        // Populate fields with extracted properties
        cityName.setText(name);
        cityPopulation.setText(population);

        //Different colours for odd and even elements
        if(cursor.getPosition()%2==0) {
            view.setBackgroundColor(context.getResources().getColor(R.color.listItems));
        }
    }
}
