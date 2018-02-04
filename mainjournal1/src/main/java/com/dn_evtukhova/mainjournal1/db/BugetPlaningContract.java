package com.dn_evtukhova.mainjournal1.db;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by 1 on 23.01.2018.
 */

public final class BugetPlaningContract {
    private BugetPlaningContract (){}

    public static final String CONTENT_AUTHORITY = "com.dn_evtukhova.mainjournal1";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //описываем таблицу категории
    public static final class Categories implements BaseColumns
    {
        private Categories() {}
        public static final String TABLE_NAME ="categories";
        private static final String SCHEME = "content://";
        private static final String PATH_CATEGORIES = "/categories";
        private static final String PATH_CATEGORIES_ID = "/categories/";
        public static final int CATEGORIES_ID_PATH_POSITION = 1;
        public static final Uri CONTENT_URI =  Uri.parse(SCHEME + CONTENT_AUTHORITY + PATH_CATEGORIES);
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + CONTENT_AUTHORITY + PATH_CATEGORIES_ID);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORIES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORIES;

        public static final String DEFAULT_SORT_ORDER = "category_name ASC";


        public static final String COLUMN_CATEGORY_IMG   = "image";
        public static final String COLUMN_CATEGORY_NAME   = "category_name";

        public static final String[] DEFAULT_PROJECTION = new String[]{
                BugetPlaningContract.Categories._ID,
                BugetPlaningContract.Categories.COLUMN_CATEGORY_IMG,
                BugetPlaningContract.Categories.COLUMN_CATEGORY_NAME
        };

    }
    //описываем таблицу расходы
    public static final class Consumption implements BaseColumns
    {
        private Consumption() {}
        public static final String TABLE_NAME ="consumption";
        private static final String SCHEME = "content://";
        private static final String PATH_CONSUMPTION = "/consumption";
        private static final String PATH_CONSUMPTION_ID = "/consumption/";
        public static final int CONSUMPTION_ID_PATH_POSITION = 1;
        public static final Uri CONTENT_URI =  Uri.parse(SCHEME + CONTENT_AUTHORITY + PATH_CONSUMPTION);
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + CONTENT_AUTHORITY + PATH_CONSUMPTION_ID);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONSUMPTION;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONSUMPTION;

        public static final String DEFAULT_SORT_ORDER = "consumption_date ASC";


        public static final String COLUMN_CATEGORY_ID   = "category_id";
        public static final String COLUMN_CONSUMPTION_DATE  = "consumption_date";
        public static final String COLUMN_CONSUMPTION_AMOUNT   = "consumption_amount";
        public static final String COLUMN_CONSUMPTION_COMMENT    = "consumption_comment";



        public static final String[] DEFAULT_PROJECTION = new String[]{
                BugetPlaningContract.Consumption._ID,
                BugetPlaningContract.Consumption.COLUMN_CATEGORY_ID,
                BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_DATE,
                BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_AMOUNT,
                BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_COMMENT
        };
    }

    //бюджет по категориям
    public static final class BugetOnCategory implements BaseColumns
    {
        private BugetOnCategory() {}
        public static final String TABLE_NAME ="bugetOnCategory";
        private static final String SCHEME = "content://";
        private static final String PATH_BUGETONCATEGORY = "/bugetOnCategory";
        private static final String PATH_BUGETONCATEGORY_ID = "/bugetOnCategory/";
        public static final int BUGETONCATEGORY_ID_PATH_POSITION = 1;
        public static final Uri CONTENT_URI =  Uri.parse(SCHEME + CONTENT_AUTHORITY + PATH_BUGETONCATEGORY);
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + CONTENT_AUTHORITY + PATH_BUGETONCATEGORY_ID);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUGETONCATEGORY;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUGETONCATEGORY;

       public static final String DEFAULT_SORT_ORDER = "category_id ASC";


        public static final String COLUMN_BUGETONCATEGORY_CATEGORY_ID   = "category_id";
        public static final String COLUMN_AMOUNT_BUGET_CATEGORY   = "amount_buget";
        public static final String COLUMN_BUGET_ID   = "buget_id";


        public static final String[] DEFAULT_PROJECTION = new String[]{
                BugetPlaningContract.BugetOnCategory._ID,
                BugetPlaningContract.BugetOnCategory.COLUMN_BUGETONCATEGORY_CATEGORY_ID,
                BugetPlaningContract.BugetOnCategory.COLUMN_AMOUNT_BUGET_CATEGORY,
                BugetPlaningContract.BugetOnCategory.COLUMN_BUGET_ID

        };
    }

    //общий бюджет
    public static final class BugetAll implements BaseColumns
    {
        private BugetAll() {}
        public static final String TABLE_NAME ="bugetAll";
        private static final String SCHEME = "content://";
        private static final String PATH_BUGETALL = "/bugetAll";
        private static final String PATH_BUGETALL_ID = "/bugetAll/";
        public static final int BUGETALL_ID_PATH_POSITION = 1;
        public static final Uri CONTENT_URI =  Uri.parse(SCHEME + CONTENT_AUTHORITY + PATH_BUGETALL);
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + CONTENT_AUTHORITY + PATH_BUGETALL_ID);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUGETALL;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUGETALL;


        public static final String DEFAULT_SORT_ORDER = "amount_bugetAll_mounth ASC";

        public static final String COLUMN_AMOUNT_BUGETALL_MOUNTH = "amount_bugetAll_mounth";
        public static final String COLUMN_AMOUNT_BUGETALL_WEEK = "amount_bugetAll_week";
        public static final String COLUMN_AMOUNT_BUGETALL_YEAR = "amount_bugetAll_year";
        public static final String COLUMN_AMOUNT_BUGETALL_DAY   = "amount_bugetAll_day";


        public static final String[] DEFAULT_PROJECTION = new String[]{
                BugetPlaningContract.BugetAll._ID,
                BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_MOUNTH,
                BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_WEEK,
                BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_YEAR,
                BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_DAY

        };
    }
    //для произвольного SQL-запроса к БД
    public static final class RawQuery implements BaseColumns
    {
        private static final String SCHEME = "content://";
       private static final String RAW_QUERY_PATH = "/raw";
        public static final Uri CONTENT_RAW_URI = Uri.parse(SCHEME + CONTENT_AUTHORITY + RAW_QUERY_PATH);
    }
}
