package com.example.termproject_1;

import java.util.Random;

public class ListData {
    private String name;
    private double[] nutr;

    public ListData(String name,int nutrSize){
        this.name=name;
        this.nutr = new double[4];

        Random random = new Random();

        for(int i=0;i<nutrSize;++i) {
            nutr[i] = random.nextDouble()+(double)random.nextInt(1000);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}