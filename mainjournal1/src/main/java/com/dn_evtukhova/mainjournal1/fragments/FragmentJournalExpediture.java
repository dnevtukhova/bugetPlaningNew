package com.dn_evtukhova.mainjournal1.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dn_evtukhova.mainjournal1.ActivityAddExpediture;
import com.dn_evtukhova.mainjournal1.R;

import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentJournalExpediture.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentJournalExpediture#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentJournalExpediture extends Fragment {
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

    public FragmentJournalExpediture() {
        // Required empty public constructor
    }

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
