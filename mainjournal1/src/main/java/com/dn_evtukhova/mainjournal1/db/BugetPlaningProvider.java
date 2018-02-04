package com.dn_evtukhova.mainjournal1.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dn_evtukhova.mainjournal1.db.BugetPlaningContract.Categories;
import com.dn_evtukhova.mainjournal1.db.BugetPlaningContract.Consumption;
import com.dn_evtukhova.mainjournal1.db.BugetPlaningContract.BugetOnCategory;
import com.dn_evtukhova.mainjournal1.db.BugetPlaningContract.BugetAll;

import java.util.HashMap;


/**
 * Created by 1 on 24.01.2018.
 */

public class BugetPlaningProvider extends ContentProvider {

    private static final int DATABASE_VERSION = 5;
    private static HashMap<String, String> sCategoriesProjectionMap;
    private static HashMap<String, String> sConsumptionProjectionMap;
    private static HashMap<String, String> sBugetOnCategoryProjectionMap;
    private static HashMap<String, String> sBugetAllProjectionMap;

//Для начала зададим константы, соответствующие возможным типам запроса к нашей БД:
    private static final int CATEGORIES = 101;
    private static final int CATEGORIES_ID = 102;
    private static final int CONSUMPTION = 103;
    private static final int CONSUMPTION_ID = 104;
    private static final int BUGETONCATEGORY = 105;
    private static final int BUGETONCATEGORY_ID = 106;
    private static final int BUGETALL = 107;
    private static final int BUGETALL_ID = 108;
    private static final int URI_RAW_QUERY = 109; // Uri произвольного запроса


    //Затем объявим переменную класса UriMatcher
    private static final UriMatcher sUriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
    static {
//И в static блоке подготовим ее к использованию:
        sUriMatcher.addURI(BugetPlaningContract.CONTENT_AUTHORITY, "categories", CATEGORIES);
        sUriMatcher.addURI(BugetPlaningContract.CONTENT_AUTHORITY, "categories/#", CATEGORIES_ID);
        sUriMatcher.addURI(BugetPlaningContract.CONTENT_AUTHORITY, "consumption", CONSUMPTION);
        sUriMatcher.addURI(BugetPlaningContract.CONTENT_AUTHORITY, "consumption/#", CONSUMPTION_ID);
        sUriMatcher.addURI(BugetPlaningContract.CONTENT_AUTHORITY, "bugetOnCategory", BUGETONCATEGORY);
        sUriMatcher.addURI(BugetPlaningContract.CONTENT_AUTHORITY, "bugetOnCategory/#", BUGETONCATEGORY_ID);
        sUriMatcher.addURI(BugetPlaningContract.CONTENT_AUTHORITY, "bugetAll", BUGETALL);
        sUriMatcher.addURI(BugetPlaningContract.CONTENT_AUTHORITY, "bugetAll/#", BUGETALL_ID);

        sUriMatcher.addURI(BugetPlaningContract.CONTENT_AUTHORITY, "raw", URI_RAW_QUERY);

        //Также нам необходимо задать проекции для выборки столбцов в запросе, они пригодятся нам в методе query():
        //Для проекции по умолчанию мы возьмем весь список столбцов:
        sCategoriesProjectionMap = new HashMap<String, String>();
        for (int i = 0; i < BugetPlaningContract.Categories.DEFAULT_PROJECTION.length; i++) {
            sCategoriesProjectionMap.put(
                    BugetPlaningContract.Categories.DEFAULT_PROJECTION[i],
                    BugetPlaningContract.Categories.DEFAULT_PROJECTION[i]);
        }
        sConsumptionProjectionMap = new HashMap<String, String>();
        for (int i = 0; i < BugetPlaningContract.Consumption.DEFAULT_PROJECTION.length; i++) {
            sConsumptionProjectionMap.put(
                    BugetPlaningContract.Consumption.DEFAULT_PROJECTION[i],
                    BugetPlaningContract.Consumption.DEFAULT_PROJECTION[i]);
        }
        sBugetOnCategoryProjectionMap = new HashMap<String, String>();
        for (int i = 0; i < BugetPlaningContract.BugetOnCategory.DEFAULT_PROJECTION.length; i++) {
            sBugetOnCategoryProjectionMap.put(
                    BugetPlaningContract.BugetOnCategory.DEFAULT_PROJECTION[i],
                    BugetPlaningContract.BugetOnCategory.DEFAULT_PROJECTION[i]);
        }
        sBugetAllProjectionMap = new HashMap<String, String>();
        for (int i = 0; i < BugetPlaningContract.BugetAll.DEFAULT_PROJECTION.length; i++) {
            sBugetAllProjectionMap.put(
                    BugetPlaningContract.BugetAll.DEFAULT_PROJECTION[i],
                    BugetPlaningContract.BugetAll.DEFAULT_PROJECTION[i]);
        }
    }


