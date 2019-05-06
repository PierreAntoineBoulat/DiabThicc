package com.game.pa2a.diabthicc.models;

public class Diet {

    private int proteinIntake = 0;
    private int fatIntake = 0;
    private int carbsIntake = 0;

    private int caloricValue = 0;

    public Diet(int prot, int fat, int carbs) {
        this.setProteinIntake(prot);
        this.setFatIntake(fat);
        this.setCarbsIntake(carbs);

        calculateCaloricValue();
    }

    Diet() {}

    void addDiet(Diet diet) {
        this.setCarbsIntake(this.carbsIntake + diet.getCarbsIntake());
        this.setProteinIntake(this.proteinIntake + diet.getProteinIntake());
        this.setFatIntake(this.fatIntake + diet.getFatIntake());
    }

    // To update caloricValue. Must be called each time a macro nutriment value is changed.
    private void calculateCaloricValue() {
        int FAT_CALORIC_MULTIPLIER = 9;
        int DEFAULT_CALORIC_MULTIPLIER = 4;

        this.caloricValue = (this.proteinIntake + this.carbsIntake) * DEFAULT_CALORIC_MULTIPLIER + this.fatIntake * FAT_CALORIC_MULTIPLIER;
    }

    public int getProteinIntake() {
        return proteinIntake;
    }

    void setProteinIntake(int prot) {
        if(prot > 0) {
            this.proteinIntake = prot;
            calculateCaloricValue();
        }
    }

    public int getFatIntake() {
        return fatIntake;
    }

    void setFatIntake(int fat) {
        if(fat > 0) {
            this.fatIntake = fat;
            calculateCaloricValue();
        }
    }

    public int getCarbsIntake() {
        return carbsIntake;
    }

    void setCarbsIntake(int carbs) {
        if(carbs > 0) {
            this.carbsIntake = carbs;
            calculateCaloricValue();
        }
    }

    public int getCaloricIntake() {
        return caloricValue;
    }
}
