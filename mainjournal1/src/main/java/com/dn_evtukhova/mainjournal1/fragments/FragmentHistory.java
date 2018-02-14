package com.dn_evtukhova.mainjournal1.fragments;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;


import com.dn_evtukhova.mainjournal1.R;
import com.dn_evtukhova.mainjournal1.db.BugetPlaningContract;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHistory.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHistory extends Fragment implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Spinner historySpinner;
    String[] historyPeriod = {"День", "Неделя", "Месяц", "Год"};

    SimpleCursorAdapter historyAdapter;
    ListView listHistory;

    private OnFragmentInteractionListener mListener;

    public FragmentHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHistory newInstance(String param1, String param2) {
        FragmentHistory fragment = new FragmentHistory();
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
       View viewHistory = inflater.inflate(R.layout.fragment_history, container, false);
        PieChart chart = (PieChart)viewHistory.findViewById(R.id.chartHistory);
      /*  List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(18.5f, "Продукты"));

        entries.add(new PieEntry(26.7f, "Бытовая химия"));
        entries.add(new PieEntry(24.0f, "Одежда"));
        entries.add(new PieEntry(30.8f, "Коммунальные расходы"));

        PieDataSet set = new PieDataSet(entries, "Категории");
        PieData data = new PieData(set);

        set.setColors(ColorTemplate.VORDIPLOM_COLORS);
        chart.setData(data);
        chart.invalidate();*/

        ArrayAdapter<String> adapterHistory = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, historyPeriod);
        adapterHistory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        historySpinner = (Spinner) viewHistory.findViewById(R.id.spinnerHistory);
        historySpinner.setAdapter(adapterHistory);
        historySpinner.setPrompt("Период");
        historySpinner.setSelection(0);
        // устанавливаем обработчик нажатия
        historySpinner.setOnItemSelectedListener(this);

        return viewHistory;
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (historySpinner.getSelectedItemPosition()) {
            case 0:
                // формируем столбцы сопоставления
                String[] from = new String[]{BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_AMOUNT /*BugetPlaningContract.Consumption.COLUMN_CONSUMPTION_AMOUNT*/};
                int[] to = new int[]{R.id.tvTextListJournalExpediture2};

                // создаем адаптер и настраиваем список
                historyAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.list_journal_expediture, null, from, to, 0);
                listHistory = (ListView) getView().findViewById(R.id.listHistory);
                listHistory.setAdapter(historyAdapter);
                //прокрутка списка
                listHistory.setOnScrollListener(new AbsListView.OnScrollListener() {
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }

                    public void onScroll(AbsListView view, int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {
                        Log.d(LOG_TAG, "scroll: firstVisibleItem = " + firstVisibleItem
                                + ", visibleItemCount" + visibleItemCount
                                + ", totalItemCount" + totalItemCount);
                    }
                });

                // создаем лоадер для чтения данных
                getActivity().getSupportLoaderManager().initLoader(12, null,  this);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String queryD = "SELECT SUM(consumption_amount) as consumption_amount"
                + " FROM Consumption";
               /* + " ON Categories._id ="
                + " Consumption.category_id"*/
              //  + " WHERE consumption_date = ? ";
               // + " GROUP BY category_id";
        String[] selectionArgsD = {FragmentJournalExpediture.mySelectDate(3)};
        if (id == 11) {


            return new CursorLoader(
                    getContext(),
                    BugetPlaningContract.RawQuery.CONTENT_RAW_URI,
                    null,
                    queryD, selectionArgsD,
                    null);
        }
        else {return new CursorLoader(
                getContext(),
                BugetPlaningContract.RawQuery.CONTENT_RAW_URI,
                null,
                queryD,
                null,
                null);}
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case 11:

                historyAdapter.swapCursor(data);
/*
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
*/

            default:

                historyAdapter.swapCursor(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case 11:
                historyAdapter.swapCursor(null);
                break;
           /* case LWEEK:
                journalExpeditureAdapter1.swapCursor(null);

                break;
            case LMOUNTH:
                journalExpeditureAdapterM.swapCursor(null);
                break;
            case LYEAR:
                journalExpeditureAdapter.swapCursor(null);
                break;


            default:
                journalExpeditureAdapter.swapCursor(null);*/
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
}
