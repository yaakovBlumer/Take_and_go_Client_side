package com.example.yaakovblumer.takego_clientside.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yaakovblumer.takego_clientside.R;
import com.example.yaakovblumer.takego_clientside.model.backend.FactoryMethod;
import com.example.yaakovblumer.takego_clientside.model.backend.MYSharedPreferences;
import com.example.yaakovblumer.takego_clientside.model.entities.Customer;

public class Register extends AppCompatActivity {

    Intent intent_home = new Intent();
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int ID_LENGTH = 9;
    MYSharedPreferences mySharedPreferences;
    String ourId = "";
    String ourPass = "";


    EditText id,firstName,lastName, phoneNum, email, creditCardNum, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_register );


            mySharedPreferences= new MYSharedPreferences();
            intent_home.setClass(getApplicationContext(),Home.class);

            id = ((EditText) findViewById( R.id.Id ));
            firstName = ((EditText) findViewById( R.id.FirstName ));
            lastName = ((EditText) findViewById( R.id.LastName ));
            phoneNum = ((EditText) findViewById( R.id.PhoneNum ));
            email = ((EditText) findViewById( R.id.Email ));
            creditCardNum = ((EditText) findViewById( R.id.CreditCardNum ));
            password = ((EditText) findViewById( R.id.Password ));

        }
        catch(Exception ex){Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();};

    }

    public void btnAddUserClick(View view) {
        try
        {
            ourId = id.getText().toString();
            ourPass = password.getText().toString();

            if (TextUtils.isEmpty(ourId) || ourId.length() != ID_LENGTH)
                throw new Exception("You must have 9 characters in your id.");

            if (TextUtils.isEmpty(ourPass) || ourPass.length() < MIN_PASSWORD_LENGTH)
                throw new Exception("You must have 8 characters in your password at least.");

            new AsyncTask<Void, Void, Long>() {

                Customer customer = new Customer(
                        id.getText().toString(),
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        phoneNum.getText().toString(),
                        email.getText().toString(),
                        creditCardNum.getText().toString(),
                        password.getText().toString()   );



                @Override
                protected void onPostExecute(Long idResult) {
                    super.onPostExecute(idResult);
                    if (idResult > 0)
                    {
                        Toast.makeText(getBaseContext(), "insert id: " + id.getText().toString(), Toast.LENGTH_LONG).show();
                        mySharedPreferences.saveSharedPreferences(getBaseContext(), ourId, ourPass);

                       // Toast.makeText(getBaseContext(), "Load Application..", Toast.LENGTH_SHORT).show();

                        startActivity(intent_home);

                    }
                }

                @Override
                protected Long doInBackground(Void... params) {
                    return FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).addCustomer(customer);


                }
            }.execute();




        }
        catch(Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        };
    }
}
