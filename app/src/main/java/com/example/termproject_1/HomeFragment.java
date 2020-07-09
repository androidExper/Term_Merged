package com.example.termproject_1;

<<<<<<< Updated upstream
import android.app.Dialog;
=======
import android.content.ContentValues;
>>>>>>> Stashed changes
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        //Log.d("time","time :"+currTime);
        TextView timetitle = rootView.findViewById(R.id.timeTitle);
        this.SetTimeTitle(currTime, timetitle);

        /*
        MyAsyncTask myAsyncTask = new MyAsyncTask(getActivity(),recyclerView, foodListAdapter, foodList);
        myAsyncTask.execute("닭갈비");
        myAsyncTask.getFoodListAdapter().setOnItemClickListener(new FoodListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("test", "onItemClick: Success");
                OnClickItem(position);
            }
        });
        */
        final ArrayList<Food> recommendList = new ArrayList<>();

        Cursor cursor = FM.select("meal", "2000.0");

        if(cursor != null){
            while(cursor.moveToNext()){
                Food foodData = new Food();

                foodData.setFoodname(cursor.getString(1));
                foodData.setKcal(cursor.getString(4));
                foodData.setNutr(new String[]{"10","11","12","13"});
                recommendList.add(foodData);
                Log.d("db", "in DB "+foodData.foodname);
            }
        }


        foodListAdapter.setItems(recommendList);
        foodListAdapter.setOnItemClickListener(new FoodListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("test", "onItemClick in Home using DB: Success");
                OnClickItem(position,recommendList);
            }
        });
        recyclerView.setAdapter(foodListAdapter);

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
