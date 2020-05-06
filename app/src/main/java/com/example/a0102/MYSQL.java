package com.example.a0102;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class MYSQL extends SQLiteOpenHelper {
    public MYSQL(Context context) {
        super(context, "myDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
                db.execSQL("create table mytable ("
                        + "id integer primary key autoincrement,"
                        + "image text,"
                        + "email text,"
                        + "day text,"
                        + "month text,"
                        + "year text,"
                        + "gobad text,"
                        + "name text" + ");");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
