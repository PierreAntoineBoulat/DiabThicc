package com.game.pa2a.diabthicc.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DietTest {

    private Diet diet;

    @Before
    public void setup() {
        diet = new Diet(2, 3, 4);
    }

    @Test
    public void getProteinIntake() {
        assertEquals(2, diet.getProteinIntake());
        diet.setProteinIntake(10);
        assertEquals(10, diet.getProteinIntake());
    }

    @Test
    public void getFatIntake() {
        assertEquals(3, diet.getFatIntake());
        diet.setFatIntake(10);
        assertEquals(10, diet.getFatIntake());
    }

    @Test
    public void getCarbsIntake() {
        assertEquals(4, diet.getCarbsIntake());
        diet.setCarbsIntake(10);
        assertEquals(10, diet.getCarbsIntake());
    }

    @Test
    public void getCaloricIntake() {
        assertEquals((3 * 9 + (2+4) * 4), diet.getCaloricIntake());
    }

    @Test
    public void addMeal() {
        diet.addDiet(new Diet(10, 10, 10));
        assertEquals((10+3) * 9 + (2+4+20) * 4, diet.getCaloricIntake());
    }
}