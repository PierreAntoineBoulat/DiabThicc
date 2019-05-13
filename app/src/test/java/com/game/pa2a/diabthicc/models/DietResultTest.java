package com.game.pa2a.diabthicc.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DietResultTest {

    private DietResult dietResult;

    @Before
    public void setup() {
        dietResult = new DietResult(new Diet(10, 10, 10), new Diet(5, 5, 5));
    }

    @Test
    public void getPercentFat() {
        assertEquals(50.0, dietResult.getPercentFat(), 0.1);
    }

    @Test
    public void getPercentCarbs() {
        assertEquals(50.0, dietResult.getPercentCarbs(), 0.1);
    }

    @Test
    public void getPercentProt() {
        assertEquals(50.0, dietResult.getPercentProt(), 0.1);
    }

    @Test
    public void getPercentCaloric() {
        assertEquals(50.0, dietResult.getPercentCaloric(), 0.1);
    }
}