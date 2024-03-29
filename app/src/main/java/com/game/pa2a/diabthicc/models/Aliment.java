package com.game.pa2a.diabthicc.models;

import java.io.Serializable;

/**
 * A meal is made of one or multiple ingredients, each having a name and caloric values.
 */

public class Aliment implements Serializable {

    private String name;

    private Diet diet;

    public Aliment(String name, Diet diet) {

        this.name = name;

        this.diet = diet;
    }

    public Diet getDiet() {
        return diet;
    }

    public String getName() {
        return name;
    }
}
