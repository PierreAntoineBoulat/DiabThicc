package com.game.pa2a.diabthicc.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Person class, where the user's personal information are stored.
 */

public class Person implements Serializable {

    private String name, firstName;
    String image, icon;

    // We assume 0 is female, 1 is male
    private boolean sexe;

    private HashMap<CustomDate, Double> archivedWeights = new HashMap<>(); // All new weights must send the old weight into the archive

    private CustomDate lastModified; // date of last modification of weight
    private double height, weight;
    private int age;
    private Profile profil;

    HashMap<CustomDate, Diet> archivedDiets = new HashMap<>();

    private MealsDaily currentDiet;
    private ArrayList<MealsDaily> pastDiets;

    public Person(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
        currentDiet = new MealsDaily();
    }

    public Person(String name, String firstName, String profil) {
        this.name = name;
        this.firstName = firstName;
        this.profil = new Profile(profil);
        currentDiet = new MealsDaily();// Only for demo purposes
    }

    public Person(String name, String firstName, String profil, String image, String icon) {
        this.name = name;
        this.firstName = firstName;
        this.image = image;
        this.icon = icon;
        this.profil = new Profile(profil);
        currentDiet = new MealsDaily();

        // For demo purposes only
        CustomDate today = new CustomDate();
        long day = 24 * 60 * 60 * 1000;
        CustomDate yesterday = new CustomDate(today.getYear(), today.getMonth()-1, today.getDay() - 1, today.getHours(), today.getMinutes());
        archivedWeights.put(yesterday, 40.0);
        archivedWeights.put(CustomDate.build(today.getTime() - (day * 5)), 38.0);
        archivedWeights.put(CustomDate.build(today.getTime() - (day * 9)), 36.0);
        archivedWeights.put(CustomDate.build(today.getTime() - (day * 13)), 34.0);

        // For demo purposes only
        archivedDiets.put(yesterday, new Diet(500, 300, 200));
        archivedDiets.put(CustomDate.build(today.getTime() - (day * 5)), new Diet(300, 200, 150));
        archivedDiets.put(CustomDate.build(today.getTime() - (day * 9)), new Diet(600, 350, 250));
        archivedDiets.put(CustomDate.build(today.getTime() - (day * 13)), new Diet(400, 200, 300));
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
        if(lastModified == null) {
            lastModified = new CustomDate();
        }
        if(lastModified.isToday()) {
            this.weight = weight;
        } /*else {
            archivedWeights.put(lastModified, this.weight);
            lastModified = new CustomDate();
            this.weight = weight;
        }*/
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

    public HashMap<CustomDate, Double> getArchivedWeights() {
        return archivedWeights;
    }

    public void addPastDiets(ArrayList<MealsDaily> pastDiets) {
        this.pastDiets.addAll(pastDiets);
    }

    public CustomDate getLastModified() {
        return lastModified;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public HashMap<CustomDate, Diet> getArchivedDiets() {
        return archivedDiets;
    }
}
