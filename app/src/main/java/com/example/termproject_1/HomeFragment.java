package com.example.termproject_1;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class HomeFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FoodListAdapter foodListAdapter;
    static ArrayList<Food> foodList = new ArrayList<Food>();

    long now;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        final Context context = container.getContext();
        //MainActivity mainActivity = (MainActivity) getActivity();

        final FoodDataBaseManager FM = ((MainActivity)getActivity()).getFoodDBManager();
        final ContentValues addRowValue = new ContentValues();

        // recyclerview 설정
        recyclerView = rootView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);       // foodListAdapter = new FoodListAdapter();

        foodListAdapter = new FoodListAdapter();

        Log.d("test", "onCreateView: " + foodList.size());
        // title 설정
        now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleData = new SimpleDateFormat("HH");
        String currTime = simpleData.format(mDate);
        TextView timetitle = rootView.findViewById(R.id.timeTitle);
        this.SetTimeTitle(currTime, timetitle);


        final ArrayList<Food> personalDBresult = new ArrayList<>();

        // select all
        Cursor cursor = FM.select("meal", "2000.0");
        Cursor cursor2 = FM.select("meal", "2000.0");

        if(cursor != null){
            while(cursor.moveToNext()){
                Food foodData = new Food();
                foodData.setFoodname(cursor.getString(1));
                foodData.setKcal(cursor.getString(4));
                foodData.setNutr(new String[]{cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)});
                personalDBresult.add(foodData);
            }
        }

        float k=0;
        float n1=0;
        float n2=0;
        float n3=0;
        float n4=0;

        String m1="2300";
        String m2="2000";
        String m3="300";
        String m4="60";
        String m5="40";

        String[] m = {m1,m2,m3,m4,m5};

        long now=System.currentTimeMillis();
        Date currDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd");
        String cDate= simpleDate.format(currDate);

        if(cursor2 != null){
            while(cursor.moveToNext()){
                if(cursor.getString(2).compareTo(cDate)==0){
                    k=k+Float.parseFloat(cursor.getString(4));      // today kcal
                    n1=n1+Float.parseFloat(cursor.getString(5));
                    n2=n2+Float.parseFloat(cursor.getString(6));
                    n3=n3+Float.parseFloat(cursor.getString(7));
                    n4=n4+Float.parseFloat(cursor.getString(8));
                }
            }
        }

        float dif1 = k/Float.parseFloat(m1);
        float dif2 = n1/Float.parseFloat(m2);
        float dif3= n2/Float.parseFloat(m3);
        float dif4 = n3/Float.parseFloat(m4);
        float dif5 = n4/Float.parseFloat(m5);

        float[] differ = {dif1,dif2,dif3,dif4,dif5};
        int max = 0;
        float result = differ[1];
        for(int i =0 ; i<5; ++i){
            if(result<differ[i] && result != differ[0]){
                max = i;
                result = differ[i];
            }
        }

        int index = 2;
        float less = Float.parseFloat(m[max])-differ[max]*Float.parseFloat(m[max]);
        if(less <= 0.0){
            less = (float)10.0;
        }

        if(max == 0){
            index = 1;
        }
        else if(max == 1){
            index = 2;
        }
        else if(max == 2){
            index = 6;
        }
        else if(max == 3){
            index = 3;
        }
        else{
            index = 4;
        }

        MyAsyncTask myAsyncTask = new MyAsyncTask(getActivity(),recyclerView, foodListAdapter, foodList);
        myAsyncTask.execute(" "+"/"+index+"/"+less);
        myAsyncTask.getFoodListAdapter().setOnItemClickListener(new FoodListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("test", "onItemClick: Success");
                OnClickItem(position,foodList);
            }
        });


        /*
        foodListAdapter.setItems(recommendList);
        foodListAdapter.setOnItemClickListener(new FoodListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("test", "onItemClick in Home using DB: Success");
                OnClickItem(position,recommendList);
            }
        });
        recyclerView.setAdapter(foodListAdapter);


         */
        return rootView;
    }


    public void OnClickItem(int position,ArrayList<Food> foodList){

        FoodListClickDialog dialogFragment = new FoodListClickDialog(foodList.get(position).getFoodname(),
                foodList.get(position).getKcal(),foodList.get(position).getNutr());

        dialogFragment.show(getFragmentManager(),"simiple");

        Log.d("test", "OnClickItem: data_0: "+foodList.get(position).getFoodname());
        Log.d("test", "OnClickItem: data_0: "+foodList.get(position).getKcal());
        Log.d("test", "OnClickItem: data_0: "+foodList.get(position).getNutr()[0]);
        Log.d("test", "OnClickItem: data_0: "+foodList.get(position).getNutr()[1]);
        Log.d("test", "OnClickItem: data_0: "+foodList.get(position).getNutr()[2]);
        Log.d("test", "OnClickItem: data_0: "+foodList.get(position).getNutr()[3]);
    }

    // 시간대별 텍스트 뷰 설정
    private void SetTimeTitle(String strtime, TextView view) {
        int time = Integer.parseInt(strtime);
        Log.d("time", "time :" + time);
        String title = null;
        if (4 <= time && time <= 10) {
            title = "아침 추천 식단";
        } else if (10 < time && time <= 17) {
            title = "점심 추천 식단";
        } else if (17 < time && time <= 23) {
            title = "저녁 추천 식단";
        }

        if (title != null) {
            view.setText(title);
        }
    }
}
