package com.example.multipleemaillogin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.multipleemaillogin.model.EmailData;

import java.util.ArrayList;

public class EmailDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "emails.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_EMAILS = "emails";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_COLOR = "color";
    private static final String COLUMN_IS_SELECTED = "is_selected"; // New column for selection state

    public EmailDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_EMAILS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_URL + " TEXT, " +
                COLUMN_COLOR + " INTEGER, " +
                COLUMN_IS_SELECTED + " INTEGER DEFAULT 0)"; // Default to not selected (0)
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMAILS);
        onCreate(db);
    }

    public void addEmail(EmailData emailData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, emailData.getName());
        values.put(COLUMN_URL, emailData.getUrl());
        values.put(COLUMN_COLOR, emailData.getColor());
        values.put(COLUMN_IS_SELECTED, emailData.isChecked() ? 1 : 0); // Store checked state
        db.insert(TABLE_EMAILS, null, values);
        db.close();
    }

    public void deleteAllEmails() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMAILS, null, null);
        db.close();
    }

    public ArrayList<EmailData> getAllEmails() {
        ArrayList<EmailData> emailList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EMAILS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String url = cursor.getString(cursor.getColumnIndex(COLUMN_URL));
                int color = cursor.getInt(cursor.getColumnIndex(COLUMN_COLOR));
                boolean isChecked = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_SELECTED)) == 1; // Check if selected

                EmailData emailData = new EmailData(id, color, name, url);
                emailData.setChecked(isChecked); // Set checked state
                emailList.add(emailData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return emailList;
    }

    // Method to retrieve selected emails
    public ArrayList<EmailData> getSelectedEmails() {
        ArrayList<EmailData> emailList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EMAILS, null, COLUMN_IS_SELECTED + " = 1", null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String url = cursor.getString(cursor.getColumnIndex(COLUMN_URL));
                int color = cursor.getInt(cursor.getColumnIndex(COLUMN_COLOR));
                emailList.add(new EmailData(id, color, name, url));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return emailList;
    }

    // Method to update selected state of an email
    public void updateEmailSelection(int id, boolean isSelected) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_SELECTED, isSelected ? 1 : 0); // Store 1 for true and 0 for false
        db.update(TABLE_EMAILS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}


