package com.example.yaakovblumer.takego_clientside.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

   // static ArrayList<Branch> branchArrayList=new ArrayList<>();
    static ArrayList<Car> carArrayList=new ArrayList<>();

    //ArrayAdapter<Branch> branchArrayAdapter=null;
    ArrayAdapter<Car> carArrayAdapter=null;

    Spinner BranchNumSpinner;
    static ArrayList<Branch> BranchesSimpleList = new ArrayList<Branch>();
    static ArrayList<String> BranchesCodeSimpleList = new ArrayList<String>();
ListView carListView;


    //  ListView _dynamic;

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }


    private OnFragmentInteractionListener mListener;

    public Branches_sec() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view=inflater.inflate(R.layout.fragment_branches_sec, container, false);
        carListView =  (ListView) view.findViewById( R.id.carListView );
       CreateCarAdapter();

        BranchNumSpinner=(Spinner)view.findViewById(R.id.BranchNumSpinner);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                BranchesSimpleList.addAll(FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).allBranches());
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
                   // branchArrayAdapter.notifyDataSetChanged();
                    BranchNumSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, BranchesCodeSimpleList));

                } catch (Exception e) {
                    Log.w(ConstantsAndEnums.Log.APP_LOG, e.getMessage() );
                //    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT ).show();

                }


            }
        }.execute();

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
       /*
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/

    }

   /* @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

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




    @SuppressLint("StaticFieldLeak")
    private void CreateCarAdapter() {
        carArrayAdapter = new ArrayAdapter<Car>(getContext(), R.layout.car_row, carArrayList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(getContext(), R.layout.car_row, null);
                }


                Car car = (Car) this.getItem(position);
                TextView modelCode = (TextView) convertView.findViewById(R.id.modelCode);
                TextView productionDate = (TextView) convertView.findViewById(R.id.productionDate);
                TextView mileage = (TextView) convertView.findViewById(R.id.mileage);
                TextView licenseNumber = (TextView) convertView.findViewById(R.id.licenseNumber);
                TextView homeBranch = (TextView) convertView.findViewById(R.id.homeBranch);
                TextView averageCostPerDay = (TextView) convertView.findViewById(R.id.averageCostPerDay);
                TextView busy = (TextView) convertView.findViewById(R.id.busy);


                modelCode.setText("Model Code:" + car.getModelCode());
                productionDate.setText("Production Date:" + car.getProductionDate());
                mileage.setText("Mileage: " + car.getMileage());
                licenseNumber.setText("License Number:" + car.getLicenseNumber());
                homeBranch.setText("Home Branch:" + car.getHomeBranch());
                averageCostPerDay.setText("Average Cost Per Day: " + car.getAverageCostPerDay());
                busy.setText("Busy:" + car.getBusy());
                return convertView;
            }
        };
    }

}
