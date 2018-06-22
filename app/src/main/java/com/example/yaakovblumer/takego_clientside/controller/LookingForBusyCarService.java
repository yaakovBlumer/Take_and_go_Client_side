package com.example.yaakovblumer.takego_clientside.controller;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.yaakovblumer.takego_clientside.R;
import com.example.yaakovblumer.takego_clientside.model.backend.FactoryMethod;
import com.example.yaakovblumer.takego_clientside.model.datasource.MySQL_DB_manager;
import com.example.yaakovblumer.takego_clientside.model.entities.Car;


import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LookingForBusyCarService extends IntentService {
    ArrayList<Car> carAvailable=new ArrayList<Car>();
    ArrayList<Car> tempCarAvailable=new ArrayList<Car>();
    ArrayList<Car> lastCarAvailable=new ArrayList<Car>();
    LogIn login=new LogIn();

    boolean isFirstTime=true;
    static int count = 1;
    int id = 0, startId = -1;
    boolean isRun = false;
    final String TAG = "testService";
    private Timer timer = new Timer();
    static final int UPDATE_INTERVAL = 1000 * 10;
    public static final String PARAM_OUT_MSG = "OUT_MESSAGE";

    public LookingForBusyCarService() {
        super("LookingForBusyCarService");

        id = count++;     }

        @Override
        protected void onHandleIntent(Intent intent) {
        while (isRun) {
            try {
                if(isFirstTime) {
                    isFirstTime = false;

                    new AsyncTask<Void, Void, Void>() {


                        @Override
                        protected Void doInBackground(Void... params) {

                            carAvailable = FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).allCarAvailable();

                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {

                            super.onPostExecute(aVoid);
                            Intent broadcastIntent = new Intent();
                            broadcastIntent.setAction(ResponseReceiver.ACTION_RESP);
                            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                            //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
                            //String currentDateandTime = sdf.format(new Date());
                            broadcastIntent.putExtra(PARAM_OUT_MSG, "Cars available: " + carAvailable.toString());
                            sendBroadcast(broadcastIntent);

                            Log.d("LookingForBusyCarService", "Start Sending message..");
                            // Toast.makeText(this, "Service Sending", Toast.LENGTH_LONG).show();

                        }

                    }.execute();

                }
                else
                {

                    new AsyncTask<Void, Void, Void>() {


                        @Override
                        protected Void doInBackground(Void... params) {

                            tempCarAvailable = FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).allCarAvailable();


                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {

                            super.onPostExecute(aVoid);



                            for (Car item : tempCarAvailable) {
                                if (!carAvailable.contains(item))
                                    lastCarAvailable.add(item);
                            }

                            carAvailable=tempCarAvailable;
                            if(!lastCarAvailable.isEmpty())
                            {
                                Intent broadcastIntent = new Intent();
                                broadcastIntent.setAction(ResponseReceiver.ACTION_RESP);
                                broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                                //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
                                //String currentDateandTime = sdf.format(new Date());
                                broadcastIntent.putExtra(PARAM_OUT_MSG, "Last cars available: " + lastCarAvailable.toString());
                                sendBroadcast(broadcastIntent);
                                Log.d("LookingForBusyCarService", "Last cars available: " + lastCarAvailable.toString());
                            }

                        try {
                            Thread.sleep(1000*10);
                        }
                        catch(InterruptedException e) {e.printStackTrace();}





                        }

                    }.execute();

                }

             /*   if(isFirstTime)
                {
                    isFirstTime=false;
                     login.carAvailableList(carAvailable);
                            //FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).allCarAvailable();

                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(ResponseReceiver.ACTION_RESP);
                    broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
                    //String currentDateandTime = sdf.format(new Date());
                    broadcastIntent.putExtra(PARAM_OUT_MSG, "Cars available: " + carAvailable.toString());
                    sendBroadcast(broadcastIntent);

                    Log.d("LookingForBusyCarService", "Start Sending message..");
                    // Toast.makeText(this, "Service Sending", Toast.LENGTH_LONG).show();

                }

                else {

                     login.carAvailableList(tempCarAvailable);
                    //FactoryMethod.getDataSource(FactoryMethod.Type.MySQL).allCarAvailable();

                    for (Car item : tempCarAvailable) {
                        if (!carAvailable.contains(item))
                            lastCarAvailable.add(item);
                    }

                    carAvailable=tempCarAvailable;
                    if(!lastCarAvailable.isEmpty())
                    {
                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction(ResponseReceiver.ACTION_RESP);
                        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
                        //String currentDateandTime = sdf.format(new Date());
                        broadcastIntent.putExtra(PARAM_OUT_MSG, "Last cars available: " + lastCarAvailable.toString());
                        sendBroadcast(broadcastIntent);
                        Log.d("LookingForBusyCarService", "Last cars available: " + lastCarAvailable.toString());
                    }

                }

                Thread.sleep(1000*10);
                */
            }
           // catch(InterruptedException e) {e.printStackTrace();}
            catch (Exception e) { e.getMessage();}
           // Log.d(TAG, serviceInfo() + " print ...");
        }     }

            String serviceInfo()
            {
                return "service [" + id + "] startId = " + startId;
            }

            @Override
            public void onCreate()
            {
                super.onCreate();

                id++;
                Log.d(TAG, serviceInfo() + " onCreate ...");

    /////////////////////
                Notification.Builder nBuilder = new Notification.Builder(getBaseContext());
                nBuilder.setSmallIcon(R.drawable.ic_car_rent_notification);
                nBuilder.setContentTitle("Looking For Busy Car - Take&GoService");
                nBuilder.setContentText("Looking For Busy Car All Any 10 Seconds");
                Notification notification = nBuilder.build();
                startForeground(1234, notification);

    }

        @Override
        public void onDestroy() {
        Log.d(TAG, serviceInfo() + " onDestroy ...");
        isRun = false;
        super.onDestroy();     }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        isRun = true;
        Log.d(TAG, serviceInfo() + " onStartCommand start ...");
        return super.onStartCommand(intent, flags, startId);     }
}


