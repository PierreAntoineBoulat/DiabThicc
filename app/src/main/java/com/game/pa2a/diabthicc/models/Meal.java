package com.game.pa2a.diabthicc.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A meal is composed of one or multiple ingredients. It is to be consumed on a known date, the list of aliments can be updated and
 */
public class Meal {

    String name;
    String image, icon;

    String type;

    private List<Aliment> aliments;

    private Diet mealDiet;

    // TODO : Might be moved from here to MealsDaily, since the same meal could apply to multiple diets
    CustomDate consommationDate;

    public Meal(String name, CustomDate consommationDate) {
        this.name = name;
        this.aliments = new ArrayList<>();
        this.mealDiet = new Diet();
        this.consommationDate = consommationDate;
    }

    public Meal(String name, CustomDate consommationDate, String type) {
        this.name = name;
        this.type = type;
        this.aliments = new ArrayList<>();
        this.mealDiet = new Diet();
        this.consommationDate = consommationDate;
    }

    public Meal(String name, CustomDate consommationDate, String type, String image, String icon) {
        this.name = name;
        this.image = image;
        this.icon = icon;
        this.type = type;
        this.aliments = new ArrayList<>();
        this.mealDiet = new Diet();
        this.consommationDate = consommationDate;
    }

    public String getType() {
        return type;
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

    public void rename(String name) {
        this.name = name;
    }

    public void addAliment(Aliment aliment) {
        aliments.add(aliment);
        mealDiet.addDiet(aliment.getDiet());
    }

    public CustomDate getConsommationDate() {
        return consommationDate;
    }

    public void setConsommationDate(CustomDate consommationDate) {
        this.consommationDate = consommationDate;
    }

    public String getName() {
        return name;
    }

    public List<Aliment> getAliments() { return aliments;}

    public Diet getDiet() {
        return mealDiet;
    }

    @Override
    public String toString(){
        return this.name;
    }

    // TODO : Would be removed if the ComparatorDate is.
    public static class ModelMealComparatorOnDate implements Comparator<Meal> {

        @Override
        public int compare(Meal o1, Meal o2) {
            return o1.getConsommationDate().compareTo(o2.getConsommationDate());
        }
    }

    public static class ModelMealComparatorOnName implements Comparator<Meal> {

        @Override
        public int compare(Meal o1, Meal o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
