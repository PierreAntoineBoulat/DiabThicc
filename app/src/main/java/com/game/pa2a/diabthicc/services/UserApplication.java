package com.game.pa2a.diabthicc.services;

import android.app.Application;

import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.models.Profile;

public class UserApplication extends Application {

    private static UserApplication singleton;
    private Person currentUser;

    public UserApplication getInstance(){
        return singleton;
    }

    public Person getUser(){
        return currentUser;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        Person mcBibi = new Person("McBiceps", "John", "Sportif Confirmé", "mc_biceps", "mc_biceps_round");
        Profile mcBibiProfile = new Profile("BibiSportif");
        mcBibiProfile.setMaxProt(500);
        mcBibiProfile.setMaxGlucides(200);
        mcBibiProfile.setMaxLipides(300);
        mcBibi.setProfil(mcBibiProfile);
        currentUser = mcBibi;
    }
}