     private BugetPlaningDBHelper bugetPlaningDbHelper;
//onCreate() - инициализирует ContentProvider.
// Провайдер будет создан как только вы обратитесь к нему с помощью ContentResolver'a
//В методе onCreate() создадим наш DBHelper:
    @Override
    public boolean onCreate() {

        bugetPlaningDbHelper = new BugetPlaningDBHelper(getContext());
        return true;
    }

//query() - извлекает данные из БД, и возвращает их в виде Cursor
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String orderBy = null;
        Cursor c;
        SQLiteDatabase db = bugetPlaningDbHelper.getReadableDatabase();

            switch (sUriMatcher.match(uri)) {
                case CATEGORIES:
                    qb.setTables(Categories.TABLE_NAME);
                    qb.setProjectionMap(sCategoriesProjectionMap);
                    orderBy = Categories.DEFAULT_SORT_ORDER;

                    c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
                   // c.setNotificationUri(getContext().getContentResolver(), uri);
                   // return c;

                    break;
                case CATEGORIES_ID:
                    qb.setTables(Categories.TABLE_NAME);
                    qb.setProjectionMap(sCategoriesProjectionMap);
                    qb.appendWhere(Categories._ID + "=" + uri.getPathSegments().get(Categories.CATEGORIES_ID_PATH_POSITION));
                    orderBy = Categories.DEFAULT_SORT_ORDER;

                    c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
                    //c.setNotificationUri(getContext().getContentResolver(), uri);
                   // return c;
                    break;
                case CONSUMPTION:
                    qb.setTables(Consumption.TABLE_NAME);
                    qb.setProjectionMap(sConsumptionProjectionMap);
                    orderBy = Consumption.DEFAULT_SORT_ORDER;

                    c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
                   // c.setNotificationUri(getContext().getContentResolver(), uri);
                   // return c;
                    break;
                case CONSUMPTION_ID:
                    qb.setTables(Consumption.TABLE_NAME);
                    qb.setProjectionMap(sConsumptionProjectionMap);
                    qb.appendWhere(Consumption._ID + "=" + uri.getPathSegments().get(Consumption.CONSUMPTION_ID_PATH_POSITION));
                    orderBy = Consumption.DEFAULT_SORT_ORDER;

                    c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
                    //c.setNotificationUri(getContext().getContentResolver(), uri);
                   // return c;
                    break;
                case BUGETONCATEGORY:
                    qb.setTables(BugetOnCategory.TABLE_NAME);
                    qb.setProjectionMap(sBugetOnCategoryProjectionMap);
                    orderBy = BugetOnCategory.DEFAULT_SORT_ORDER;
                    c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
                   // c.setNotificationUri(getContext().getContentResolver(), uri);
                   // return c;
                    break;
                case BUGETONCATEGORY_ID:
                    qb.setTables(BugetOnCategory.TABLE_NAME);
                    qb.setProjectionMap(sBugetOnCategoryProjectionMap);
                    qb.appendWhere(BugetOnCategory._ID + "=" + uri.getPathSegments().get(BugetOnCategory.BUGETONCATEGORY_ID_PATH_POSITION));
                    orderBy = BugetOnCategory.DEFAULT_SORT_ORDER;

                    c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
                   // c.setNotificationUri(getContext().getContentResolver(), uri);
                   // return c;
                    break;
                case BUGETALL:
                    qb.setTables(BugetAll.TABLE_NAME);
                    qb.setProjectionMap(sBugetAllProjectionMap);
                    orderBy = BugetAll.DEFAULT_SORT_ORDER;

                    c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
                    //c.setNotificationUri(getContext().getContentResolver(), uri);
                    //return c;
                    break;
                case BUGETALL_ID:
                    qb.setTables(BugetAll.TABLE_NAME);
                    qb.setProjectionMap(sBugetAllProjectionMap);
                    qb.appendWhere(BugetAll._ID + "=" + uri.getPathSegments().get(BugetAll.BUGETALL_ID_PATH_POSITION));
                    orderBy = BugetAll.DEFAULT_SORT_ORDER;

                    c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
                   // c.setNotificationUri(getContext().getContentResolver(), uri);
                    //return c;
                    break;

                case URI_RAW_QUERY:
                    c= db.rawQuery(selection, selectionArgs);
                     break;

                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }
            /*SQLiteDatabase db = bugetPlaningDbHelper.getReadableDatabase();
            Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;
*/
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;

    }

    //getType() - возвращает MIME-тип для заданной content URI
    //В реализации метода getType() мы просто будем возвращать тип данных из нашего ContractClass'a:
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case CATEGORIES:
                return BugetPlaningContract.Categories.CONTENT_TYPE;
            case CATEGORIES_ID:
                return BugetPlaningContract.Categories.CONTENT_ITEM_TYPE;
            case CONSUMPTION:
                return BugetPlaningContract.Consumption.CONTENT_TYPE;
            case CONSUMPTION_ID:
                return BugetPlaningContract.Consumption.CONTENT_ITEM_TYPE;
            case BUGETONCATEGORY:
                return BugetPlaningContract.BugetOnCategory.CONTENT_TYPE;
            case BUGETONCATEGORY_ID:
                return BugetPlaningContract.BugetOnCategory.CONTENT_ITEM_TYPE;
            case BUGETALL:
                return BugetPlaningContract.BugetAll.CONTENT_TYPE;
            case BUGETALL_ID:
                return BugetPlaningContract.BugetAll.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    //insert() - добавляет новые данные в БД, возвращает uri новой записи
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues initialValues) {
       //Добавлять строки можно только в таблицы, поэтому сделаем фильтр для тех Content Uri, которые не подходят:
        if (
                sUriMatcher.match(uri) != CATEGORIES &&
                        sUriMatcher.match(uri) != CONSUMPTION &&
                        sUriMatcher.match(uri) != BUGETONCATEGORY &&
                        sUriMatcher.match(uri) != BUGETALL

                ) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = bugetPlaningDbHelper.getWritableDatabase();
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        }
        else {
            values = new ContentValues();
        }
        long rowId = -1;
        Uri rowUri = Uri.EMPTY;
        //И определим в какую таблицу нужно добавить новые данные:
        switch (sUriMatcher.match(uri)) {
            case CATEGORIES:
                if (values.containsKey(BugetPlaningContract.Categories.COLUMN_CATEGORY_IMG) == false) {
                    values.put(BugetPlaningContract.Categories.COLUMN_CATEGORY_IMG, "");
                }
                if (values.containsKey(Categories.COLUMN_CATEGORY_NAME) == false) {
                    values.put(Categories.COLUMN_CATEGORY_NAME, "");
                }
                rowId = db.insert(BugetPlaningContract.Categories.TABLE_NAME,
                        Categories.COLUMN_CATEGORY_IMG,
                        values);
                if (rowId > 0) {
                    rowUri = ContentUris.withAppendedId(BugetPlaningContract.Categories.CONTENT_ID_URI_BASE, rowId);

                    //строка отвечает за обновление данных в CursorAdapter(и, соответственно, в нашем ListView, где мы его будем использовать).
                    getContext().getContentResolver().notifyChange(rowUri, null);
                }
                break;
            case CONSUMPTION:
                if (values.containsKey(Consumption.COLUMN_CATEGORY_ID) == false) {
                    values.put(Consumption.COLUMN_CATEGORY_ID, "");
                }
                if (values.containsKey(Consumption.COLUMN_CONSUMPTION_DATE) == false) {
                    values.put(Consumption.COLUMN_CONSUMPTION_DATE, "");
                }
                if (values.containsKey(Consumption.COLUMN_CONSUMPTION_AMOUNT) == false) {
                    values.put(Consumption.COLUMN_CONSUMPTION_AMOUNT, "");
                }
                if (values.containsKey(Consumption.COLUMN_CONSUMPTION_COMMENT) == false) {
                    values.put(Consumption.COLUMN_CONSUMPTION_COMMENT, "");
                }
                rowId = db.insert(Consumption.TABLE_NAME,
                        Consumption.COLUMN_CATEGORY_ID,
                        values);
                if (rowId > 0) {
                    rowUri = ContentUris.withAppendedId(Consumption.CONTENT_ID_URI_BASE, rowId);
                    getContext().getContentResolver().notifyChange(rowUri, null);
                }
                break;
            case BUGETONCATEGORY:
                if (values.containsKey(BugetOnCategory.COLUMN_BUGETONCATEGORY_CATEGORY_ID) == false) {
                    values.put(BugetOnCategory.COLUMN_BUGETONCATEGORY_CATEGORY_ID, "");
                }
                if (values.containsKey(BugetOnCategory.COLUMN_AMOUNT_BUGET_CATEGORY) == false) {
                    values.put(BugetOnCategory.COLUMN_AMOUNT_BUGET_CATEGORY, "");
                }
                rowId = db.insert(BugetOnCategory.TABLE_NAME,
                        BugetOnCategory.COLUMN_BUGETONCATEGORY_CATEGORY_ID,
                        values);
                if (rowId > 0) {
                    rowUri = ContentUris.withAppendedId(BugetOnCategory.CONTENT_ID_URI_BASE, rowId);
                    getContext().getContentResolver().notifyChange(rowUri, null);
                }
                break;
            case BUGETALL:
                if (values.containsKey(BugetAll.COLUMN_AMOUNT_BUGETALL_MOUNTH) == false) {
                    values.put(BugetAll.COLUMN_AMOUNT_BUGETALL_MOUNTH, "");
                }
                if (values.containsKey(BugetAll.COLUMN_AMOUNT_BUGETALL_WEEK) == false) {
                    values.put(BugetAll.COLUMN_AMOUNT_BUGETALL_WEEK, "");
                }
                if (values.containsKey(BugetAll.COLUMN_AMOUNT_BUGETALL_YEAR) == false) {
                    values.put(BugetAll.COLUMN_AMOUNT_BUGETALL_YEAR, "");
                }
                if (values.containsKey(BugetAll.COLUMN_AMOUNT_BUGETALL_DAY) == false) {
                    values.put(BugetAll.COLUMN_AMOUNT_BUGETALL_DAY, "");
                }
                rowId = db.insert(BugetAll.TABLE_NAME,
                        BugetAll.COLUMN_AMOUNT_BUGETALL_MOUNTH,
                        values);
                if (rowId > 0) {
                    rowUri = ContentUris.withAppendedId(BugetAll.CONTENT_ID_URI_BASE, rowId);
                    getContext().getContentResolver().notifyChange(rowUri, null);
                }
                break;
        }
        return rowUri;
    }

    //delete() - удаляет данные
    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
            SQLiteDatabase db = bugetPlaningDbHelper.getWritableDatabase();
            String finalWhere;
            int count;
        switch (sUriMatcher.match(uri)) {
            case CATEGORIES:
                count = db.delete(BugetPlaningContract.Categories.TABLE_NAME,where,whereArgs);
                break;
            case CATEGORIES_ID:
                finalWhere = BugetPlaningContract.Categories._ID + " = " + uri.getPathSegments().get(Categories.CATEGORIES_ID_PATH_POSITION);
                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.delete(BugetPlaningContract.Categories.TABLE_NAME,finalWhere,whereArgs);
                break;
            case CONSUMPTION:
                count = db.delete(BugetPlaningContract.Consumption.TABLE_NAME,where,whereArgs);
                break;
            case CONSUMPTION_ID:
                finalWhere = BugetPlaningContract.Consumption._ID + " = " + uri.getPathSegments().get(BugetPlaningContract.Consumption.CONSUMPTION_ID_PATH_POSITION);
                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.delete(BugetPlaningContract.Consumption.TABLE_NAME,finalWhere,whereArgs);
                break;
            case BUGETONCATEGORY:
                count = db.delete(BugetPlaningContract.BugetOnCategory.TABLE_NAME,where,whereArgs);
                break;
            case BUGETONCATEGORY_ID:
                finalWhere = BugetPlaningContract.BugetOnCategory._ID + " = " + uri.getPathSegments().get(BugetOnCategory.BUGETONCATEGORY_ID_PATH_POSITION);
                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.delete(BugetPlaningContract.BugetOnCategory.TABLE_NAME,finalWhere,whereArgs);
                break;
            case BUGETALL:
                count = db.delete(BugetPlaningContract.BugetAll.TABLE_NAME,where,whereArgs);
                break;
            case BUGETALL_ID:
                finalWhere = BugetPlaningContract.BugetAll._ID + " = " + uri.getPathSegments().get(BugetAll.BUGETALL_ID_PATH_POSITION);
                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.delete(BugetPlaningContract.BugetAll.TABLE_NAME,finalWhere,whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }



 //update() - обновляет строки в БД согласно заданным условиям
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String where, @Nullable String[]whereArgs) {
        SQLiteDatabase db = bugetPlaningDbHelper.getWritableDatabase();
        int count;
        String finalWhere;
        String id;
        switch (sUriMatcher.match(uri)) {
            case CATEGORIES:
                count = db.update(Categories.TABLE_NAME, values, where, whereArgs);
                break;
            case CATEGORIES_ID:
                id = uri.getPathSegments().get(Categories.CATEGORIES_ID_PATH_POSITION);
                finalWhere = Categories._ID + " = " + id;
                if (where !=null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.update(Categories.TABLE_NAME, values, finalWhere, whereArgs);
                break;
            case CONSUMPTION:
                count = db.update(Consumption.TABLE_NAME, values, where, whereArgs);
                break;
            case CONSUMPTION_ID:
                id = uri.getPathSegments().get(Consumption.CONSUMPTION_ID_PATH_POSITION);
                finalWhere = Consumption._ID + " = " + id;
                if (where !=null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.update(Consumption.TABLE_NAME, values, finalWhere, whereArgs);
                break;
            case BUGETONCATEGORY:
                count = db.update(BugetOnCategory.TABLE_NAME, values, where, whereArgs);
                break;
            case BUGETONCATEGORY_ID:
                id = uri.getPathSegments().get(BugetOnCategory.BUGETONCATEGORY_ID_PATH_POSITION);
                finalWhere = BugetOnCategory._ID + " = " + id;
                if (where !=null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.update(BugetOnCategory.TABLE_NAME, values, finalWhere, whereArgs);
                break;
            case BUGETALL:
                count = db.update(BugetAll.TABLE_NAME, values, where, whereArgs);
                break;
            case BUGETALL_ID:
                id = uri.getPathSegments().get(BugetAll.BUGETALL_ID_PATH_POSITION);
                finalWhere = BugetAll._ID + " = " + id;
                if (where !=null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.update(BugetAll.TABLE_NAME, values, finalWhere, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}

