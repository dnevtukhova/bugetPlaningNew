package com.dn_evtukhova.mainjournal1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 1 on 11.01.2018.
 */

public class DBHelper {

    public static final int DATABASE_VERSOIN = 9;
    public static final String DATABASE_NAME = "incomeAndExpensesDB";



    public static final String TABLE_CONTACTS = "categories";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "nameCategory";
    public static final String KEY_IMG = "categoryImg";

    String [] categories = {"Продукты","Коммунальные платежи","Бытовая химия","Одежда","Транспорт","Арендная плата","Здоровье","Развлечения","Подарки","Связь","Питомцы","Косметика","Прочее"};


    private static final String DB_CREATE =
            "create table " + TABLE_CONTACTS + "(" +
            KEY_ID + " integer primary key autoincrement, " +
            KEY_NAME + " text, " +
            KEY_IMG + " integer " + ");";

/*

    public static final String TABLE_EXPEDITURE = "expediture";
    public static final String KEY_ID_EXPEDITURE = "_id";
    public static final String KEY_NAME_EXPEDITURE = "nameExpediture";
    public static final String KEY_CATEGORY = "categoryId";
    public static final String KEY_DATE = "dateExpediture";

    private static final String CREATE_TABLE_EXPEDITURE =
            "create table " + TABLE_EXPEDITURE + "(" +
                    KEY_ID_EXPEDITURE + " integer primary key autoincrement, " +
                    KEY_NAME_EXPEDITURE + " text, " +
                    KEY_CATEGORY + " text, " +
                    KEY_DATE + " text " + ");";
*/




    private SQLiteDatabase mDB;

    private DBHelperCreateUpgrate mDBHelper;

    private final Context mCtx;

    public DBHelper(Context ctx) {
        this.mCtx = ctx;
    }
    // открыть подключение
    public void open() {
        mDBHelper = new DBHelperCreateUpgrate(mCtx, DATABASE_NAME, null, DATABASE_VERSOIN);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    // получить все данные из таблицы TABLE_CONTACTS
    public Cursor getAllData() {
        return mDB.query(TABLE_CONTACTS, null, null, null, null, null, null);
    }
//получить данные из поля категории
    public Cursor getCategory() {
        return mDB.query(TABLE_CONTACTS, new String[]{"_id", "nameCategory"}, null, null, null, null, null);
    }

    // добавить запись в DB_TABLE
    public void addRec(String txt, int img) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, txt);
        cv.put(KEY_IMG, img);
        mDB.insert(TABLE_CONTACTS, null, cv);
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(TABLE_CONTACTS, KEY_ID + " = " + id, null);
    }


    private class DBHelperCreateUpgrate extends SQLiteOpenHelper
    {


        public DBHelperCreateUpgrate(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
               // this.getWritableDatabase();

            /*Cursor cursor = this.query(TABLE_CONTACTS, null, null, null, null, null, null);
            if (cursor.getCount() == 0) {*/
                ContentValues cv = new ContentValues();
                // заполним таблицу
                for (int i = 0; i < 13; i++) {
                    cv.put("nameCategory", categories[i]);
                    cv.put("CategoryImg", R.mipmap.ic_launcher);
                    db.insert(TABLE_CONTACTS, null, cv);

                    // Log.d("mLog", "id = " + this.getReadableDatabase().insert("categories", null, cv));
                }

            //    db.execSQL(CREATE_TABLE_EXPEDITURE);

        }



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_CONTACTS);

            onCreate(db);

        }
    }

}
