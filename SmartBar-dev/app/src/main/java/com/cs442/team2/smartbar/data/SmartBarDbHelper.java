package com.cs442.team2.smartbar.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


//import static com.cs442.team2.smartbar.data.SmartBarReaderContract.WorkoutReader.COLUMN_NAME_EXERCISE;
import static com.cs442.team2.smartbar.data.SmartBarReaderContract.WorkoutReader;
import static com.cs442.team2.smartbar.data.SmartBarReaderContract.WorkoutReader.COLUMN_NAME_EXERCISE;

/**
 * Created by SumedhaGupta on 10/30/16.
 */

/**********************************
 * Name : SmartBarDbHelper
 *
 * Use: This class is for defining the database and creating the tables
 ***********************************/

public class SmartBarDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "smartbar.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    /*
    SQL Create Entries
     */
    /* creating user table*/
    private static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + SmartBarReaderContract.WorkoutReader.USERS_TABLE_NAME + " (" +
                    WorkoutReader.COLUMN_NAME_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    WorkoutReader.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                    WorkoutReader.COLUMN_NAME_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                    WorkoutReader.COLUMN_NAME_HEIGHT + " INTEGER," +
                    WorkoutReader.COLUMN_NAME_WEIGHT + " INTEGER," +
                    WorkoutReader.COLUMN_NAME_DOB + TEXT_TYPE + COMMA_SEP +
                    WorkoutReader.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    WorkoutReader.COLUMN_NAME_PASSWORD + TEXT_TYPE + ")";

    /* creating workout table*/
    private static final String CREATE_WORKOUTS_TABLE =
            "CREATE TABLE " + WorkoutReader.WORKOUTS_TABLE_NAME + " (" +
                    WorkoutReader.COLUMN_NAME_WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    WorkoutReader.COLUMN_NAME_WORKOUT_DATE + TEXT_TYPE + COMMA_SEP +
                    WorkoutReader.COLUMN_NAME_START_TIME + TEXT_TYPE + COMMA_SEP +
                    WorkoutReader.COLUMN_NAME_END_TIME +  TEXT_TYPE + COMMA_SEP +
                    WorkoutReader.COLUMN_NAME_REPS + " INTEGER," +
                    WorkoutReader.COLUMN_NAME_SETS + " INTEGER," +
                    WorkoutReader.COLUMN_NAME_WORKOUT_WEIGHT + " INTEGER," +
                    COLUMN_NAME_EXERCISE +  TEXT_TYPE + COMMA_SEP +
                    WorkoutReader.COLUMN_NAME_WORKOUT_USER_ID + " INTEGER, " +
                    "FOREIGN KEY(" + WorkoutReader.COLUMN_NAME_WORKOUT_USER_ID + ")  REFERENCES "+
                    WorkoutReader.USERS_TABLE_NAME + "("+WorkoutReader.COLUMN_NAME_USER_ID+")"  +" )";

    /* creating calendar table*/
    private static final String CREATE_NOTES_TABLE =
            "CREATE TABLE " + WorkoutReader.NOTES_TABLE_NAME + " (" +
                    WorkoutReader.COLUMN_NAME_NOTE_ID + " INTEGER PRIMARY KEY," +
                    WorkoutReader.COLUMN_NAME_CALENDAR_DATE + TEXT_TYPE + COMMA_SEP +
                    WorkoutReader.COLUMN_NAME_CALENDAR_WORKOUT_ID + " INTEGER," +
                    WorkoutReader.COLUMN_NAME_NOTES + TEXT_TYPE + COMMA_SEP +
                    "FOREIGN KEY(" + WorkoutReader.COLUMN_NAME_CALENDAR_WORKOUT_ID + ")  REFERENCES "
                    + WorkoutReader.WORKOUTS_TABLE_NAME + "("+WorkoutReader.COLUMN_NAME_WORKOUT_ID+")"  +" )";

    /*
   SQL Delete Entries
    */
    private static final String DELETE_USERS_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS " + WorkoutReader.USERS_TABLE_NAME;
    private static final String DELETE_WORKOUTS_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS " + WorkoutReader.WORKOUTS_TABLE_NAME;
    private static final String DELETE_NOTES_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS " + WorkoutReader.NOTES_TABLE_NAME;


    public SmartBarDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

//        db.execSQL(CREATE_USERS_TABLE);
   //     db.execSQL(CREATE_WORKOUTS_TABLE);
   //     db.execSQL(CREATE_NOTES_TABLE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DELETE_USERS_TABLE_IF_EXISTS);
        db.execSQL(DELETE_WORKOUTS_TABLE_IF_EXISTS);
        db.execSQL(DELETE_NOTES_TABLE_IF_EXISTS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
