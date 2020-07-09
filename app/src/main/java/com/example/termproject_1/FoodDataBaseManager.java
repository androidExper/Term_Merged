package com.example.termproject_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
                "kcal FLOAT," +
                "nutr1 FLOAT," +
                "nutr2 FLOAT," +
                "nutr3 FLOAT," +
                "nutr4 FLOAT);");
    }

    public long insert(ContentValues addRowValues){
        Log.d("test", "insert: success");
        return db.insert(TABLE_FOOD, null, addRowValues);
    }

    public Cursor select(String columname, String margin){
        //return db.rawQuery("SELECT * FROM "+TABLE_FOOD +" WHERE "+"kcal"+" > 2000",null);
        return db.rawQuery("SELECT * FROM "+TABLE_FOOD,null);
    }

    public void deleteAll(){
        db.execSQL("DELETE from "+TABLE_FOOD);
    }

    public  boolean checkConnection(){
        return true;
    }
}

    /*
    public Cursor query(String[] colums,
                        String selection,
                        String[] selectionArgs,
                        String groupBy,
                        String having,
                        String orderby)
    {
        return db.query(TABLE_FOOD,
                colums,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderby);
    }
     */
