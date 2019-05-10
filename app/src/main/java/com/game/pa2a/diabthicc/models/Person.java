package com.game.pa2a.diabthicc.models;

import android.util.Pair;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Person class, where the user's personal information are stored.
 */

public class Person implements Serializable {

    private String name, firstName;
    String image, icon;

    // We assume 0 is female, 1 is male
    private boolean sexe;

    private ArrayList<Pair<CustomDate, Double>> archivedWeights = new ArrayList<>(); // All new weights must send the old weight into the archive

    private CustomDate lastModified; // date of last modification of weight
    private double height, weight;
    private int age;
    private Profile profil;

    List<Pair<CustomDate, Diet>> archivedDiets = new ArrayList<>();

    private MealsDaily currentDiet;
    private ArrayList<MealsDaily> pastDiets;

    public Person(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
    }

    public Person(String name, String firstName, String profil) {
        this.name = name;
        this.firstName = firstName;
        this.profil = new Profile(profil);
    }

    public Person(String name, String firstName, String profil, String image, String icon) {
        this.name = name;
        this.firstName = firstName;
        this.image = image;
        this.icon = icon;
        this.profil = new Profile(profil);
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
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
        if(lastModified.isToday()) {
            this.weight = weight;
        } else {
            archivedWeights.add(new Pair<>(lastModified, this.weight));
            lastModified = new CustomDate();
            this.weight = weight;
        }
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

    public ArrayList<Pair<CustomDate, Double>> getArchivedWeights() {
        return archivedWeights;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
