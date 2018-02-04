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

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentJournalExpediture.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentJournalExpediture#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentJournalExpediture extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textViewDJournal;
    Spinner journalSpinner;
    private OnFragmentInteractionListener mListener;
    String [] journalPeriod = {"День","Неделя", "Месяц", "Год", "Календарь"};

    Button btnAddExpediture;

   SimpleCursorAdapter journalExpeditureAdapter;



   ListView listJournalExpediture;

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
        textViewDJournal = (TextView)journalView.findViewById(R.id.textViewDateJournal);
        long date = System.currentTimeMillis();

        SimpleDateFormat dfDate_day= new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dfDate_day.format(date);
        String dateString=dfDate_day.format(date);
        textViewDJournal.setText(dateString);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, journalPeriod);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        journalSpinner = (Spinner)journalView.findViewById(R.id.spinnerJournal);
        journalSpinner.setAdapter(adapter);
        journalSpinner.setPrompt("Период");
        journalSpinner.setSelection(0);
        // устанавливаем обработчик нажатия
        journalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view,
                                                                         int position, long id) {
                                                  // показываем позиция нажатого элемента
                                                  Toast.makeText(getContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                                              }
                                              @Override
                                              public void onNothingSelected(AdapterView<?> arg0) {
                                              }
        });


        btnAddExpediture = (Button)journalView.findViewById(R.id.buttonJournal);
        btnAddExpediture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View t) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ActivityAddExpediture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // формируем столбцы сопоставления
        String[] from = new String[] { BugetPlaningContract.Categories.COLUMN_CATEGORY_IMG, BugetPlaningContract.Categories.COLUMN_CATEGORY_NAME,BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_AMOUNT, BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_DATE};
        int[] to = new int[] { R.id.ivImgListJournalExpediture, R.id.tvTextListJournalExpediture1, R.id.tvTextListJournalExpediture2, R.id.tvTextListJournalExpediture3 };

        // создаем адаптер и настраиваем список
        journalExpeditureAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.list_journal_expediture, null, from, to, 0);
        listJournalExpediture = (ListView) journalView.findViewById(R.id.list_journal_expediture_f);
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
        getActivity().getSupportLoaderManager().initLoader(0, null, this);


        return journalView;
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
        String query ="SELECT * "
        + " FROM Consumption"
        + " INNER JOIN Categories"
        + " ON Consumption.category_id = "
        + "Categories._id";

       // String[] arguments = new String[] {"123"};
        return new CursorLoader(
                getContext(),
                BugetPlaningContract.RawQuery.CONTENT_RAW_URI,
                null,
                query,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        journalExpeditureAdapter.swapCursor(data);
       // data.close();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){journalExpeditureAdapter.swapCursor(null);

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

}
