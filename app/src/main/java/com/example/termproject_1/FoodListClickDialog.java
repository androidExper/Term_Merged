package com.example.termproject_1;

import android.app.AlertDialog;
import android.app.Dialog;
<<<<<<< Updated upstream
import android.app.FragmentManager;
=======
import android.content.ContentValues;
>>>>>>> Stashed changes
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

<<<<<<< Updated upstream
=======
import java.text.SimpleDateFormat;
>>>>>>> Stashed changes
import java.util.ArrayList;
import java.util.Date;


public class FoodListClickDialog extends DialogFragment {
    private  Fragment fragment;

    String foodname="";
    String kcal="0";
    String[] nutr=new String[4];

    String m1="2000";
    String m2="2200";
    String m3="1500";
    String m4="500";
    String m5="600";

    public FoodListClickDialog(String foodname,String kcal,String[] nutr){
        if(foodname != null)
            this.foodname = foodname;
        else
            this.foodname = "Nothing";
        if(kcal != null)
            this.kcal = kcal;
        else
            this.kcal = "0";
        if(nutr != null) {
            for (int i = 0; i < 4; ++i) {
                if (nutr[i] != null) {
                    this.nutr[i] = nutr[i];
                } else {
                    this.nutr[i] = "0";
                }
            }
        }
        else
            this.nutr = new String[4];
    }


    public Dialog onCreateDialog(Bundle saveInstanceState){
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_food_list_click_dialog, new LinearLayout(getActivity()), false);
        Button regButton = view.findViewById(R.id.registBtn);
        final FoodDataBaseManager FM = ((MainActivity)getActivity()).getFoodDBManager();
        final ContentValues addRowValue = new ContentValues();

        Log.d("testtest", "_--================-------------==============-------------======");

        // 그래프 설정
        RadarChart radarChart=(RadarChart)view.findViewById(R.id.chart3);

        ArrayList<Entry> NoOfEmp= new ArrayList();
        ArrayList<Entry> max= new ArrayList<>();

        ArrayList<String> labels=new ArrayList<String>();

        NoOfEmp.add(new BarEntry(Float.parseFloat(this.kcal), 0));
        NoOfEmp.add(new BarEntry(Float.parseFloat(this.nutr[0]), 1));
        NoOfEmp.add(new BarEntry(Float.parseFloat(this.nutr[1]), 2));
        NoOfEmp.add(new BarEntry(Float.parseFloat(this.nutr[2]), 3));
        NoOfEmp.add(new BarEntry(Float.parseFloat(this.nutr[3]), 4));
        max.add(new BarEntry(Float.parseFloat(m1), 0));
        max.add(new BarEntry(Float.parseFloat(m2), 1));
        max.add(new BarEntry(Float.parseFloat(m3), 2));
        max.add(new BarEntry(Float.parseFloat(m4), 3));
        max.add(new BarEntry(Float.parseFloat(m5), 4));

        labels.add("칼로리");
        labels.add("나트륨");
        labels.add("탄수화물");
        labels.add("단백질");
        labels.add("지방");

        RadarDataSet dataSet1=new RadarDataSet(NoOfEmp, "총량(g)");
        RadarDataSet dataSet2=new RadarDataSet(max, "권장량(g)");
        dataSet1.setColor(Color.BLUE);
        dataSet2.setColor(Color.RED);
        RadarData data=new RadarData(labels, dataSet1);
        data.addDataSet(dataSet2);
        XAxis xAxis1=radarChart.getXAxis();
        radarChart.setData(data);
        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleMonth = new SimpleDateFormat("MM");
                SimpleDateFormat simpleDate = new SimpleDateFormat("dd");
                SimpleDateFormat simpleTime = new SimpleDateFormat("HH");

                String cTime = simpleDate.format(mDate);
                String dTime = simpleDate.format(mDate);

                Integer currTime=Integer.parseInt(cTime);
                String meal = "null";
                if (4 <= (Integer) currTime && (Integer)currTime <= 10) {
                    meal = "아침";
                } else if (10 < currTime && currTime <= 17) {
                    meal = "점심";
                } else if (17 < currTime && currTime <= 23) {
                    meal = "저녁";
                }

                addRowValue.put("foodname",foodname);
                addRowValue.put("date",dTime);
                addRowValue.put("meal",meal);
                addRowValue.put("kcal",kcal);
                addRowValue.put("nutr1",nutr[0]);
                addRowValue.put("nutr2",nutr[1]);
                addRowValue.put("nutr3",nutr[2]);
                addRowValue.put("nutr4",nutr[3]);

                FM.insert(addRowValue);
                String[] colums = new String[] {"_id","foodname","date","meal","kcal",
                        "nutr1","nutr2","nutr3","nutr4",};
            }
        });

        return builder;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        //int width = getResources().getDimensionPixelSize(R.dimen.dialoge_width);
        super.onResume();
    }
}

