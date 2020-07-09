package com.example.termproject_1;

public class Food {
    String foodname;
    String[] nutr;
    String kcal;

    public Food(String foodname, String[] nutr, String kcal) {
        this.foodname = foodname;
        this.nutr = nutr;
        this.kcal = kcal;
    }

    public String getFoodname() {
        return foodname;
    }
    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }
    public String[] getNutr() {
        return nutr;
    }
    public void setNutr(String[] nutr) {
        this.nutr = nutr;
    }
    public String getKcal() {
        return kcal;
    }
    public void setKcal(String kcal) {
        this.kcal = kcal;
    }
}
