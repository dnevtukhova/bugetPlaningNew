package com.dn_evtukhova.mainjournal1.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dn_evtukhova.mainjournal1.R;
import com.dn_evtukhova.mainjournal1.db.BugetPlaningContract.Categories;
import com.dn_evtukhova.mainjournal1.db.BugetPlaningContract.Consumption;
import com.dn_evtukhova.mainjournal1.db.BugetPlaningContract.BugetOnCategory;
import com.dn_evtukhova.mainjournal1.db.BugetPlaningContract.BugetAll;




/**
 * Created by 1 on 24.01.2018.
 */

public class BugetPlaningDBHelper extends SQLiteOpenHelper {
    /**
     * Имя файла базы данных
     */
    private static final String DATABASE_NAME = "bugetPlaning.db";

    /**
     * Версия базы данных. При изменении схемы увеличить на единицу
     */
    private static final int DATABASE_VERSION = 4;

    /**
     * Конструктор
     */
    String [] categories = {"Продукты","Коммунальные платежи","Бытовая химия","Одежда","Транспорт","Арендная плата","Здоровье","Развлечения","Подарки","Связь","Питомцы","Косметика","Прочее"};

    /**
     * Конструктор
     */
    public BugetPlaningDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Строка для создания таблицы CATEGORIES
    String SQL_CREATE_TABLE_CATEGORIES = "CREATE TABLE " + Categories.TABLE_NAME + " ("
            + BugetPlaningContract.Categories._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Categories.COLUMN_CATEGORY_IMG + " INTEGER NOT NULL, "
            + Categories.COLUMN_CATEGORY_NAME + " TEXT NOT NULL);";

    // Строка для создания таблицы CONSUMPTION
    String SQL_CREATE_TABLE_CONSUMPTION = "CREATE TABLE " + Consumption.TABLE_NAME + " ("
            + BugetPlaningContract.Consumption._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Consumption.COLUMN_CATEGORY_ID + " INTEGER NOT NULL, "
            + Consumption.COLUMN_CONSUMPTION_AMOUNT + " INTEGER NOT NULL, "
            + Consumption.COLUMN_CONSUMPTION_DATE + " TEXT NOT NULL, "
            + Consumption.COLUMN_CONSUMPTION_COMMENT + " TEXT NOT NULL);";

    // Строка для создания таблицы BUGETONCATEGORY
    String SQL_CREATE_TABLE_BUGETONCATEGORY = "CREATE TABLE " + BugetOnCategory.TABLE_NAME + " ("
            + BugetPlaningContract.BugetOnCategory._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BugetOnCategory.COLUMN_BUGETONCATEGORY_CATEGORY_ID + " INTEGER NOT NULL, "
            + BugetOnCategory.COLUMN_BUGET_ID + " INTEGER NOT NULL, "
            + BugetOnCategory.COLUMN_AMOUNT_BUGET_CATEGORY + " INTEGER NOT NULL);";

    // Строка для создания таблицы BUGETALL
    String SQL_CREATE_TABLE_BUGETALL = "CREATE TABLE " + BugetAll.TABLE_NAME + " ("
            + BugetPlaningContract.BugetAll._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BugetAll.COLUMN_AMOUNT_BUGETALL + " INTEGER NOT NULL, "
            + BugetAll.COLUMN_BUGETALL_DATE_BEGIN + " TEXT NOT NULL, "
            + BugetAll.COLUMN_BUGETALL_DATE_END + " TEXT NOT NULL);";



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_CATEGORIES);
        db.execSQL(SQL_CREATE_TABLE_CONSUMPTION);
        db.execSQL(SQL_CREATE_TABLE_BUGETONCATEGORY);
        db.execSQL(SQL_CREATE_TABLE_BUGETALL);


        // заполним таблицу CATEGORIES
        ContentValues cv = new ContentValues();
        for (int i = 0; i < 13; i++) {
            cv.put(Categories.COLUMN_CATEGORY_NAME, categories[i]);
            cv.put(Categories.COLUMN_CATEGORY_IMG, R.mipmap.ic_launcher);
            db.insert(Categories.TABLE_NAME, null, cv);


        }



    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Categories.TABLE_NAME);
        db.execSQL("drop table if exists " + Consumption.TABLE_NAME);
        db.execSQL("drop table if exists " + BugetOnCategory.TABLE_NAME);
        db.execSQL("drop table if exists " + BugetAll.TABLE_NAME);

        onCreate(db);

    }




}
