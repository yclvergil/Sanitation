package com.songming.sanitation.sign.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/11/15.
 */

public class MyDBOpenHelper extends SQLiteOpenHelper {

    public MyDBOpenHelper(Context context) {
        super(context, "sign.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS sign(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME VARCHAR(50)," +
                "address VARCHAR(30)," +
                "signDate VARCHAR(20)," +
                "signTime VARCHAR(20)," +
                "signLongitude DOUBLE," +
                "signLatitude DOUBLE," +
                "signType INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
