package com.example.yaakovblumer.takego_clientside.controller;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yaakovblumer.takego_clientside.R;
import com.example.yaakovblumer.takego_clientside.model.backend.FactoryMethod;
import com.example.yaakovblumer.takego_clientside.model.entities.Customer;

public class Fill_Details extends AppCompatActivity {


    EditText id,firstName,lastName, phoneNum, email, creditCardNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_fill__details );

            id = ((EditText) findViewById( R.id.Id ));
            firstName = ((EditText) findViewById( R.id.FirstName ));
            lastName = ((EditText) findViewById( R.id.LastName ));
            phoneNum = ((EditText) findViewById( R.id.PhoneNum ));
            email = ((EditText) findViewById( R.id.Email ));
            creditCardNum = ((EditText) findViewById( R.id.CreditCardNum ));

            throw new Exception("Congratulations! Car with ID number: " + id.getText().toString()+ "added to the database. \n" );
        }
        catch(Exception ex){};

    }

    public void btnAddUserClick(View view) {
        try
        {

            new AsyncTask<Void, Void, Long>() {

                Customer customer = new Customer(
                        id.getText().toString(),
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        phoneNum.getText().toString(),
                        email.getText().toString(),
                        creditCardNum.getText().toString()   );

                @Override
                protected void onPostExecute(Long idResult) {
                    super.onPostExecute(idResult);
                    if (idResult > 0)
                        Toast.makeText(getBaseContext(), "insert id: " + id.getText().toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                protected Long doInBackground(Void... params) {
                    return FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).addCustomer(customer);


                }
            }.execute();

            this.finish();

            //throw new Exception("Congratulations! Car with ID number: " + id.getText().toString()+ " added to the database. \n" );
        }
        catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        };
    }
}
