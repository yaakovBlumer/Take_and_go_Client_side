package com.example.yaakovblumer.takego_clientside.controller;


import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;

import com.example.yaakovblumer.takego_clientside.R;

public class NotificationView extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_view);

        TextView text1 = (TextView) findViewById(R.id.text1);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            text1.setText(extras.getString("text").toString());
        }
    }




}