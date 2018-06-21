package com.example.yaakovblumer.takego_clientside.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yaakovblumer.takego_clientside.R;
import com.example.yaakovblumer.takego_clientside.controller.about;

public class take_and_go extends AppCompatActivity {

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;
    about odot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_and_go);

        fragmentTransaction = getFragmentManager().beginTransaction();


        Branches_sec branches_sec=Branches_sec.newInstance("A","B");

        odot=new about();

        fragmentTransaction.add(R.id.fragment_container, new Fragment());
        fragmentTransaction.commit();
    }



}
