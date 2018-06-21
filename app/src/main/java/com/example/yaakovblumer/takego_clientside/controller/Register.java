package com.example.yaakovblumer.takego_clientside.controller;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.yaakovblumer.takego_clientside.R;
import com.example.yaakovblumer.takego_clientside.model.backend.FactoryMethod;
import com.example.yaakovblumer.takego_clientside.model.utils.ConstantsAndEnums;

public class Register extends AppCompatActivity {
    EditText editText, editText3, editText4;

    private ResponseReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editText = ((EditText) findViewById( R.id.editText));
        editText3 = ((EditText) findViewById( R.id.editText3));
        editText4 = ((EditText) findViewById( R.id.editText4));




        ///////////////////////
        startService(new Intent(getBaseContext(), LookingForBusyCarService.class));

        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);

        ///////////////////////

    }


    public void onClickBtn(View view)
    {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... params) {


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                try {
                    super.onPostExecute(aVoid);

                } catch (Exception e) {
                    Log.w(ConstantsAndEnums.Log.APP_LOG, e.getMessage() );

                }
            }

        }.execute();

    }
}
