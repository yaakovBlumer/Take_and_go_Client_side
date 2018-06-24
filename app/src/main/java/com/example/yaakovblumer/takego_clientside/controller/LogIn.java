package com.example.yaakovblumer.takego_clientside.controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
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

import java.util.Map;
import java.util.Random;

import static com.example.yaakovblumer.takego_clientside.controller.LogIn.ResponseReceiver.ACTION_RESP;

public class LogIn extends AppCompatActivity {

    private static final String GROUP_TAKE_AND_GO = "com.example.yaakovblumer.takego_clientside.controller";
    private static final String CHANNEL_ID ="26" ;
    private static final int SUMMARY_ID = 0;
    private int noti_id =1 ;

    MYSharedPreferences mySharedPreferences;
    Customer customer;
    String ourId = "";
    String ourPass = "";
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int ID_LENGTH = 9;
    Intent intent_register = new Intent();
    Intent intent_home = new Intent();

    EditText id, password;


    private ResponseReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        id = ((EditText) findViewById(R.id.Id));
        password = ((EditText) findViewById(R.id.Password));
        customer = new Customer();
        mySharedPreferences = new MYSharedPreferences();
        intent_register.setClass(getApplicationContext(), Register.class);
        intent_home.setClass(getApplicationContext(), Home.class);


        ///////////////////////

        IntentFilter filter = new IntentFilter(ACTION_RESP);

        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver=new ResponseReceiver();
        registerReceiver(receiver, filter);

        ///////////////////////

    }


    public void loginOnClickBtn(View view) {

        try {

            ourId = id.getText().toString();
            ourPass = password.getText().toString();

            if (TextUtils.isEmpty(ourId) || ourId.length() != ID_LENGTH)
                throw new Exception("You must have 9 characters in your id.");

            if (TextUtils.isEmpty(ourPass) || ourPass.length() < MIN_PASSWORD_LENGTH)
                throw new Exception("You must have 8 characters in your password at least.");

            //everything is good.
            if (mySharedPreferences.isStringExistsInSharedPreferences(this, ourId, "ID") == true
                    && mySharedPreferences.isStringExistsInSharedPreferences(this, ourPass, "PASSWORD") == true)
              //  startActivity(new Intent(LogIn.this, Home.class));

                startActivity(intent_home);
                // Toast.makeText(this, "Load Application..", Toast.LENGTH_SHORT).show();

//good id and error with password.
            else if (mySharedPreferences.isStringExistsInSharedPreferences(this, ourId, "ID") == true)
                Toast.makeText(this, "Error with password.", Toast.LENGTH_SHORT).show();

//searching in sql_db in web.
            else {
                new AsyncTask<Void, Void, Void>() {


                    @Override
                    protected Void doInBackground(Void... params) {

                        customer = FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).isExistsCustomer(ourId);

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {

                        super.onPostExecute(aVoid);
                        //everything is good.
                        if (customer != null && customer.getId().equals(ourId) && customer.getPassword().equals(ourPass)) {
                            mySharedPreferences.saveSharedPreferences(getBaseContext(), ourId, ourPass);

                            //Toast.makeText(getBaseContext(), "Load Application..", Toast.LENGTH_SHORT).show();

                            startActivity(intent_home);

                        }

                        //good id and error with password.
                        else if (customer != null && customer.getId().equals(ourId)) {
                            Toast.makeText(getBaseContext(), "Error with password.", Toast.LENGTH_SHORT).show();
                        }

                        //not exists this customer at all. go to register a new one.
                        else {
                            try {
                                // Toast.makeText(getBaseContext(), "Go to register your details.", Toast.LENGTH_SHORT).show();

                                startActivity(intent_register);

                            } catch (Exception ex) {
                                Toast.makeText(LogIn.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.w(ConstantsAndEnums.Log.APP_LOG, ex.getMessage());
                            }

                        }


                    }

                }.execute();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            Log.w(ConstantsAndEnums.Log.APP_LOG, ex.getMessage());
        }

    }


    public void registerOnClickBtn(View view) {
        startActivity(new Intent(LogIn.this, Register.class));
    }


    private NotificationManager notifManager;


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

                Notify(noti_id,"Take&Go Client side-Receiver", outMsg);

                Log.d("Take&Go Client side-Receiver", outMsg);
                noti_id++;

            } catch (Exception ex) {
                Log.w(ConstantsAndEnums.Log.APP_LOG, ex.getMessage());
            }
        }



    }

    public void Notify(int id_notification,String notificationTitle, String notificationMessage) {

      //  Random random = new Random();
       // int m = random.nextInt(9999 - 1000);

        NotificationCompat.Builder mBuilder =new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_car_rent_notification)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage);
        Intent intent = new Intent(this, LogIn.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LogIn.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager)     this.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(id_notification, mBuilder.build());



                mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_car_rent_notification);

        NotificationCompat.InboxStyle inboxStyle =
               new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle("New car/s available.");


        inboxStyle.setSummaryText("Take&Go- Receiver service");



        mBuilder.setStyle(inboxStyle);

                stackBuilder = TaskStackBuilder.create(this);

       stackBuilder.addNextIntent(intent);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pIntent);

                mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(0, mBuilder.build());


    }


}