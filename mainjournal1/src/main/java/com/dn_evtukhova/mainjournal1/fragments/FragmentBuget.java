package com.dn_evtukhova.mainjournal1.fragments;

import android.content.ContentProvider;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dn_evtukhova.mainjournal1.R;
import com.dn_evtukhova.mainjournal1.db.BugetPlaningContract;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentBuget.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBuget#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBuget extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView bugetMounth;
    TextView bugetWeek;
    TextView bugetDay;
    TextView bugetYear;
    Button bugetBtn;
    SimpleCursorAdapter bugetAdapter;
    ListView listBuget;

    private OnFragmentInteractionListener mListener;

    public FragmentBuget() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBuget.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBuget newInstance(String param1, String param2) {
        FragmentBuget fragment = new FragmentBuget();
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
        final View view = inflater.inflate(R.layout.fragment_buget, container, false);

        bugetMounth = (view).findViewById(R.id.bugetMounth);
        bugetWeek = (view).findViewById(R.id.bugetWeek);
        bugetDay = (view).findViewById(R.id.bugetDay);
        bugetYear = (view).findViewById(R.id.bugetYear);
        bugetBtn = (view).findViewById(R.id.buttonBuget);
      //  bugetBtn.setOnClickListener(DialogInterface.OnClickListener);

        // формируем столбцы сопоставления
        String[] from = new String[] { BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_MOUNTH, BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_YEAR, BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_WEEK, BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_DAY};
        int[] to = new int[] { R.id.bugetMounth, R.id.bugetYear, R.id.bugetWeek, R.id.bugetDay };

        // создаем адаптер и настраиваем список
       // bugetAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.fragment_buget, null, from, to, 0);
       /* listBuget = (ListView)view.findViewById(R.id.listBuget);*/
       // setListAdapter(bugetAdapter);

        Cursor cursor =getActivity().getContentResolver().query(BugetPlaningContract.BugetAll.CONTENT_URI, null, null,
                null, null);
       getActivity().startManagingCursor(cursor);
        cursor.moveToFirst();
        //cursor.moveToNext();

        String displayBugetMounth = cursor.getString(cursor
                .getColumnIndex(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_MOUNTH));
        String displayBugetDay = cursor.getString(cursor
                .getColumnIndex(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_DAY));
        String displayBugetYear = cursor.getString(cursor
                .getColumnIndex(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_YEAR));
        String displayBugetWeek = cursor.getString(cursor
                .getColumnIndex(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_WEEK));
        cursor.close();

        bugetMounth.append(displayBugetMounth);
        bugetDay.append(displayBugetDay);
        bugetWeek.append(displayBugetWeek);
        bugetYear.append(displayBugetYear);
        // создаем лоадер для чтения данных
      //  getActivity().getSupportLoaderManager().initLoader(0, null, this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;/* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/
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
