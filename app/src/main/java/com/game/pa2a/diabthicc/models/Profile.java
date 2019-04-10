package com.game.pa2a.diabthicc.models;

public class Profile {


    private String name;

    // TODO : Can be replaced by a Diet Object (prots, fats, carbs)
    // Default needed informations
    int maxLipides, maxGlucides, maxProt, numProfil;

    // Diabetes need extra info
    int maxSugarLevel = 0; // 0 is the default for "not a diabetic"

    public Profile() {
    }

    public Profile(String name) {
        this.name=name;
    }

    public int getMaxLipides() {
        return maxLipides;
    }

    public void setMaxLipides(int maxLipides) {
        this.maxLipides = maxLipides;
    }

    public int getMaxGlucides() {
        return maxGlucides;
    }

    public void setMaxGlucides(int maxGlucides) {
        this.maxGlucides = maxGlucides;
    }

    public int getNumProfil() { return numProfil; }

    public int getMaxProt() {
        return maxProt;
    }

    public void setMaxProt(int maxProt) {
        this.maxProt = maxProt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
