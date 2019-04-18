package com.game.pa2a.diabthicc.models;

public class DietResult {

    private double percentFat;
    private double percentCarbs;
    private double percentProt;

    private double percentCaloric;

    DietResult(Diet objective, Diet actual) {
        this.percentFat = ((double) actual.getFatIntake() / objective.getFatIntake())*100;
        this.percentCarbs = ((double) actual.getCarbsIntake() / objective.getCarbsIntake())*100;
        this.percentProt = ((double) actual.getProteinIntake() / objective.getProteinIntake())*100;

        this.percentCaloric = ((double) actual.getCaloricIntake() / objective.getCaloricIntake())*100;
    }

    public double getPercentFat() {
        return percentFat;
    }

    public double getPercentCarbs() {
        return percentCarbs;
    }

    public double getPercentProt() {
        return percentProt;
    }

    public double getPercentCaloric() {
        return percentCaloric;
    }
}
