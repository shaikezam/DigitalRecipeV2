package com.example.shayzambrovski.digitalrecipe;

/**
 * Created by Shay Zambrovski on 07/09/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "LogInDataBase";

    // Tables name
    private static final String LOG_IN_TABLE = "tableOfLogIn";

    // Users Table Columns names
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_IS_LOG_IN = "password";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        SQLiteDatabase db = this.getWritableDatabase();

        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + LOG_IN_TABLE + "("
                + KEY_IS_LOG_IN + " INTEGER," + KEY_USER_NAME + " VARCHAR" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + LOG_IN_TABLE + "("
                + KEY_IS_LOG_IN + " INTEGER," + KEY_USER_NAME + " VARCHAR" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + LOG_IN_TABLE);
        // Create tables again
        onCreate(db);
    }
    // Adding new user
    long logInUser(String sUserName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sOldLogInUser = getLogInUser();
        if(sOldLogInUser != null) {
            ContentValues values = new ContentValues();
            values.put(KEY_USER_NAME, sOldLogInUser); // Contact Name
            values.put(KEY_IS_LOG_IN, 0); // Contact Phone
            long number = db.insert(LOG_IN_TABLE, null, values);
            db.close(); // Closing database connection
        }
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, sUserName); // Contact Name
        values.put(KEY_IS_LOG_IN, 1); // Contact Phone
        long number = db.insert(LOG_IN_TABLE, null, values);
        db.close(); // Closing database connection
        return number;
    }

    long logOutUser(String sUserName) {

        String countQuery = "SELECT  * FROM " + LOG_IN_TABLE + " where " + KEY_IS_LOG_IN + " = '" + 1 + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

        // return count
        int i = cursor.getCount();

        ContentValues values = new ContentValues();
        values.put(KEY_IS_LOG_IN, 0);
        final String[] whereArgs = { sUserName };
        db.update(LOG_IN_TABLE, values, KEY_USER_NAME + "=?", whereArgs);

        db = this.getReadableDatabase();
        cursor = db.rawQuery(countQuery, null);
        i = cursor.getCount();

        db.close(); // Closing database connection
        // Closing database connection
        return 3;
    }

    String getLogInUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultSet = null;
        try{
            resultSet = db.rawQuery("SELECT * FROM " + LOG_IN_TABLE + " where " + KEY_IS_LOG_IN + " = '" + 1 + "';",null);
        }catch(Exception e) {
            Log.e("Error: ", e.toString());
        }
        if(resultSet.getCount() == 0 || resultSet == null) {
            return null;
        }

        if (resultSet != null)
            resultSet.moveToFirst();
        return resultSet.getString(resultSet.getColumnIndex(KEY_USER_NAME));
    }

    public void deleteDB() {
        //String countQuery = "DELETE  * FROM " + USER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        //db.execSQL("delete from "+ USER_TABLE);
        db.execSQL("DROP TABLE " + LOG_IN_TABLE);
    }

}