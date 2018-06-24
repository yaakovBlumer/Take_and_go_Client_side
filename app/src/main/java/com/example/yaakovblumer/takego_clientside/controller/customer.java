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
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaakovblumer.takego_clientside.model.backend.FactoryMethod;
import com.example.yaakovblumer.takego_clientside.model.entities.Car;
import com.example.yaakovblumer.takego_clientside.model.entities.CarModel;


import com.example.yaakovblumer.takego_clientside.R;
import com.example.yaakovblumer.takego_clientside.model.utils.ConstantsAndEnums;

import java.util.ArrayList;

public class customer extends Fragment {

    TextView textView8, textView10, textView12, textView14, textView16, textView18, textView20, textView22, textView24;


    static String temp=new String("");
    ArrayAdapter<Car> carArrayAdapter;
    ArrayAdapter<CarModel> carModelArrayAdapter;
    static ArrayList<Car> carArrayList=new ArrayList<>();
    static ArrayList<CarModel> carModelArrayList=new ArrayList<>();
    Car car;
    CarModel carModel;

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }


    private customer.OnFragmentInteractionListener mListener;

    public customer() {
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

        View view=inflater.inflate(R.layout.fragment_customer, container, false);

        textView8=(TextView)view.findViewById(R.id.textView8);
        textView10=(TextView)view.findViewById(R.id.textView10);
        textView12=(TextView)view.findViewById(R.id.textView12);
        textView14=(TextView)view.findViewById(R.id.textView14);
        textView16=(TextView)view.findViewById(R.id.textView16);
        textView18=(TextView)view.findViewById(R.id.textView18);
        textView20=(TextView)view.findViewById(R.id.textView20);
        textView22=(TextView)view.findViewById(R.id.textView22);
        textView24=(TextView)view.findViewById(R.id.textView24);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                car= FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).isExistsCar("77");
                carModel= FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).isExistsCarModel(car.getModelCode());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                try
                {
                    super.onPostExecute(aVoid);
                    //everything is good.
                }
                catch(Exception ex)
                {
                 //   Toast.makeText(LogIn.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.w(ConstantsAndEnums.Log.APP_LOG, ex.getMessage());
                }
                }

        }.execute();

if(carModel!=null) {
    textView8.setText(carModel.getModelName());
    textView10.setText(carModel.getCompanyName());
    textView12.setText(carModel.getEngineVolume());
    textView14.setText(carModel.getGearbox().toString());
    textView16.setText(carModel.getNumOfSeats());
    textView18.setText(carModel.getCarKind().toString());
    textView20.setText(car.getProductionDate());
    textView22.setText(car.getMileage());
    textView24.setText(car.getLicenseNumber());
}


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
        /*
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
*/


    public void CloseOrderBtm(View view)
    {
        int kilometer=5400;

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).closeOrder("1243");
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                try {
                    super.onPostExecute(aVoid);

                } catch (Exception e) {
                    Log.w(ConstantsAndEnums.Log.APP_LOG, e.getMessage() );
                  //  Toast.makeText( AddCar.this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                }


            }
        }.execute();
    }
}
