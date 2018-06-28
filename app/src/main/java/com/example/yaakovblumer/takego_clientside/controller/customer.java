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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaakovblumer.takego_clientside.model.backend.FactoryMethod;
import com.example.yaakovblumer.takego_clientside.model.backend.MYSharedPreferences;
import com.example.yaakovblumer.takego_clientside.model.entities.Branch;
import com.example.yaakovblumer.takego_clientside.model.entities.Car;
import com.example.yaakovblumer.takego_clientside.model.entities.CarModel;


import com.example.yaakovblumer.takego_clientside.R;
import com.example.yaakovblumer.takego_clientside.model.entities.Order;
import com.example.yaakovblumer.takego_clientside.model.utils.ConstantsAndEnums;

import java.util.ArrayList;

public class customer extends Fragment {

    TextView customerNum,modeOfOrder, carNumber, rentStartDate, rentEndDate, kilometresAtStart, kilometresAtEnd,
     isInsertDelek, howMuchDelekInsert, howMuchNeedPay, orderNum;
    Spinner OrdersNumSpinner;


    MYSharedPreferences mySharedPreferences=new MYSharedPreferences();
String ourIdCustomer=null;
    static String temp=new String("");
    ArrayAdapter<Car> carArrayAdapter;
    ArrayAdapter<CarModel> carModelArrayAdapter;
    static ArrayList<Order> orderArrayList=new ArrayList<>();
    static ArrayList<String> orderNumSimpleList = new ArrayList<String>();
    String selectedOrder=null;


    static ArrayList<Car> carArrayList=new ArrayList<>();
    static ArrayList<CarModel> carModelArrayList=new ArrayList<>();
    Car car=null;
    CarModel carModel=null;

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
try {
    ourIdCustomer = mySharedPreferences.idInSharedPreferencesNow(getContext());
}
catch (Exception e){}



        customerNum = ((TextView) view.findViewById(R.id.customerNumTextView));
        modeOfOrder = ((TextView) view.findViewById(R.id.modeOfOrderTextView));

        carNumber = ((TextView) view.findViewById(R.id.carNumberTextView));

        rentStartDate = ((TextView) view.findViewById(R.id.rentStartDateTextView));

        rentEndDate = ((TextView) view.findViewById(R.id.rentEndDateTextView));

        kilometresAtStart = ((TextView) view.findViewById(R.id.kilometresAtStartTextView));

        kilometresAtEnd = ((TextView) view.findViewById(R.id.kilometresAtEndTextView));

        isInsertDelek = ((TextView) view.findViewById(R.id.isInsertDelekTextView));
        howMuchDelekInsert = ((TextView) view.findViewById(R.id.howMuchDelekInsertTextView));

        howMuchNeedPay = ((TextView) view.findViewById(R.id.howMuchNeedPayTextView));

        orderNum = ((TextView) view.findViewById(R.id.orderNumTextView));




        OrdersNumSpinner = (Spinner) view.findViewById(R.id.ordersSpinner);
        updateOrdersSpinner();
        ////////////////////////
        OrdersNumSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> carArrayAdapter, View view, int position, long l) {
                selectedOrder=(String) OrdersNumSpinner.getItemAtPosition(position);
                updateAllOurTextView(selectedOrder);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // your stuff
            }
        });



        OrdersNumSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                selectedOrder=(String) OrdersNumSpinner.getItemAtPosition(i).toString();

                String s = ((String) OrdersNumSpinner.getItemAtPosition(i));
                updateAllOurTextView(selectedOrder);

                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                // adapter.dismiss(); // If you want to close the adapter
            }
        });
        ////////////////////////////////////


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


    public void updateOrdersSpinner() {
        /////////////////////////


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                orderArrayList=new ArrayList<>();
                orderArrayList.clear();

                orderArrayList=FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).allOrdersOpen();


                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                try {
                    super.onPostExecute(aVoid);

                    if (orderArrayList == null) {
                        orderArrayList = new ArrayList<>();
                    }
                    orderNumSimpleList=new ArrayList<>();
                    orderNumSimpleList.clear();
                    orderNumSimpleList=getALLThisCustomerOrders(orderArrayList);
                    // branchArrayAdapter.notifyDataSetChanged();
                    OrdersNumSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, orderNumSimpleList));

                } catch (Exception e) {
                    Log.w(ConstantsAndEnums.Log.APP_LOG, e.getMessage());
                    //    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT ).show();

                }


            }
        }.execute();
    }


    public ArrayList<String> getALLThisCustomerOrders(ArrayList<Order> allOrders )
    {

        ArrayList<String> temp = new ArrayList<>();
        for (Order item : allOrders)
        {
            if (item.getCustomerNum().equals(ourIdCustomer))
                temp.add(item.getOrderNum());
        }

        return temp;
    }

    public Order getOrderByOrderNum(String orderNum )
    {

        Order temp = null;
        for (Order item : orderArrayList)
        {
            if (item.getOrderNum().equals(ourIdCustomer))
                return item;
        }

        return temp;
    }

    public void updateAllOurTextView(String orderNum)

    {
        customerNum.setText(getOrderByOrderNum(orderNum).getCustomerNum());
        modeOfOrder.setText(getOrderByOrderNum(orderNum).getModeOfOrder().toString());

        carNumber.setText(getOrderByOrderNum(orderNum).getCarNumber());

        rentStartDate.setText(getOrderByOrderNum(orderNum).getRentStartDate());

        rentEndDate.setText(getOrderByOrderNum(orderNum).getRentEndDate());

        kilometresAtStart.setText(getOrderByOrderNum(orderNum).getKilometresAtStart());

        kilometresAtEnd.setText(getOrderByOrderNum(orderNum).getKilometresAtEnd());

        isInsertDelek.setText(( Boolean.toString(getOrderByOrderNum(orderNum).getIsInsertDelek())));
        howMuchDelekInsert.setText(getOrderByOrderNum(orderNum).getHowMuchDelekInsert());

        howMuchNeedPay.setText(getOrderByOrderNum(orderNum).getHowMuchNeedPay());

        this.orderNum.setText(getOrderByOrderNum(orderNum).getOrderNum());

    }


    }
