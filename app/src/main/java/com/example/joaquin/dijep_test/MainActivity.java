package com.example.joaquin.dijep_test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private int countrySelected = -1;
        DijepDbHelper dbHelper;
        private SQLiteDatabase db;
        private CountryCursorAdapter mCountryCursorAdapter;
        private CityCursorAdapter mCityCursorAdapter;
        private Cursor cursor;
        private String[] countryFields = {"_id","name","flag"};
        private String[] cityFields = {"name","population"};
        private String[] cityArgs = {String.valueOf(countrySelected)};

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;
            Bundle args = this.getArguments();

            //create database
            dbHelper = new DijepDbHelper(getContext());
            db = dbHelper.getReadableDatabase();//In case we wanted to write on the database, we should use getWritableDatabase().

            if(args.getInt(ARG_SECTION_NUMBER) == 1){
                rootView = inflater.inflate(R.layout.fragment_countries, container, false);
                ListView listViewCountries = (ListView) rootView.findViewById(R.id.listView_countries);

                //query for countries
                cursor = db.query("Countries", countryFields, null, null, null, null, null);

                //create and set adapter for countries
                mCountryCursorAdapter = new CountryCursorAdapter(getContext(),cursor,0);
                listViewCountries.setAdapter(mCountryCursorAdapter);

                //set OnClick event to select the country and save the selection in 'countrySelected'
                listViewCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Cursor c = (Cursor) mCountryCursorAdapter.getItem(position);
                        countrySelected = c.getInt(c.getColumnIndex("_id"));
                    }
                });

                //Button for add a country (not implemented)
                FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_countries);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Add Country", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }
            else{
                rootView = inflater.inflate(R.layout.fragment_cities, container, false);
                ListView listViewCities = (ListView) rootView.findViewById(R.id.listView_cities);
                TextView textView = (TextView) rootView.findViewById(R.id.textView_noCountrySelected);

                //At first no country is selected
                if(countrySelected == -1) {
                    textView.setText(R.string.countryNoSelected);
                }
                else{
                    //if a country is selected remove message
                    textView.setText("");
                    //query for cities
                    cursor = db.query("Cities", cityFields, "country_id=?", cityArgs, null, null, null);
                    //create and set adapter for cities
                    mCityCursorAdapter = new CityCursorAdapter(getContext(), cursor, 0);
                    listViewCities.setAdapter(mCityCursorAdapter);
                }

                //Button for add a city (not implemented)
                FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_cities);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Add City", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages for this example.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "COUNTRIES";
                case 1:
                    return "CITIES";
            }
            return null;
        }
    }
}
