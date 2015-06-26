package com.nvurgaft.redmonk.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Koby on 26-Jun-15.
 */
public class SqlAccess extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RedMonk";
    private static final int DATABASE_VERSION = 1;
    private static final String USER_TABLE = "users";
    private static final String DAILY_CONSUMPTION_TABLE = "daily_consumption";
    private static final String CONTACTS_TABLE = "emergency_contacts";

    private static final String USER_NAME = "name";
    private static final String USER_GENDER = "gender";
    private static final String USER_HEIGHT = "height";
    private static final String USER_WEIGHT = "weight";

    private static final String DC_DATE = "date";
    private static final String CALORIES = "calories";
    private static final String CARBS = "carbs";
    private static final String PROTEINS = "proteins";
    private static final String FATS = "fats";

    private static final String CONTACT_NAME = "";
    private static final String CONTACT_ROLE = "";
    private static final String FIRST_NUMBER = "";
    private static final String SECOND_NUMBER = "";
    private static final String THIRD_NUMBER = "";


    public SqlAccess(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create the users table
        db.execSQL("CREATE TABLE " + USER_TABLE + " (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        USER_NAME + " TEXT NOT NULL, " +
                        USER_HEIGHT + " INTEGER NOT NULL " +
                        USER_WEIGHT + " INTEGER NOT NULL " +
                        USER_GENDER + " TEXT NOT NULL" +
                        ");"
        );

        // create the daily consumption table
        db.execSQL("CREATE TABLE " + DAILY_CONSUMPTION_TABLE + " (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DC_DATE + " TEXT NOT NULL, " +
                        CALORIES + " INTEGER " +
                        CARBS + " INTEGER " +
                        PROTEINS + " INTEGER " +
                        FATS + " INTEGER NOT NULL" +
                        ");"
        );

        // create the contacts table
        db.execSQL("CREATE TABLE " + CONTACTS_TABLE + " (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        USER_NAME + " TEXT NOT NULL, " +
                        CONTACT_NAME + " TEXT NOT NULL, " +
                        CONTACT_ROLE + " INTEGER " +
                        FIRST_NUMBER + " INTEGER " +
                        SECOND_NUMBER + " INTEGER " +
                        THIRD_NUMBER + " INTEGER" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Inserts a new user
     * @param db
     */
    public void insertNewUser(SQLiteDatabase db) {

    }

    /**
     * Gets a user by him name
     * @param db
     * @param name
     */
    public void getUser(SQLiteDatabase db, String name) {

    }

    /**
     * Gets all users
     * @param db
     */
    public void getAllUsers(SQLiteDatabase db) {

    }

    /**
     * Removes a user by his name
     * @param db
     * @param name
     */
    public void removeUser(SQLiteDatabase db, String name) {

    }
}
