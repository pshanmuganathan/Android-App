package com.cs442.team2.smartbar.data;

import android.provider.BaseColumns;

/**
 * Created by SumedhaGupta on 10/30/16.
 */

/**********************************
 Name : SmartBarReaderContract

 Use: This class defines the table contents
 ***********************************/


public class SmartBarReaderContract implements BaseColumns {


    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private SmartBarReaderContract() {}

    /* Inner class that defines the table contents */
    public static class WorkoutReader implements BaseColumns {
        public static final String USERS_TABLE_NAME = "users";
        public static final String WORKOUTS_TABLE_NAME = "workouts";
        public static final String NOTES_TABLE_NAME = "notes";

       //column names for user table
       public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_FIRST_NAME= "first_name";
        public static final String COLUMN_NAME_LAST_NAME = "last_name";
        public static final String COLUMN_NAME_HEIGHT = "height";
        public static final String COLUMN_NAME_WEIGHT = "weight";
        public static final String COLUMN_NAME_DOB = "dob";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PASSWORD = "password";

        //column names for workout table
        public static final String COLUMN_NAME_WORKOUT_ID = "workout_id";
        public static final String COLUMN_NAME_WORKOUT_DATE = "date";
        public static final String COLUMN_NAME_START_TIME= "start";
        public static final String COLUMN_NAME_END_TIME = "end";
        public static final String COLUMN_NAME_WORKOUT_USER_ID = "fk_user_id";
        public static final String COLUMN_NAME_EXERCISE = "exercise";
        public static final String COLUMN_NAME_REPS = "reps";
        public static final String COLUMN_NAME_SETS = "sets";
        public static final String COLUMN_NAME_WORKOUT_WEIGHT = "weight";

        //column names for notes table
        public static final String COLUMN_NAME_CALENDAR_DATE = "calendar_date";
        public static final String COLUMN_NAME_CALENDAR_WORKOUT_ID = "fk_workout_id";
        public static final String COLUMN_NAME_NOTES = "notes";
        public static final String COLUMN_NAME_NOTE_ID = "note_id";


    }
}
