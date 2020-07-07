package com.example.termproject_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class FoodDataBaseManager {
    static final String DB_FOOD = "Food.db";
    static final String TABLE_FOOD = "Foods";
    static final int DB_VERSION = 1;

    Context myContext = null;

    private static FoodDataBaseManager foodDataBaseManager = null;
    private SQLiteDatabase db = null;

    public static FoodDataBaseManager getInstance(Context context){
        if(foodDataBaseManager == null){
            foodDataBaseManager = new FoodDataBaseManager(context);
        }
        return foodDataBaseManager;
    }

    private FoodDataBaseManager(Context context){
        this.myContext = context;

        // DB OPEN
        db = context.openOrCreateDatabase(DB_FOOD , context.MODE_PRIVATE, null);

        // TABLE CREATE
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FOOD +
                "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "foodname TEXT," +
                "date TEXT," +
                "meal TEXT," +
                "kcal TEXT," +
                "nutr1 TEXT," +
                "nutr2 TEXT," +
                "nutr3 TEXT," +
                "nutr4 TEXT);");
    }

    public  boolean checkConnection(){
        return true;
    }
}