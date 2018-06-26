package com.example.yaakovblumer.takego_clientside.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yaakovblumer.takego_clientside.R;
import com.example.yaakovblumer.takego_clientside.model.backend.FactoryMethod;
import com.example.yaakovblumer.takego_clientside.model.entities.Car;
import com.example.yaakovblumer.takego_clientside.model.entities.Order;
import com.example.yaakovblumer.takego_clientside.model.utils.ConstantsAndEnums;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    cars Cars = null;
    customer Customer = null;
    Branches_sec branches_Sec = null;
    about About = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
/*
       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      //  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /*
        @Override
        public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }*/
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_about:
                fragment = getAboutFragment();
                break;
            case R.id.navigation_branches:
                fragment = getBranches_secFragment();
                break;
            case R.id.navigation_cars:
                fragment = getCarsFragment();
                break;
            case R.id.navigation_customer:
                fragment = getCustomerFragment();
                break;
            default:
                break;
        }


        if (fragment != null) {
            changeFragement(fragment);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        setTitle(item.getTitle());
        return true;
    }


    protected void changeFragement(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frgament_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    /*
    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_about:
                    changeFragement(odot);
                    return true;
                case R.id.navigation_branches:
                    changeFragement(snifim);
                    return true;
                case R.id.navigation_cars:
                    changeFragement(oto);
                    return true;
                case R.id.navigation_customer:
                    changeFragement(kone);
                    return true;

            }
            return false;
        }
    };


    */


    private customer getCustomerFragment() {
        if (Customer == null) {
            Customer = new customer();
        }
        return Customer;
    }


    private cars getCarsFragment() {
        try {
                if (Cars == null) {
                Cars = new cars();
            }

        }catch (Exception e){Log.d("car constractor",e.getMessage());}
        return Cars;
    }

    private Branches_sec getBranches_secFragment() {
        if (branches_Sec == null) {
            branches_Sec = new Branches_sec();
        }
        return branches_Sec;
    }


    private about getAboutFragment() {
        if (About == null) {
            About = new about();
        }
        return About;
    }

    //customer fragment click
    public void closeOrderOnClick(View v)
    {

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



    //cars fragment on click
    public void orderCarOnClickC(View view)
    {
        new AsyncTask<Void, Void, Long>() {


          /*  Order order=new Order(
                    "468711",
                    ConstantsAndEnums.orderMode.OPEN,
                    getCarsFragment().ListOfCars.getSelectedItem().toString(),
                    "22/06/93",
                    "00/00/00",
                    1,
                    2,
                    false,
                    3,
                    4,
                    "45567");*/

            @Override
            protected void onPostExecute(Long idResult) {
                super.onPostExecute(idResult);
                //     if (idResult > 0)
                //  Toast.makeText(getBaseContext(), "insert Branch Number: " + BranchNum.getText().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            protected Long doInBackground(Void... params) {
                return null;//FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).addOrder(order);

            }
        }.execute();

    }


    //branch_sec fragment on click
    public void showCarsOnClick(View view)
    {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... params) {
                if(getBranches_secFragment().carArrayList==null)
                   getBranches_secFragment().carArrayList=new ArrayList<>();
                getBranches_secFragment().carArrayList.clear();
                getBranches_secFragment().carArrayList=FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).allCarAvailableInBranch(getBranches_secFragment().BranchNumSpinner.getSelectedItem().toString());
                //if(getBranches_secFragment().carArrayList==null)
                 //   getBranches_secFragment().carArrayList=new ArrayList<>();

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                try {
                    super.onPostExecute(aVoid);
                    // textView4.setText(temp);
                    if(getBranches_secFragment().carArrayList!=null) {
                        getBranches_secFragment().carArrayAdapter.clear();
                        getBranches_secFragment().carArrayAdapter.addAll(getBranches_secFragment().carArrayList);
                        getBranches_secFragment().carArrayAdapter.notifyDataSetChanged();
                      /*  getBranches_secFragment().carArrayAdapter = new ArrayAdapter<Car>(getBranches_secFragment().getContext(), R.layout.car_row, getBranches_secFragment().carArrayList) {
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
                        */

                         getBranches_secFragment().carListView.setAdapter(getBranches_secFragment().carArrayAdapter);
                    }
                    if(getBranches_secFragment().carArrayList==null) {
                        getBranches_secFragment().carArrayList = new ArrayList<>();
                        getBranches_secFragment().carArrayAdapter.clear();
                        getBranches_secFragment().carArrayAdapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    Log.w(ConstantsAndEnums.Log.APP_LOG, e.getMessage() );
                    //   Toast.makeText(Branches_sec.this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            }
        }.execute();
    }


    //branch_sec fragment on click
    public void orderCarOnClickB(View view)
    {
        new AsyncTask<Void, Void, Long>() {


           /* Order order=new Order(
                    "468711",
                    ConstantsAndEnums.orderMode.OPEN,
                    getBranches_secFragment().carListView.getSelectedItem().toString(),
                    "22/06/93",
                    "00/00/00",
                    1,
                    2,
                    false,
                    3,
                    4,
                    "45567");  */

            @Override
            protected void onPostExecute(Long idResult) {
                super.onPostExecute(idResult);
                //     if (idResult > 0)
                //  Toast.makeText(getBaseContext(), "insert Branch Number: " + BranchNum.getText().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            protected Long doInBackground(Void... params) {
                return null; // FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).addOrder(order);

            }
        }.execute();

    }

}