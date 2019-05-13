package com.game.pa2a.diabthicc.models;

import java.io.Serializable;

public class Profile implements Serializable {


    private String name;

    // TODO : Can be replaced by a Diet Object (prots, fats, carbs)
    // Default needed informations
    Diet objectif;
    // Diabetes need extra info
    int maxSugarLevel = 0; // 0 is the default for "not a diabetic"

    public Profile(String name) {
        this.name=name;
        this.objectif = new Diet();
    }

    public Diet getObjectif() {
        return objectif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxProt(int prot) {
        objectif.setProteinIntake(prot);
    }

    public void setMaxLipides(int lipides) {
        objectif.setFatIntake(lipides);
    }

    public void setMaxGlucides(int carbs) {
        objectif.setCarbsIntake(carbs);
    }

    public int getMaxProt() {
        return objectif.getProteinIntake();
    }

    public int getMaxLipides() {
        return objectif.getFatIntake();
    }

    public int getMaxGlucides() {
        return objectif.getCarbsIntake();
    }
}
