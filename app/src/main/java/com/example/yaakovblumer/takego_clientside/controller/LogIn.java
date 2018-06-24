package com.example.yaakovblumer.takego_clientside.controller;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
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
import static com.example.yaakovblumer.takego_clientside.controller.LogIn.ResponseReceiver.ACTION_RESP;

public class LogIn extends AppCompatActivity {

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

                Notify("Take&Go Client side-Receiver", outMsg);

                Log.d("Take&Go Client side-Receiver", outMsg);


            } catch (Exception ex) {
                Log.w(ConstantsAndEnums.Log.APP_LOG, ex.getMessage());
            }
        }



    }

    public void Notify(String notificationTitle, String notificationMessage) {


     //////////////////////////////
        /*
            final int NOTIFY_ID = 1002;


            // There are hardcoding only for show it's just strings
            String name = "my_package_channel";
            String id = "my_package_channel_1"; // The user-visible name of the channel.
            String description = "my_package_first_channel"; // The user-visible description of the channel.

            Intent intent;
            PendingIntent pendingIntent;
            NotificationCompat.Builder builder;

            if (notifManager == null) {
                notifManager =
                        (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = notifManager.getNotificationChannel(id);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(id, name, importance);
                    mChannel.setDescription(description);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notifManager.createNotificationChannel(mChannel);
                }
                builder = new NotificationCompat.Builder(this, id);

                intent = new Intent(this, NotificationView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

                builder.setContentTitle(notificationTitle)  // required
                        .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                        .setContentText(notificationMessage)  // required
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setTicker(notificationTitle)
                        .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            } else {

                builder = new NotificationCompat.Builder(this);

                intent = new Intent(this, NotificationView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

                builder.setContentTitle(notificationTitle)                           // required
                        .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                        .setContentText(notificationMessage)  // required
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setTicker(notificationTitle)
                        .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                        .setPriority(Notification.PRIORITY_HIGH);
            } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Notification notification = builder.build();
            notifManager.notify(NOTIFY_ID, notification);

*/
        ///////////////////////////////////////



        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_car_rent_notification)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle());

         NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
       /* Intent resultIntent = new Intent(this, NotificationView.class);
        resultIntent.putExtra("text",notificationMessage);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationView.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);*/
        notificationManager.notify(888, builder.build());


    }


}