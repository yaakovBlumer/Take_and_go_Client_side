package com.example.yaakovblumer.takego_clientside.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yaakovblumer.takego_clientside.R;
import com.example.yaakovblumer.takego_clientside.model.backend.FactoryMethod;
import com.example.yaakovblumer.takego_clientside.model.backend.MYSharedPreferences;
import com.example.yaakovblumer.takego_clientside.model.entities.Customer;
import com.example.yaakovblumer.takego_clientside.model.utils.ConstantsAndEnums;

import java.util.ArrayList;

public class LogIn extends AppCompatActivity {

    MYSharedPreferences mySharedPreferences;
    Customer customer;
    String ourId="";
    String ourPass="";
    boolean tmpFlag=false;
    boolean elseFlag=false;
    boolean ifFlag=false;

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int ID_LENGTH = 9;
    private static String Id="";
    private static String Password="";
    Intent intent_name = new Intent();
   // private static final View view=null;




    EditText id, password;

    private ResponseReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        id = ((EditText) findViewById( R.id.Id));
        password = ((EditText) findViewById( R.id.Password));
        customer= new Customer();
        mySharedPreferences=new MYSharedPreferences();
        intent_name.setClass(getApplicationContext(),Register.class);



        ///////////////////////
        startService(new Intent(getBaseContext(), LookingForBusyCarService.class));

        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);

        ///////////////////////

    }


    public void loginOnClickBtn(View view)
    {

try {

    ourId = id.getText().toString();
    ourPass = password.getText().toString();

    if (TextUtils.isEmpty(ourId) || ourId.length() != ID_LENGTH)
        throw new Exception("You must have 9 characters in your id.");

    if (TextUtils.isEmpty(ourPass) || ourPass.length() < MIN_PASSWORD_LENGTH)
        throw new Exception("You must have 8 characters in your password at least.");

    //everything is good.
if (mySharedPreferences.isStringExistsInSharedPreferences(this,ourId,"ID")==true
        && mySharedPreferences.isStringExistsInSharedPreferences(this,ourPass,"PASSWORD")==true)
    Toast.makeText(this, "Load Application..", Toast.LENGTH_SHORT).show();

//good id and error with password.
else if(mySharedPreferences.isStringExistsInSharedPreferences(this,ourId,"ID")==true)
    Toast.makeText(this, "Error with password.", Toast.LENGTH_SHORT).show();

//searching in sql_db in web.
else {
        new AsyncTask<Void, Void, Void>() {


        @Override
        protected Void doInBackground(Void... params) {

           customer= FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).isExistsCustomer(ourId);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            //everything is good.
            if (customer != null && customer.getId().equals(ourId) && customer.getPassword().equals(ourPass)) {
                 mySharedPreferences.saveSharedPreferences(getBaseContext(), ourId, ourPass);
                //
                Toast.makeText(getBaseContext(), "Load Application..", Toast.LENGTH_SHORT).show();
            }

            //good id and error with password.
            else if (customer != null && customer.getId().equals(ourId)) {
                mySharedPreferences.saveSharedPreferences(getBaseContext(), ourId, ourPass);
                Toast.makeText(getBaseContext(), "Error with password.", Toast.LENGTH_SHORT).show();
            }

            //not exists this customer at all. go to register a new one.
            else {
                try {
                   // Toast.makeText(getBaseContext(), "Go to register your details.", Toast.LENGTH_SHORT).show();

                    startActivity(intent_name);

                }

  catch(Exception ex)
            {
                Toast.makeText(LogIn.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                Log.w(ConstantsAndEnums.Log.APP_LOG, ex.getMessage());
            }

    }




        }

    }.execute();

}
} catch(InterruptedException e) {e.printStackTrace();}
  catch (Exception ex)
{
    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
    Log.w(ConstantsAndEnums.Log.APP_LOG, ex.getMessage());
}

    }


    public void registerOnClickBtn(View view)
    {
        startActivity( new Intent( LogIn.this,Register.class ) );
    }



}
