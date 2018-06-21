package com.example.yaakovblumer.takego_clientside.model.backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;

public class MYSharedPreferences {

    public  void clearSharedPreferences(android.content.Context context, String id, String password) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

        Toast.makeText(context, "clear Preferences", Toast.LENGTH_SHORT).show();
    }

    public boolean isStringExistsInSharedPreferences(android.content.Context context, String value, String key) throws Exception {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String flag="";
        flag=sharedPreferences.getString(key,"");
        if (flag.equals(value))
            return true;
        return false;


       /*  if (sharedPreferences.contains(key))

       {
            Map<String, ?> keys = sharedPreferences.getAll();
            for (Map.Entry<String, ?> entry : keys.entrySet())
            {
                if (entry.getKey().equals(key) && entry.getValue().equals(value) )
                    return true;
                //Toast.makeText(context, "load id", Toast.LENGTH_SHORT).show();
            }

        }

        return false;   */
    }


    public void saveSharedPreferences(android.content.Context context, String id, String password) {
        try {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();



            editor.putString("ID", id);
            editor.putString("PASSWORD", password);
            editor.commit();
            Toast.makeText(context, "Save id and password Preferences", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(context, "failed to save Preferences", Toast.LENGTH_SHORT).show();
        }
    }

}

