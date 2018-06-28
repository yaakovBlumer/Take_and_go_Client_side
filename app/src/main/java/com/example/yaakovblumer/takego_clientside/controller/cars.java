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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaakovblumer.takego_clientside.model.backend.FactoryMethod;

import com.example.yaakovblumer.takego_clientside.R;
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



    ListView ListOfCars=null;
    ArrayAdapter<Car> carArray_Adapter=null;
    static ArrayList<Car> car_ArrayList=new ArrayList<>();;
    Car selectedCarC=null;

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }


    private cars.OnFragmentInteractionListener mListener;

    public cars() {
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
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_cars, container, false);
            ListOfCars = (ListView) view.findViewById( R.id.ListOfCarsView );
            CreateCar_Adapter();

     ////////////////////////
     ListOfCars.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> carArrayAdapter, View view, int position, long l) {
             selectedCarC=(Car) ListOfCars.getItemAtPosition(position);
         }
         @Override
         public void onNothingSelected(AdapterView<?> adapterView) {
             // your stuff
         }
     });



     ListOfCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

             selectedCarC=(Car) ListOfCars.getItemAtPosition(i);

             String s = ((Car) ListOfCars.getItemAtPosition(i)).getLicenseNumber();

             Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
             // adapter.dismiss(); // If you want to close the adapter
         }
     });

     updateCarList();


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

     /*   if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/

    }
/*
    @Override
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


    @SuppressLint("StaticFieldLeak")
    private void CreateCar_Adapter() {
        carArray_Adapter = new ArrayAdapter<Car>(this.getContext(), R.layout.car_row, car_ArrayList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(this.getContext(), R.layout.car_row, null);
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



  public void updateCarList() {
        /////////////////////////


        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... params) {
                if (car_ArrayList == null)
                    car_ArrayList = new ArrayList<>();
                car_ArrayList.clear();
                car_ArrayList = FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).allCarAvailable();

                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                try {
                    super.onPostExecute(aVoid);
                    // textView4.setText(temp);
                    if (car_ArrayList != null) {
                        carArray_Adapter.clear();
                        carArray_Adapter.addAll(car_ArrayList);
                        carArray_Adapter.notifyDataSetChanged();
                        ListOfCars.setAdapter(carArray_Adapter);
                    }
                    if (car_ArrayList == null)
                        car_ArrayList = new ArrayList<>();


                } catch (Exception e) {
                    Log.w(ConstantsAndEnums.Log.APP_LOG, e.getMessage());
                    // Toast.makeText( , e.getMessage(), Toast.LENGTH_SHORT ).show();
                }

            }

        }.execute();

    }


}
