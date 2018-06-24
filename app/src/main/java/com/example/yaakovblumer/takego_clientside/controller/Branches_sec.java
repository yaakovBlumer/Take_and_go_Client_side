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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yaakovblumer.takego_clientside.model.backend.FactoryMethod;
import com.example.yaakovblumer.takego_clientside.model.entities.Branch;

import com.example.yaakovblumer.takego_clientside.R;
import com.example.yaakovblumer.takego_clientside.model.entities.Car;
import com.example.yaakovblumer.takego_clientside.model.entities.Order;
import com.example.yaakovblumer.takego_clientside.model.utils.ConstantsAndEnums;

import java.util.ArrayList;
import java.util.Date;


public class Branches_sec extends Fragment {

    static ArrayList<Branch> branchArrayList=new ArrayList<>();
    static ArrayList<Car> carArrayList=new ArrayList<>();

    static String temp=new String("");
    ArrayAdapter<Branch> branchArrayAdapter;
    ArrayAdapter<Car> carArrayAdapter;

    Spinner spinner;
    static ArrayList<Branch> BranchesSimpleList = null;
    static ArrayList<String> BranchesCodeSimpleList = new ArrayList<String>();

    ListView _dynamic;


    //private OnFragmentInteractionListener mListener;

    public Branches_sec() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_branches_sec, container, false);

        spinner=(Spinner)view.findViewById(R.id.spinner);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                BranchesSimpleList= FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).allBranches();
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                try {
                    if(BranchesCodeSimpleList == null){
                        BranchesCodeSimpleList = new ArrayList<>( );
                    }
                    super.onPostExecute(aVoid);
                    BranchesCodeSimpleList.clear();
                    BranchesCodeSimpleList.addAll( getALLBranchesCode(BranchesSimpleList) );
                    branchArrayAdapter.notifyDataSetChanged();
                    spinner.setAdapter(branchArrayAdapter);

                } catch (Exception e) {
                    Log.w(ConstantsAndEnums.Log.APP_LOG, e.getMessage() );
                //    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT ).show();

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
       /*
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        */
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

    public ArrayList<String> getALLBranchesCode(ArrayList<Branch> CarModelList )
    {

        ArrayList<String> temp=new ArrayList<>();
        for (Branch item : CarModelList)
        {
            temp.add(item.getModelCode());
        }
        return temp;
    }


    public void onButtom3Click(View view)
    {
        temp="";

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... params) {
                carArrayList.clear();
                carArrayList.addAll(FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).allCarAvailableInBranch(spinner.getSelectedItem().toString()));

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
                    _dynamic.setAdapter(carArrayAdapter);

                } catch (Exception e) {
                    Log.w(ConstantsAndEnums.Log.APP_LOG, e.getMessage() );
                 //   Toast.makeText(Branches_sec.this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            }
        }.execute();
    }

    public void orderCarButton(View view){

        new AsyncTask<Void, Void, Long>() {


            Order order=new Order(
                    "468711",
                    ConstantsAndEnums.orderMode.OPEN,
                    _dynamic.getSelectedItem().toString(),
                    "22/06/93",
                    "00/00/00",
                    1,
                    2,
                    false,
                    3,
                    4,
                    "45567");

            @Override
            protected void onPostExecute(Long idResult) {
                super.onPostExecute(idResult);
           //     if (idResult > 0)
                  //  Toast.makeText(getBaseContext(), "insert Branch Number: " + BranchNum.getText().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            protected Long doInBackground(Void... params) {
                return FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).addOrder(order);

            }
        }.execute();
    }
}
