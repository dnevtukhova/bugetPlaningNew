package com.dn_evtukhova;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dn_evtukhova.db.BugetPlaningContract;
import com.dn_evtukhova.db.BugetPlaningDBHelper;

public class MainBuget extends AppCompatActivity implements View.OnClickListener {
    final String LOG_TAG = "myLogs";
    Button mbtn;
    BugetPlaningDBHelper mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_buget);
        mbtn = (Button)findViewById(R.id.button);
        mbtn.setOnClickListener(this);

        mdb = new BugetPlaningDBHelper(this);
    }

    @Override
    public void onClick(View v) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // подключаемся к БД
        SQLiteDatabase db = mdb.getWritableDatabase();

        switch (v.getId()) {
            case R.id.button:
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query(BugetPlaningContract.Categories.TABLE_NAME, null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex(BugetPlaningContract.Categories._ID);
                    int nameColIndex = c.getColumnIndex(BugetPlaningContract.Categories.COLUMN_CATEGORY_NAME);
                    int emailColIndex = c.getColumnIndex(BugetPlaningContract.Categories.COLUMN_CATEGORY_IMG);

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
}
