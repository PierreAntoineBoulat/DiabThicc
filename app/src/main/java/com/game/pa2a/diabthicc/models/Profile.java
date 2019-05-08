package com.game.pa2a.diabthicc.models;

public class Profile {


    private String name;

    // TODO : Can be replaced by a Diet Object (prots, fats, carbs)
    // Default needed informations
    Diet objectif;
    // Diabetes need extra info
    int maxSugarLevel = 0; // 0 is the default for "not a diabetic"

    public Profile(String name) {
        this.name=name;
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

}
