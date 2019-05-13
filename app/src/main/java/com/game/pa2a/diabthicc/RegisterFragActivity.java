package com.game.pa2a.diabthicc;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RegisterFragActivity extends Fragment {

    public RegisterFragActivity(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        return inflater.inflate(R.layout.register_frag_activity, container, false);
    }
}
