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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link customer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link customer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class customer extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextView textView8, textView10, textView12, textView14, textView16, textView18, textView20, textView22, textView24;


    static String temp=new String("");
    ArrayAdapter<Car> carArrayAdapter;
    ArrayAdapter<CarModel> carModelArrayAdapter;
    static ArrayList<Car> carArrayList=new ArrayList<>();
    static ArrayList<CarModel> carModelArrayList=new ArrayList<>();
    Car car;
    CarModel carModel;
    private OnFragmentInteractionListener mListener;

    public customer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment customer.
     */
    // TODO: Rename and change types and number of parameters
    public static customer newInstance(String param1, String param2) {
        customer fragment = new customer();
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


        textView8.setText(carModel.getModelName());
        textView10.setText(carModel.getCompanyName());
        textView12.setText(carModel.getEngineVolume());
        textView14.setText(carModel.getGearbox().toString());
        textView16.setText(carModel.getNumOfSeats());
        textView18.setText(carModel.getCarKind().toString());
        textView20.setText(car.getProductionDate());
        textView22.setText(car.getMileage());
        textView24.setText(car.getLicenseNumber());


        return view;
    }

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
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



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
