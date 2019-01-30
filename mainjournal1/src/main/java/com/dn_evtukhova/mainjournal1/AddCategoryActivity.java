package com.dn_evtukhova.mainjournal1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.dn_evtukhova.mainjournal1.db.BugetPlaningContract;

public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAddCategory, btnLog;
    EditText eTextCategory;

    DBHelper dbHelper;
    SQLiteDatabase database;
    Cursor cursor;
    ListView lCategory;

    final String LOG_TAG = "myLogs";



    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        btnAddCategory = (Button)findViewById(R.id.buttonViewAddCategory);
        btnAddCategory.setOnClickListener(this);
       // btnLog = (Button)findViewById(R.id.buttonLocBd);
      //  btnLog.setOnClickListener(this);

        eTextCategory = (EditText)findViewById(R.id.editTextAddCategory);
// подключаемся к базе

        dbHelper = new DBHelper(this);
        dbHelper.open();
        /*database = dbHelper.getWritableDatabase();*/
       /* database.delete("categories", null, null);

        cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
       if (cursor.getCount() == 0) {
           ContentValues cv = new ContentValues();
           // заполним таблицу
           for (int i = 0; i < 10; i++) {
               cv.put("nameCategory", categories[i]);

               Log.d("mLog", "id = " + database.insert("categories", null, cv));
           }
       }

        cursor.close();
        dbHelper.close();
        // эмулируем нажатие кнопки btnAll
      //  onClick(btnLog);*/

    }

    @Override
    public void onClick(View v) {
   String categName = eTextCategory.getText().toString();



        switch (v.getId())
        {
            case R.id.buttonViewAddCategory:
                //Вносим значения в БД

                ContentValues cv = new ContentValues();
                cv.put(BugetPlaningContract.Categories.COLUMN_CATEGORY_NAME, categName);
                cv.put(BugetPlaningContract.Categories.COLUMN_CATEGORY_IMG, R.mipmap.ic_launcher);
                Uri newUri = getContentResolver().insert(BugetPlaningContract.Categories.CONTENT_URI, cv);
                Log.d(LOG_TAG, "insert, result Uri : " + newUri.toString());


               // dbHelper.addRec(categName, R.mipmap.ic_launcher);

                break;
           /* case R.id.buttonLocBd:
                cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);

                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", nameCategory = " + cursor.getString(nameIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog","0 rows");

                cursor.close();
            break;*/

        }
        dbHelper.close();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
