package com.game.pa2a.diabthicc.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MealsDaily implements Serializable {

    List<Meal> meals;

    Diet dailyDiet;

    public MealsDaily() {
        dailyDiet = new Diet();
        meals = new ArrayList<>();
    }

    public void addMeal(Meal meal){
        if(meals.add(meal)) {
            dailyDiet.addDiet(meal.getDiet());
        }
    }

    public List<Meal> getMeals(){
        return meals;
    }
}
