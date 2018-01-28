package com.dn_evtukhova.mainjournal1;

import android.Manifest;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;


import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.content.ContextCompat;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;

import android.widget.ListView;


import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.dn_evtukhova.mainjournal1.db.BugetPlaningContract.Categories;
import com.dn_evtukhova.mainjournal1.db.BugetPlaningDBHelper;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

public class ActivityAddExpediture extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {
    Spinner spinnerAddExpediture;
    DBHelper DBAddExp;
    SimpleCursorAdapter spinAdapter;


    final String LOG_TAG = "myLogs"; //для контроля заполнения БД
    Button mbtn;
    BugetPlaningDBHelper mdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expediture_n);
        mbtn = (Button)findViewById(R.id.buttonViewAddExpediture);
        mbtn.setOnClickListener(this);

        mdb = new BugetPlaningDBHelper(this); //для теста, заполнилась бд или нет при создании

        // формируем столбцы сопоставления
        String[] from = new String[] { Categories.COLUMN_CATEGORY_NAME};
        int[] to = new int[] { android.R.id.text1};



        /// Создаем адаптер запроса из БД данных для спиннера
       spinAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item,null,
       from, to, 0);

       /// Дополнительно настраиваем адаптер
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Получаем спиннер:
      spinnerAddExpediture = (Spinner)findViewById(R.id.spinnerAddExpediture);

        // Устанавливаем спиннеру адаптер
     spinnerAddExpediture.setAdapter(spinAdapter);
     spinnerAddExpediture.setPrompt("Список категорий");

        // создаем лоадер для чтения данных
        this.getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

       //Готовим колонки и условие
        String[] cols = new String[] { Categories._ID, Categories.COLUMN_CATEGORY_NAME };

        // создаем и возвращаем настроенный лоадер поиска

         return new CursorLoader(
                this,
                Categories.CONTENT_URI,
                cols, null,
                null,
                null);
    }


    // обработчик окончания выборки из БД
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        // Устанавливаем курсор в адаптер
       spinAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
       spinAdapter.swapCursor(null);
    }


    //для контроля заполнения БД
    @Override
    public void onClick(View v) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // подключаемся к БД
        SQLiteDatabase db = mdb.getWritableDatabase();

        switch (v.getId()) {
            case R.id.buttonViewAddExpediture:
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query(Categories.TABLE_NAME, null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex(Categories._ID);
                    int nameColIndex = c.getColumnIndex(Categories.COLUMN_CATEGORY_NAME);
                    int emailColIndex = c.getColumnIndex(Categories.COLUMN_CATEGORY_IMG);

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", name = " + c.getString(nameColIndex) +
                                        ", email = " + c.getString(emailColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}


