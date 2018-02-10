package com.dn_evtukhova.mainjournal1.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dn_evtukhova.mainjournal1.ActivityAddExpediture;
import com.dn_evtukhova.mainjournal1.R;
import com.dn_evtukhova.mainjournal1.db.BugetPlaningContract;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentJournalExpediture.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentJournalExpediture#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentJournalExpediture extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int LDAY = 8;
    private static final int LWEEK = 9;
    private static final int LMOUNTH = 10;
    private static final int LYEAR = 11;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textViewDJournal;
    Spinner journalSpinner;
    private OnFragmentInteractionListener mListener;
    String[] journalPeriod = {"День", "Неделя", "Месяц", "Год"};
    SimpleDateFormat dfDate_day = new SimpleDateFormat("dd/MM/yyyy");

    Button btnAddExpediture;

    SimpleCursorAdapter journalExpeditureAdapter;
    SimpleCursorAdapter journalExpeditureAdapter1;
    SimpleCursorAdapter journalExpeditureAdapterM;


    ListView listJournalExpediture;
    ListView listJournalExpediture1;
    long date = System.currentTimeMillis();
    Calendar calendar = Calendar.getInstance();
    int Month;
    int Year;
    String getMounth;

    TextView textViewBugetOnPeriod;

    Cursor cursorMy;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentJournalExpediture.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentJournalExpediture newInstance(String param1, String param2) {
        FragmentJournalExpediture fragment = new FragmentJournalExpediture();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View journalView = inflater.inflate(R.layout.fragment_fragment_journal_expediture, container, false);
        textViewDJournal = (TextView) journalView.findViewById(R.id.textViewDateJournal);
        textViewBugetOnPeriod = (TextView)journalView.findViewById(R.id.sum_buget);



        /*SimpleDateFormat dfDate_day= new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dfDate_day.format(date);
        String dateString=dfDate_day.format(date);
        textViewDJournal.setText(dateString);*/


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, journalPeriod);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        journalSpinner = (Spinner) journalView.findViewById(R.id.spinnerJournal);
        journalSpinner.setAdapter(adapter);
        journalSpinner.setPrompt("Период");
        journalSpinner.setSelection(0);
        // устанавливаем обработчик нажатия
        journalSpinner.setOnItemSelectedListener(this);


        btnAddExpediture = (Button) journalView.findViewById(R.id.buttonJournal);
        btnAddExpediture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View t) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ActivityAddExpediture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });



        return journalView;
    }

    //обработка спиннера

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long id) {
        // показываем позиция нажатого элемента
        // Toast.makeText(getContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
        switch (journalSpinner.getSelectedItemPosition()) {
            case 0:

                String dateString = dfDate_day.format(date);
                textViewDJournal.setText(dateString);

                // формируем столбцы сопоставления
                String[] from = new String[]{BugetPlaningContract.Categories.COLUMN_CATEGORY_IMG, BugetPlaningContract.Categories.COLUMN_CATEGORY_NAME, BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_AMOUNT, BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_DATE};
                int[] to = new int[]{R.id.ivImgListJournalExpediture, R.id.tvTextListJournalExpediture1, R.id.tvTextListJournalExpediture2, R.id.tvTextListJournalExpediture3};

                // создаем адаптер и настраиваем список
                journalExpeditureAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.list_journal_expediture, null, from, to, 0);
                listJournalExpediture = (ListView) getView().findViewById(R.id.list_journal_expediture_f);
                listJournalExpediture.setAdapter(journalExpeditureAdapter);
                //прокрутка списка
                listJournalExpediture.setOnScrollListener(new AbsListView.OnScrollListener() {
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        // Log.d(LOG_TAG, "scrollState = " + scrollState);
                    }

                    public void onScroll(AbsListView view, int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {
                        Log.d(LOG_TAG, "scroll: firstVisibleItem = " + firstVisibleItem
                                + ", visibleItemCount" + visibleItemCount
                                + ", totalItemCount" + totalItemCount);
                    }
                });

                // создаем лоадер для чтения данных
                getActivity().getSupportLoaderManager().initLoader(LDAY, null, this);

                //заполняем поле сумма бюджета
                //курсор для заполнения поля сумма бюджета
                Cursor cursorMy=getActivity().getContentResolver().query(BugetPlaningContract.BugetAll.CONTENT_URI, null, null,
                        null, null);
                getActivity().startManagingCursor(cursorMy);
                cursorMy.moveToFirst();

                String displayBugetDay = cursorMy.getString(cursorMy
                        .getColumnIndex(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_DAY));
                textViewBugetOnPeriod.append(displayBugetDay);
                cursorMy.close();
                break;
            case 1:
               String fWeek = formatWeek();
                textViewDJournal.setText("c"+fWeek+ "по" + dfDate_day.format(date));
                // формируем столбцы сопоставления
                String[] fromWeek = new String[]{BugetPlaningContract.Categories.COLUMN_CATEGORY_IMG, BugetPlaningContract.Categories.COLUMN_CATEGORY_NAME, BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_AMOUNT, BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_DATE};
                int[] toWeek = new int[]{R.id.ivImgListJournalExpediture, R.id.tvTextListJournalExpediture1, R.id.tvTextListJournalExpediture2, R.id.tvTextListJournalExpediture3};

                // создаем адаптер и настраиваем список
                journalExpeditureAdapter1 = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.list_journal_expediture, null, fromWeek, toWeek, 0);
                listJournalExpediture1 = (ListView) getView().findViewById(R.id.list_journal_expediture_f);
                listJournalExpediture1.setAdapter(journalExpeditureAdapter1);
                //прокрутка списка
                listJournalExpediture1.setOnScrollListener(new AbsListView.OnScrollListener() {
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        // Log.d(LOG_TAG, "scrollState = " + scrollState);
                    }

                    public void onScroll(AbsListView view, int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {
                        Log.d(LOG_TAG, "scroll: firstVisibleItem = " + firstVisibleItem
                                + ", visibleItemCount" + visibleItemCount
                                + ", totalItemCount" + totalItemCount);
                    }
                });
                //очищаем текстовое поле
                textViewBugetOnPeriod.setText("");
                // создаем лоадер для чтения данных
                getActivity().getSupportLoaderManager().initLoader(LWEEK, null, this);

                //курсор для заполнения поля сумма бюджета
                Cursor cursorMyW=getActivity().getContentResolver().query(BugetPlaningContract.BugetAll.CONTENT_URI, null, null,
                        null, null);
                getActivity().startManagingCursor(cursorMyW);
                cursorMyW.moveToFirst();

                String displayBugetWeek = cursorMyW.getString(cursorMyW
                        .getColumnIndex(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_WEEK));
                textViewBugetOnPeriod.append(displayBugetWeek);
                cursorMyW.close();
                break;
            case 2:
                Calendar c1 = new GregorianCalendar();
                c1.setTimeInMillis(System.currentTimeMillis());
                Month = c1.get(Calendar.MONTH);
                textViewDJournal.setText(getNameMounth(Month));
                // формируем столбцы сопоставления
                String[] fromMounth = new String[]{BugetPlaningContract.Categories.COLUMN_CATEGORY_IMG, BugetPlaningContract.Categories.COLUMN_CATEGORY_NAME, BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_AMOUNT, BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_DATE};
                int[] toMounth = new int[]{R.id.ivImgListJournalExpediture, R.id.tvTextListJournalExpediture1, R.id.tvTextListJournalExpediture2, R.id.tvTextListJournalExpediture3};

                // создаем адаптер и настраиваем список
                journalExpeditureAdapterM = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.list_journal_expediture, null, fromMounth, toMounth, 0);
                listJournalExpediture = (ListView) getView().findViewById(R.id.list_journal_expediture_f);
                listJournalExpediture.setAdapter(journalExpeditureAdapterM);
                //прокрутка списка
                listJournalExpediture.setOnScrollListener(new AbsListView.OnScrollListener() {
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        // Log.d(LOG_TAG, "scrollState = " + scrollState);
                    }

                    public void onScroll(AbsListView view, int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {
                        Log.d(LOG_TAG, "scroll: firstVisibleItem = " + firstVisibleItem
                                + ", visibleItemCount" + visibleItemCount
                                + ", totalItemCount" + totalItemCount);
                    }
                });

                // создаем лоадер для чтения данных
                getActivity().getSupportLoaderManager().initLoader(LMOUNTH, null, this);

                //очищаем текстовое поле
                textViewBugetOnPeriod.setText("");
                //курсор для заполнения поля сумма бюджета
                Cursor cursorMyM=getActivity().getContentResolver().query(BugetPlaningContract.BugetAll.CONTENT_URI, null, null,
                        null, null);
                getActivity().startManagingCursor(cursorMyM);
                cursorMyM.moveToFirst();

                String displayBugetMonth = cursorMyM.getString(cursorMyM
                        .getColumnIndex(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_MOUNTH));
                textViewBugetOnPeriod.append(displayBugetMonth);
                cursorMyM.close();
                break;
            case 3:
                Calendar c2 = new GregorianCalendar();
                c2.setTimeInMillis(System.currentTimeMillis());
                Year = c2.get(Calendar.YEAR);
                String dateStringY = String.valueOf(Year);
                textViewDJournal.setText(dateStringY);
                // формируем столбцы сопоставления
                String[] fromYear = new String[]{BugetPlaningContract.Categories.COLUMN_CATEGORY_IMG, BugetPlaningContract.Categories.COLUMN_CATEGORY_NAME, BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_AMOUNT, BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_DATE};
                int[] toYear = new int[]{R.id.ivImgListJournalExpediture, R.id.tvTextListJournalExpediture1, R.id.tvTextListJournalExpediture2, R.id.tvTextListJournalExpediture3};

                // создаем адаптер и настраиваем список
                journalExpeditureAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.list_journal_expediture, null, fromYear, toYear, 0);
                listJournalExpediture = (ListView) getView().findViewById(R.id.list_journal_expediture_f);
                listJournalExpediture.setAdapter(journalExpeditureAdapter);
                //прокрутка списка
                listJournalExpediture.setOnScrollListener(new AbsListView.OnScrollListener() {
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        // Log.d(LOG_TAG, "scrollState = " + scrollState);
                    }

                    public void onScroll(AbsListView view, int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {
                        Log.d(LOG_TAG, "scroll: firstVisibleItem = " + firstVisibleItem
                                + ", visibleItemCount" + visibleItemCount
                                + ", totalItemCount" + totalItemCount);
                    }
                });

                // создаем лоадер для чтения данных
                getActivity().getSupportLoaderManager().initLoader(LYEAR, null, this);

                //очищаем текстовое поле
                textViewBugetOnPeriod.setText("");
                //курсор для заполнения поля сумма бюджета
                Cursor cursorMyY=getActivity().getContentResolver().query(BugetPlaningContract.BugetAll.CONTENT_URI, null, null,
                        null, null);
                getActivity().startManagingCursor(cursorMyY);
                cursorMyY.moveToFirst();

                String displayBugetYear = cursorMyY.getString(cursorMyY
                        .getColumnIndex(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_YEAR));
                textViewBugetOnPeriod.append(displayBugetYear);
                cursorMyY.close();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //String query = "SELECT * FROM mytable WHERE _id > ? LIMIT 100";
        Log.d(LOG_TAG, "--- INNER JOIN with rawQuery---");
        String query = "SELECT * "
                + " FROM Consumption"
                + " INNER JOIN Categories"
                + " ON Consumption.category_id ="
                + " Categories._id"
                + " WHERE consumption_date = ? LIMIT 100";


        String[] selectionArgsD = {mySelectDate(1)};
        String[] selectionArgsM = {"%" + mySelectDate(3)};
        String[] selectionArgsY = {"%" + mySelectDate(4)};

        String query1 = "SELECT * "
                + " FROM Consumption"
                + " INNER JOIN Categories"
                + " ON Consumption.category_id = "
                + "Categories._id"
                + " WHERE consumption_date LIKE ?";

        String query3 = "SELECT * "
                + " FROM Consumption"
                + " INNER JOIN Categories"
                + " ON Consumption.category_id = "
                + "Categories._id"
                + " WHERE consumption_date IN (?,?,?,?,?,?,?,?)";

       String[] selectionArgs2 = {"%02%"};
        String[] selectionArgs3;

      String [] k = new String[8];
      dateWeek(k);

        Calendar c1 = new GregorianCalendar();
        c1.setTimeInMillis(System.currentTimeMillis());

            c1.add(Calendar.DATE, -6);
            Date todate1 = c1.getTime();
            String fromdate = dfDate_day.format(todate1);
        Calendar c2 = new GregorianCalendar();
        c2.setTimeInMillis(System.currentTimeMillis());
        c2.add (Calendar.DATE, -5);
        Date todate2=c2.getTime();
        String fromdate2 = dfDate_day.format(todate2);

       // System.out.println(k);
        String[] selectionArgs4 = { k[0], k[1], k[2], k[3], k[4], k[5], k[6], k[7]};

        CursorLoader CursorLoader;


        if (id == LDAY) {


            return new CursorLoader(
                    getContext(),
                    BugetPlaningContract.RawQuery.CONTENT_RAW_URI,
                    null,
                    query, selectionArgsD,
                    BugetPlaningContract.Categories.COLUMN_CATEGORY_NAME);
        } else if (id == LWEEK){
            return new  CursorLoader(
                    getContext(),
                    BugetPlaningContract.RawQuery.CONTENT_RAW_URI,
                    null,
                    query3,
                    selectionArgs4,
                    BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_DATE);
    } else if (id == LMOUNTH){
        return new  CursorLoader(
                getContext(),
                BugetPlaningContract.RawQuery.CONTENT_RAW_URI,
                null,
                query1,
                selectionArgsM,
                "consumption_date ASC");}
        else if (id == LYEAR){
            return new  CursorLoader(
                    getContext(),
                    BugetPlaningContract.RawQuery.CONTENT_RAW_URI,
                    null,
                    query1,
                    selectionArgsY,
                    "consumption_date ASC");}
        else {return new CursorLoader(
                getContext(),
                BugetPlaningContract.RawQuery.CONTENT_RAW_URI,
                null,
                query,
                null,
                null);}


    }




    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

       switch (loader.getId()) {
           case LDAY:

               journalExpeditureAdapter.swapCursor(data);

               break;
           case LWEEK:
               journalExpeditureAdapter1.swapCursor(data);

               break;
           case LMOUNTH:
               journalExpeditureAdapterM.swapCursor(data);

               break;
           case LYEAR:
               journalExpeditureAdapter.swapCursor(data);

               break;


           default:

               journalExpeditureAdapter.swapCursor(data);
       }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        switch (loader.getId()) {
            case LDAY:
                journalExpeditureAdapter.swapCursor(null);
                break;
            case LWEEK:
                journalExpeditureAdapter1.swapCursor(null);

                break;
            case LMOUNTH:
                journalExpeditureAdapterM.swapCursor(null);
                break;
            case LYEAR:
                journalExpeditureAdapter.swapCursor(null);
                break;


            default:
                journalExpeditureAdapter.swapCursor(null);
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public String mySelectDate(int i) {
        String myDateString = dfDate_day.format(date);
               if (i == 1) {
            return myDateString;
        }  else if (i == 3) {
            return myDateString.substring(3);
        } else if (i == 4) {
            return myDateString.substring(5);
        } else {
            return myDateString;
        }


    }

    public String formatWeek()
    {
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.DATE, -7);
        Date todate1 = c.getTime();
        String fromdate = dfDate_day.format(todate1);
         return fromdate;
    }

    public String [] dateWeek (String [] dateWeek)
    {

/*
        for (int j=0; j<-7; j--)
        {
            Calendar c1 = new GregorianCalendar();
            c1.setTimeInMillis(System.currentTimeMillis());
            c1.add(Calendar.DATE, j);
            Date todate1 = c1.getTime();
            String fromdate = dfDate_day.format(todate1);
            dateWeek [j*(-(1))]= fromdate;
            System.out.println("лог");
            System.out.println(fromdate);
        }*/
        Calendar c1 = new GregorianCalendar();
        c1.setTimeInMillis(System.currentTimeMillis());
        c1.add(Calendar.DATE, -5);
        Date todate1 = c1.getTime();
        String fromdate = dfDate_day.format(todate1);
        dateWeek[0]=fromdate;

        Calendar c2 = new GregorianCalendar();
        c2.setTimeInMillis(System.currentTimeMillis());
        c2.add (Calendar.DATE, -6);
        Date todate2=c2.getTime();
        String fromdate2 = dfDate_day.format(todate2);
        dateWeek[1]=fromdate2;

        Calendar c3 = new GregorianCalendar();
        c3.setTimeInMillis(System.currentTimeMillis());
        c3.add(Calendar.DATE, -4);
        Date todate3 = c3.getTime();
        String fromdate3 = dfDate_day.format(todate3);
        dateWeek[2]=fromdate3;

        Calendar c4 = new GregorianCalendar();
        c4.setTimeInMillis(System.currentTimeMillis());
        c4.add (Calendar.DATE, -3);
        Date todate4=c4.getTime();
        String fromdate4 = dfDate_day.format(todate4);
        dateWeek[3]=fromdate4;

        Calendar c5 = new GregorianCalendar();
        c2.setTimeInMillis(System.currentTimeMillis());
        c5.add(Calendar.DATE, -2);
        Date todate5 = c5.getTime();
        String fromdate5 = dfDate_day.format(todate5);
        dateWeek[4]=fromdate5;

        Calendar c6 = new GregorianCalendar();
        c6.setTimeInMillis(System.currentTimeMillis());
        c6.add (Calendar.DATE, -1);
        Date todate6=c6.getTime();
        String fromdate6 = dfDate_day.format(todate6);
        dateWeek[5]=fromdate6;

        Calendar c7 = new GregorianCalendar();
        c7.add(Calendar.DATE, 0);
        Date todate7 = c7.getTime();
        String fromdate7 = dfDate_day.format(todate7);
        dateWeek[6]=fromdate7;

        Calendar c8 = new GregorianCalendar();
        c8.add(Calendar.DATE, -7);
        Date todate8 = c8.getTime();
        String fromdate8 = dfDate_day.format(todate8);
        dateWeek[7]=fromdate8;

        return dateWeek;
    }

    public String getNameMounth (int i) {

        switch (i) {
            case 0:
                getMounth= "Январь";
            break;
            case 1:
                getMounth = "Февраль";
            break;
            case 2:
                getMounth = "Март";
            break;
            case 3:
                getMounth = "Апрель";
            break;
            case 4:
                getMounth = "Май";
            break;
            case 5:
                getMounth = "Июнь";
            break;
            case 6:
                getMounth = "Июль";
            break;
            case 7:
                getMounth = "Август";
            break;
            case 8:
                getMounth = "Сентябрь";
            break;
            case 9:
                getMounth = "Октябрь";
            break;
            case 10:
                getMounth = "Ноябрь";
            break;
            case 11:
                getMounth = "Декабрь";
            break;

            default:
                getMounth = "Mесяц";
        }
       return getMounth;
    }


}
