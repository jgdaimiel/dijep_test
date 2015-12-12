package com.example.joaquin.dijep_test;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by joaquin on 12/12/2015.
 */
public class CountryCursorAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    public CountryCursorAdapter(Context context, Cursor cursor, int flags){
        super (context, cursor, flags);
    }

    // The bindView method is used to bind all data to a given view.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        ImageView countryFlag = (ImageView) view.findViewById(R.id.list_item_countryFlag);
        TextView countryName = (TextView) view.findViewById(R.id.list_item_countryName);

        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String flag = cursor.getString(cursor.getColumnIndex("flag"));

        // Populate fields with extracted properties
        countryName.setText(name);
        int resId = context.getResources().getIdentifier(flag, "drawable", context.getPackageName());
        countryFlag.setImageResource(resId);

        //Different colours for odd and even elements
        if(cursor.getPosition()%2==0) {
            view.setBackgroundColor(context.getResources().getColor(R.color.listItems));
        }
    }

    // The newView method is used to inflate a new view and return it.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.list_item_country, parent, false);
    }
}
