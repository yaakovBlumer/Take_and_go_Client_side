package com.example.yaakovblumer.takego_clientside.controller;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yaakovblumer.takego_clientside.R;
import com.example.yaakovblumer.takego_clientside.model.backend.FactoryMethod;
import com.example.yaakovblumer.takego_clientside.model.entities.Car;
import com.example.yaakovblumer.takego_clientside.model.utils.ConstantsAndEnums;

import java.util.ArrayList;

/*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link cars.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link cars#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cars extends Fragment {

    ListView ListOfCars;
    static String temp=new String("");
    ArrayAdapter<Car> carArrayAdapter;
    static ArrayList<Car> carArrayList=new ArrayList<>();

    //private OnFragmentInteractionListener mListener;

    public cars() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_cars, container, false);

        ListOfCars =  (ListView)view.findViewById( R.id.ListOfCars );

        temp="";

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... params) {
                carArrayList.clear();
                carArrayList.addAll(FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).allCarAvailable());
                for (Car item : carArrayList) {
                    temp+=item.ToString();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                try {
                    super.onPostExecute(aVoid);
                    // textView4.setText(temp);
                    carArrayAdapter.notifyDataSetChanged();
                    ListOfCars.setAdapter(carArrayAdapter);


                } catch (Exception e) {
                    Log.w(ConstantsAndEnums.Log.APP_LOG, e.getMessage() );
                   // Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                }

            }

        }.execute();

        return view;
    }


    /*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    */
}
