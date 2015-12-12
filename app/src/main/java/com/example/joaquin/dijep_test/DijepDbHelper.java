package com.example.joaquin.dijep_test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by joaquin on 12/12/2015.
 */
public class DijepDbHelper extends SQLiteOpenHelper {

    //Note: A better solution would be to use a contract class for the constants.
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "DijepDb";

    private static final String COUNTRIES_TABLE_NAME = "Countries";
    private static final String CITIES_TABLE_NAME = "Cities";
    private static final String KEY_ID = "_id";
    private static final String NAME = "name";
    private static final String COUNTRY_FLAG = "flag";
    private static final String CITY_POPULATION = "population";
    private static final String CITY_COUNTRY_ID = "country_id";

    private static final String COUNTRIES_TABLE_CREATE =
            "CREATE TABLE " + COUNTRIES_TABLE_NAME +
            " (" +
            KEY_ID + " INTEGER PRIMARY KEY, " +
            NAME + " TEXT NOT NULL, " +
            COUNTRY_FLAG + " TEXT NOT NULL);";

    private static final String CITIES_TABLE_CREATE = "CREATE TABLE " + CITIES_TABLE_NAME +
            " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT NOT NULL, " +
            CITY_POPULATION + " TEXT NOT NULL, " +
            CITY_COUNTRY_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY (" + CITY_COUNTRY_ID + ") REFERENCES " + COUNTRIES_TABLE_NAME + " ("+KEY_ID+"));";

    private static final String COUNTRIES_INITIAL_DATA = "INSERT INTO " + COUNTRIES_TABLE_NAME + "(" + KEY_ID + ", " + NAME + ", " + COUNTRY_FLAG +
            " ) VALUES(1,'Argentina','argentina'),(2,'Australia','australia'),(3,'Brazil','brazil'),(4,'Canada','canada'),(5,'China','china'),(6,'Finland','finland'),(7,'France','france'),(8,'Germany','germany'),(9,'Ireland','ireland'),(10,'Italy','italy')";


    public DijepDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COUNTRIES_TABLE_CREATE);
        db.execSQL(CITIES_TABLE_CREATE);
        db.execSQL(COUNTRIES_INITIAL_DATA);

        for(int i=1;i<=10;i++){
            for(int j=1;j<=15;j++) {
                String name = "Country " + i + ", City " + j;
                String population = "Population: 100" + j;
                db.execSQL("INSERT INTO " + CITIES_TABLE_NAME + "(" + NAME + ", " + CITY_POPULATION + ", " + CITY_COUNTRY_ID + " ) VALUES('" + name + "', '" + population + "', " + i + ")");
            }
        }
        System.out.println("Database created!!!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + COUNTRIES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CITIES_TABLE_NAME);

        onCreate(db);
    }
}
