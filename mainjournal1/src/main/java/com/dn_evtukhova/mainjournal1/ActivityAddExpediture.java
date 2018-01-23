package com.dn_evtukhova.mainjournal1;

import android.Manifest;

import android.content.Context;


import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CursorAdapter;

import android.widget.ListView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.Spinner;


import com.dn_evtukhova.mainjournal1.fragments.FragmentCategory;

import java.util.zip.Inflater;

import static android.provider.CalendarContract.CalendarCache.URI;

public class ActivityAddExpediture extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    Spinner spinnerAddExpediture;
    DBHelper DBAddExp;
    SimpleCursorAdapter spinAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expediture_n);

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALENDAR);
        int permissionCheck1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALENDAR);

        // Получаем спиннер:
        spinnerAddExpediture = (Spinner)findViewById(R.id.spinnerAddExpediture);

        // открываем подключение к БД
        DBAddExp = new DBHelper(this);
        DBAddExp.open();

        // формируем столбцы сопоставления
        String[] from = new String[] { DBHelper.KEY_NAME};
        int[] to = new int[] { R.id.spinnerAddExpediture};


        // Создаем адаптер запроса из БД данных для спиннера
       spinAdapter = new SimpleCursorAdapter(this, R.layout.spin_text_add_expediture,null,
        from, to, 0);

        // Устанавливаем спиннеру адаптер
        spinnerAddExpediture.setAdapter(spinAdapter);

        // создаем лоадер для чтения данных
        getSupportLoaderManager().initLoader(0, null, (android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>)this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, DBAddExp);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        spinAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader {

        DBHelper db;

        public MyCursorLoader(Context context, DBHelper db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {

            Cursor cursor = db.getCategory();
            /*try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            return cursor;
        }

    }

}
