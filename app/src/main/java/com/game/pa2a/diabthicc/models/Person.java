package com.game.pa2a.diabthicc.models;

import java.util.ArrayList;

/**
 * Person class, where the user's personal information are stored.
 */

public class Person {

    String name, firstName;

    // We assume 0 is female, 1 is male
    boolean sexe;

    double height, weight;
    int age;
    Profile profil;

    MealsDaily currentDiet;
    ArrayList<MealsDaily> pastDiets;

    public Person(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
    }

    public Person(String name, String firstName, String profil) {
        this.name = name;
        this.firstName = firstName;
        this.profil = new Profile(profil);
    }

    /**
     * Returns the sex of the user as a boolean.
     * @return 0 for female, 1 for male
     */
    public boolean getSexe() {
        return sexe;
    }

    /**
     * Changes the sex of the user.
     * @param sexe 0 female and 1 male
     */
    public void setSexe(boolean sexe) {
        this.sexe = sexe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Profile getProfil() {
        return profil;
    }

    public void setProfil(Profile profil) {
        this.profil = profil;
    }

    public MealsDaily getCurrentDiet() {
        return currentDiet;
    }

    public void setCurrentDiet(MealsDaily currentDiet) {
        this.currentDiet = currentDiet;
    }

    public ArrayList<MealsDaily> getPastDiets() {
        return pastDiets;
    }

    public void setPastDiets(ArrayList<MealsDaily> pastDiets) {
        this.pastDiets = pastDiets;
    }

    @Override
    public String toString(){
        return this.name;
    }
}