package com.dn_evtukhova.mainjournal1.fragments;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dn_evtukhova.mainjournal1.R;
import com.dn_evtukhova.mainjournal1.db.BugetPlaningContract;



import java.io.StringReader;

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

    String value;
    float chislo;
    float bmf, bwf, bdf, byf;

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
        bugetBtn = (Button)view.findViewById(R.id.buttonBuget);

        //обработка нажатия кнопки
        bugetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*try {*/

               String bugetMounthString = bugetMounth.getText().toString();
               // bmf = Float.parseFloat(bugetMounth.getText().toString());
              String bugetWeekString = bugetWeek.getText().toString();
              //  bwf = Float.parseFloat(bugetWeek.getText().toString());
                String bugetDayString = bugetDay.getText().toString();
              //  bdf = Float.parseFloat(bugetWeek.getText().toString());
                String bugetYearString = bugetYear.getText().toString();
               // byf = Float.valueOf(bugetYear.getText().toString());
              // byf = Float.parseFloat(bugetYearString);
               /* } catch (NumberFormatException e) {
                    bmf = 0;
                    bwf = 0;
                    bdf=0;
                    byf=0;
                }*/
                ContentValues cv = new ContentValues();
                cv.put(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_MOUNTH, bugetMounthString);
                cv.put(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_WEEK, bugetWeekString);
                cv.put(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_DAY, bugetDayString);
                cv.put(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_YEAR, bugetYearString);
                Uri uri = ContentUris.withAppendedId(BugetPlaningContract.BugetAll.CONTENT_URI, 1);
                int cnt = getActivity().getContentResolver().update(uri, cv, null, null);
                Log.d(LOG_TAG, "update, count : " + cnt);

            }
        });

        // формируем столбцы сопоставления
        //String[] from = string[] { BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_MOUNTH, BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_YEAR, BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_WEEK, BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_DAY};
        //double[] to = new double[] { R.id.bugetMounth, R.id.bugetYear, R.id.bugetWeek, R.id.bugetDay };

         Cursor cursor =getActivity().getContentResolver().query(BugetPlaningContract.BugetAll.CONTENT_URI, null, null,
                null, null);
       getActivity().startManagingCursor(cursor);
        cursor.moveToFirst();


        String displayBugetMounth = cursor.getString(cursor
                .getColumnIndex(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_MOUNTH));
        float displayBugetDay = cursor.getFloat(cursor
                .getColumnIndex(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_DAY));
        float displayBugetYear = cursor.getFloat(cursor
                .getColumnIndex(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_YEAR));
        float displayBugetWeek = cursor.getFloat(cursor
                .getColumnIndex(BugetPlaningContract.BugetAll.COLUMN_AMOUNT_BUGETALL_WEEK));


        bugetMounth.append(displayBugetMounth);
        bugetDay.setText(String.format("%.1f",displayBugetDay));
        bugetWeek.setText(String.format("%.1f",displayBugetWeek));
        bugetYear.setText(String.format("%.1f",displayBugetYear));
        cursor.close();

        //обработка нажатия на текстовое поле bugetMounth
        bugetMounth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // value = bugetMounth.getText().toString();


            }

            @Override
            public void afterTextChanged(Editable s) {
               try {
                   value = bugetMounth.getText().toString();

                  chislo = Float.parseFloat(value);
                   bugetYear.setText(String.format("%.1f",(chislo * 12)));
                   bugetDay.setText(String.format("%.1f",(chislo * 12) / 365));
                   bugetWeek.setText(String.format("%.1f",((chislo * 12) / 365) * 7));
               } catch (Exception e) {
                   Toast.makeText(getContext(), "Заполните сумму бюджета!", Toast.LENGTH_SHORT).show();
               }
            }
        });

      //обработка нажатия на текстовое поле bugetYear
        bugetYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    value = bugetYear.getText().toString();

                    chislo = Float.parseFloat(value);
                    bugetMounth.setText(String.format("%.1f",(chislo / 12)));
                    bugetDay.setText(String.format("%.1f",chislo / 365));
                    bugetWeek.setText(String.format("%.1f",((chislo  / 365) * 7)));
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Заполните сумму бюджета!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //обработка нажатия на текстовое поле bugetDay
        bugetDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    value = bugetDay.getText().toString();

                    chislo = Float.parseFloat(value);
                    bugetYear.setText(String.format("%.1f",(chislo * 365)));
                    bugetMounth.setText(String.format("%.1f",(chislo * 365) / 12));
                    bugetWeek.setText(String.format("%.1f",(chislo * 7)));
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Заполните сумму бюджета!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //обработка нажатия на текстовое поле bugetWeek
        bugetWeek.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    value = bugetWeek.getText().toString();

                    chislo = Float.parseFloat(value);
                    bugetYear.setText(String.format("%.1f",((chislo/7) * 365)));
                    bugetDay.setText(String.format("%.1f",(chislo /7)));
                    bugetMounth.setText(String.format("%.1f",((chislo /7) *365)/12));
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Заполните сумму бюджета!", Toast.LENGTH_SHORT).show();
                }
            }
        });


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
