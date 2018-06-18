package com.example.yaakovblumer.takego_clientside.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ResponseReceiver extends BroadcastReceiver {

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        public static final String ACTION_RESP =
                "yaakovblumer.il.example.com.intent.action.MESSAGE_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent) {
            //TextView result = (TextView) findViewById(R.id.text3);
            String text = intent.getStringExtra(LookingForBusyCarService.PARAM_OUT_MSG);
            //  result.setText(text);
        }
    }