/*

    private Timer timer = new Timer();
    static final int UPDATE_INTERVAL = 1000 * 10;
    public static final String PARAM_OUT_MSG = "OUT_MESSAGE";



    ////////////////////////
    static int count = 1;
    int id = 0, startId = -1;
    boolean isRun = false;
    final String TAG = "testService";
    ////////////////////////
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

/*



    public LookingForBusyCarService(String name) {
        super(name);
        id = count++;
    }

    public LookingForBusyCarService() {
        super("dafault constractor of LookingForBusyCarService");

        id = count++;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        id++;
        Log.d(TAG, serviceInfo() + " onCreate ...");

        Notification.Builder nBuilder = new Notification.Builder(getBaseContext());
        nBuilder.setSmallIcon(R.drawable.ic_car_rent_notification);
        nBuilder.setContentTitle("Looking For Busy Car - Take&GoService");
        nBuilder.setContentText("Looking For Busy Car All Any 10 Seconds");
        Notification notification = nBuilder.build();
        startForeground(1234, notification);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
       /* while (isRun)
        {
            try {
                Thread.sleep(1000);}
            catch (InterruptedException e) { e.printStackTrace(); }
            Log.d(TAG, serviceInfo() + " print ...");

        }*/




/*
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Log.d("MyService", "Start Sending message...");

                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(ResponseReceiver.ACTION_RESP);
                broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
                String currentDateandTime = sdf.format(new Date());
                broadcastIntent.putExtra(PARAM_OUT_MSG, "Service Check At Time: " + currentDateandTime);
                sendBroadcast(broadcastIntent);
                Log.d("MyService", "End Sending message...");
            }
        }, 1, UPDATE_INTERVAL);


    }

    String serviceInfo()
    {
        return "service [" + id + "] startId = " + startId;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

       /* timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Log.d("MyService", "Start Sending message...");

                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(ResponseReceiver.ACTION_RESP);
                broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");
                String currentDateandTime = sdf.format(new Date());
                broadcastIntent.putExtra(PARAM_OUT_MSG, "Service Check At Time: " + currentDateandTime);
                sendBroadcast(broadcastIntent);
                Log.d("MyService", "End Sending message...");
            }
        }, 1, UPDATE_INTERVAL);*/



/*

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        //
        Object obj = getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManager notificationManager = (NotificationManager)obj;
        notificationManager.cancel(1234);
        //
        Log.d(TAG, serviceInfo() + " onDestroy ...");
        isRun = false;
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }


}
*/