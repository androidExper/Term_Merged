package com.example.termproject_1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SearchFragment extends Fragment  {
    EditText edit;
    Button button;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FoodListAdapter foodListAdapter;
    static ArrayList<Food> foodList = new ArrayList<Food>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search,container,false);
        final MainActivity mainActivity = (MainActivity) getActivity();


        // edit text input
        edit = rootView.findViewById(R.id.searchEdit);


        // recyclerview 설정
        foodListAdapter = new FoodListAdapter();
        recyclerView = rootView.findViewById(R.id.searchRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //foodListAdapter = new FoodListAdapter();


        // Button Listner
        button = rootView.findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodList.clear();
                String foodname = edit.getText().toString();
                MyAsyncTask myAsyncTask = new MyAsyncTask(mainActivity,recyclerView,foodListAdapter,foodList);
                myAsyncTask.execute(foodname);
            }
        });


        foodListAdapter.setOnItemClickListener(new FoodListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("test", "onItemClick: Success");

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
        });



        recyclerView.setAdapter(foodListAdapter);

        return rootView;
    }

    /*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        foodListAdapter = new FoodListAdapter();
        foodListAdapter.setOnFoodItemClickListener(new FoodListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("test", "onItemClick: Success");
            }
        });
        recyclerView.setAdapter(foodListAdapter);
    }

     */


}