package com.example.yaakovblumer.takego_clientside.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.yaakovblumer.takego_clientside.model.utils.ConstantsAndEnums;

public class ResponseReceiver extends BroadcastReceiver {

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        public static final String ACTION_RESP =
                "takego_clientside.yaakovblumer.example.com.intent.action.MESSAGE_PROCESSED";


    public static final String PARAM_OUT_MSG = "OUT_MESSAGE";
    private Context getBaseContext() {
        return this.getBaseContext();
    }




        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Log.d("Response Receiver", "message come...");


                String outMsg = intent.getStringExtra(this.PARAM_OUT_MSG);

                LogIn login = new LogIn();
                login.Notify("Take&Go Client side-Receiver", outMsg);
                Log.d("Take&Go Client side-Receiver", outMsg);


            } catch (Exception ex) {
                Log.w(ConstantsAndEnums.Log.APP_LOG, ex.getMessage());
            }
        }



    }



