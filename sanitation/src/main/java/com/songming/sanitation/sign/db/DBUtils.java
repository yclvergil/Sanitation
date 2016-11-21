package com.songming.sanitation.sign.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class DBUtils {
    private static MyDBOpenHelper dbHelper;
    private static SQLiteDatabase db;
    private static DBUtils dbUtils;

    public DBUtils(Context context) {
        if (dbHelper == null) {
            dbHelper = new MyDBOpenHelper(context.getApplicationContext());
        }
    }

    public static DBUtils instance(Context context) {
        if (dbUtils == null) {
            dbUtils = new DBUtils(context);
        }
        return dbUtils;
    }

    public boolean save(SignBean bean) {
        //String sql = "INSERT INTO sign(name,address,signDate,signTime,signLongitude,signLatitude,signType) VALUES(?,?,?,?,?,?,?)";
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", bean.getName());
        values.put("address", bean.getAddress());
        values.put("signDate", bean.getDate());
        values.put("signTime", bean.getTime());
        values.put("signLongitude", bean.getLongi());
        values.put("signLatitude", bean.getLati());
        values.put("signType", bean.getType());
        long rowid = db.insert("sign", null, values);
        Log.d("DBUtils", "rowid:" + rowid);

        /*db.execSQL(sql, new Object[]{bean.getName(), bean.getAddress(), bean.getDate(), bean.getTime(), bean.getLongi(),
                bean.getLati(), bean.getType()});*/

        return true;

    }

    public List<SignBean> findAll() {
        List<SignBean> list = new ArrayList<>();
        SignBean bean = null;
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from sign", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            String signDate = cursor.getString(3);
            String signTime = cursor.getString(4);
            double signLongitude = cursor.getDouble(5);
            double signLatitude = cursor.getDouble(6);
            int signType = cursor.getInt(7);
            bean = new SignBean(name, address, signDate, signTime, signLongitude, signLatitude, signType);
            list.add(bean);

        }
        cursor.close();
        return list;

    }

    public List<SignBean> findByType(int type) {
        List<SignBean> list = new ArrayList<>();
        SignBean bean = null;
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from sign where signType=?", new String[]{String.valueOf(type)});
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            String signDate = cursor.getString(3);
            String signTime = cursor.getString(4);
            double signLongitude = cursor.getDouble(5);
            double signLatitude = cursor.getDouble(6);
            int signType = cursor.getInt(7);
            bean = new SignBean(name, address, signDate, signTime, signLongitude, signLatitude, signType);
            list.add(bean);

        }
        cursor.close();
        return list;

    }
}
