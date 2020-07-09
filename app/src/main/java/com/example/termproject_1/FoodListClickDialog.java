package com.example.termproject_1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
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

import java.util.ArrayList;


public class FoodListClickDialog extends DialogFragment {
    private  Fragment fragment;

    String foodname;
    String kcal;
    String[] nutr;

    String m1="2000";
    String m2="2200";
    String m3="1500";
    String m4="500";
    String m5="600";

    public FoodListClickDialog(String foodname,String kcal,String[] nutr){
        this.foodname = foodname;
        this.kcal = kcal;
        this.nutr = nutr;
    }

    public Dialog onCreateDialog(Bundle saveInstanceState){
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_food_list_click_dialog, new LinearLayout(getActivity()), false);
        Button regButton = view.findViewById(R.id.registBtn);

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
                FoodDataBaseManager FM = ((MainActivity)getActivity()).getFoodDBManager();
                if(FM.checkConnection()){

                }
                else{
                    Toast.makeText(getContext(),"fail",Toast.LENGTH_SHORT).show();
                    dismiss();
                }
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