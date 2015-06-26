package com.nvurgaft.redmonk.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nvurgaft.redmonk.Entities.Contact;
import com.nvurgaft.redmonk.Entities.DailyConsumption;
import com.nvurgaft.redmonk.Entities.User;

import java.util.ArrayList;
import java.util.List;

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

    private static final String CDATE = "date";
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
                        USER_GENDER + " TEXT NOT NULL" +
                        USER_HEIGHT + " INTEGER NOT NULL " +
                        USER_WEIGHT + " INTEGER NOT NULL " +
                        ");"
        );

        // create the daily consumption table
        db.execSQL("CREATE TABLE " + DAILY_CONSUMPTION_TABLE + " (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        USER_NAME + " TEXT NOT NULL, " +
                        CDATE + " TEXT NOT NULL, " +
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
        if (newVersion > oldVersion) {
            db.setVersion(newVersion);
        }
    }

    /************************
     *  USERS TABLE METHODS *
     ************************/

    /**
     * Inserts a new user
     *
     * @param db
     * @return the id of the inserted row or -1 if error
     */
    public long insertNewUser(SQLiteDatabase db, User newUser) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, newUser.getName());
        contentValues.put(USER_GENDER, newUser.getGender());
        contentValues.put(USER_HEIGHT, newUser.getHeight());
        contentValues.put(USER_WEIGHT, newUser.getWeight());
        return db.insert(USER_TABLE, null, contentValues);
    }

    /**
     * Gets a user by him name
     *
     * @param db
     * @param name
     * @return the requested user object (if exists)
     */
    public User getUser(SQLiteDatabase db, String name) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + USER_NAME + " = '" + name + "';", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            User user = new User();
            user.setName(cursor.getString(0));
            user.setGender(cursor.getString(1));
            user.setHeight(cursor.getInt(2));
            user.setWeight(cursor.getInt(3));
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }

    /**
     * Gets all users
     *
     * @param db
     * @return all users in database
     */
    public List<User> getAllUsers(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE + ";", null);

        ArrayList<User> users = new ArrayList<>();
        while (cursor.moveToNext()) {
            User user = new User();
            user.setName(cursor.getString(0));
            user.setGender(cursor.getString(1));
            user.setHeight(cursor.getInt(2));
            user.setWeight(cursor.getInt(3));
            users.add(user);
        }
        cursor.close();
        return users;
    }

    /**
     * Removes a user by his name
     *
     * @param db
     * @param name
     * @return returns number of rows removed
     */
    public int removeUser(SQLiteDatabase db, String name) {
        return db.delete(USER_TABLE, USER_NAME + " = '" + name + "'", null);
    }

    /************************************
     *  DAILY CONSUMPTION TABLE METHODS *
     ************************************/

    /**
     * Inserts the daily consumption for the past day
     *
     * @param db
     * @param dailyConsumption
     * @returns the inserted row id, or -1 if error
     */
    public long insertDailyConsumption(SQLiteDatabase db, DailyConsumption dailyConsumption) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(CDATE, dailyConsumption.getDate());
        contentValues.put(CALORIES, dailyConsumption.getCalories());
        contentValues.put(CARBS, dailyConsumption.getCarbs());
        contentValues.put(PROTEINS, dailyConsumption.getProteins());
        contentValues.put(FATS, dailyConsumption.getFats());
        return db.insert(DAILY_CONSUMPTION_TABLE, null, contentValues);
    }

    /**
     * Returns the daily consumption for a provided date for a user
     *
     * @param db
     * @param user
     * @param date
     * @return return the daily consumption for a user for a specific date
     */
    public DailyConsumption getDailyConsumptionForDate(SQLiteDatabase db, String user, String date) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DAILY_CONSUMPTION_TABLE + " WHERE " + USER_NAME + " = '" + user + "' AND " + CDATE + " = '" + date + "';", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            DailyConsumption dailyConsumption = new DailyConsumption();
            dailyConsumption.setName(cursor.getString(0));
            dailyConsumption.setDate(cursor.getInt(1));
            dailyConsumption.setCalories(cursor.getInt(2));
            dailyConsumption.setCarbs(cursor.getInt(3));
            dailyConsumption.setProteins(cursor.getInt(4));
            dailyConsumption.setFats(cursor.getInt(5));
            cursor.close();
            return dailyConsumption;
        }
        cursor.close();
        return null;
    }

    /**
     * Returns all daily consumption dates for a user
     *
     * @param db
     * @param user
     * @return all daily consumption for a user
     */
    public List<DailyConsumption> getAllDailyConsumption(SQLiteDatabase db, String user) {

        Cursor cursor = db.rawQuery("SELECT * FROM " + DAILY_CONSUMPTION_TABLE + " WHERE " + USER_NAME + " = '" + user + "';", null);
        ArrayList<DailyConsumption> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            DailyConsumption dailyConsumption = new DailyConsumption();
            dailyConsumption.setName(cursor.getString(0));
            dailyConsumption.setDate(cursor.getInt(1));
            dailyConsumption.setCalories(cursor.getInt(2));
            dailyConsumption.setCarbs(cursor.getInt(3));
            dailyConsumption.setProteins(cursor.getInt(4));
            dailyConsumption.setFats(cursor.getInt(5));
            list.add(dailyConsumption);
        }
        cursor.close();
        return list;
    }

    /********************************
     *  EMERGENCY CONTACTS METHODS  *
     ********************************/

    /**
     * Inserts a new contact
     *
     * @param db
     * @param newContact
     * @returns the inserted row id, or -1 if error
     */
    public long insertNewContact(SQLiteDatabase db, Contact newContact) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, newContact.getUser());
        contentValues.put(CONTACT_NAME, newContact.getContactName());
        contentValues.put(CONTACT_ROLE, newContact.getContactRole());
        contentValues.put(FIRST_NUMBER, newContact.getFirstNumber());
        contentValues.put(SECOND_NUMBER, newContact.getSecondNumber());
        contentValues.put(THIRD_NUMBER, newContact.getThirdNumber());
        return db.insert(CONTACTS_TABLE, null, contentValues);
    }

    /**
     * Updates an existing contact
     *
     * @param db
     * @param contact
     * @return number of rows updated
     */
    public int updateContact(SQLiteDatabase db, Contact contact) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, contact.getUser());
        contentValues.put(CONTACT_NAME, contact.getContactName());
        contentValues.put(CONTACT_ROLE, contact.getContactRole());
        contentValues.put(FIRST_NUMBER, contact.getFirstNumber());
        contentValues.put(SECOND_NUMBER, contact.getSecondNumber());
        contentValues.put(THIRD_NUMBER, contact.getThirdNumber());
        return db.update(CONTACTS_TABLE, contentValues, CONTACT_NAME + " = '" + contact.getContactName() + "'", null);
    }

    /**
     * Returns all contacts for a user name
     *
     * @param db
     * @param user
     * @return a list of all contacts for a user
     */
    public ArrayList<Contact> getUserContacts(SQLiteDatabase db, String user) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + CONTACTS_TABLE + " WHERE " + USER_NAME + " = '" + user + "';", null);
        ArrayList<Contact> contacts = new ArrayList<>();
        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            contact.setUser(cursor.getString(0));
            contact.setContactName(cursor.getString(1));
            contact.setContactRole(cursor.getString(2));
            contact.setFirstNumber(cursor.getString(3));
            contact.setSecondNumber(cursor.getString(4));
            contact.setThirdNumber(cursor.getString(5));
            contacts.add(contact);
        }
        cursor.close();
        return null;
    }

    /**
     * Removes a contact by the contact name
     *
     * @param db
     * @param contactName
     * @return number of rows deleted
     */
    public int removeContactByName(SQLiteDatabase db, String contactName) {
        return db.delete(CONTACTS_TABLE, CONTACT_NAME + " = '" + contactName + "'", null);
    }

    /**
     * Removes all contacts for the user
     *
     * @param db
     * @param user
     * @return number of rows deleted
     */
    public int removeAllContactsForUser(SQLiteDatabase db, String user) {
        return db.delete(CONTACTS_TABLE, USER_NAME + " = '" + user + "'", null);
    }

}
