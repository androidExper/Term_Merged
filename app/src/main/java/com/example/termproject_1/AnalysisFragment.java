package com.example.termproject_1;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class AnalysisFragment extends Fragment {

    String m1="2000";
    String m2="2200";
    String m3="1500";
    String m4="500";
    String m5="600";

    String w1="14000";
    String w2="15400";
    String w3="10500";
    String w4="3500";
    String w5="4200";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FoodDataBaseManager FM=((MainActivity)getActivity()).getFoodDBManager();

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_analysis, container, false);
        TabHost tabHost=(TabHost) v.findViewById(R.id.host);
        tabHost.setup();

        TabHost.TabSpec tabSpecmonth= tabHost.newTabSpec("month").setIndicator("일별");
        tabSpecmonth.setContent(R.id.tab1);
        tabHost.addTab(tabSpecmonth);

        TabHost.TabSpec tabSpecweek= tabHost.newTabSpec("week").setIndicator("주별");
        tabSpecweek.setContent(R.id.tab2);
        tabHost.addTab(tabSpecweek);

        TabHost.TabSpec tabSpecday= tabHost.newTabSpec("day").setIndicator("월별");
        tabSpecday.setContent(R.id.tab3);
        tabHost.addTab(tabSpecday);

        tabHost.setCurrentTab(0);

        RadarChart radarChart=(RadarChart) v.findViewById(R.id.chart1);
        ArrayList<Entry> max= new ArrayList<>();
        ArrayList<Entry> max1= new ArrayList<>();
        max.add(new BarEntry(Float.parseFloat(m1), 0));
        max.add(new BarEntry(Float.parseFloat(m2), 1));
        max.add(new BarEntry(Float.parseFloat(m3), 2));
        max.add(new BarEntry(Float.parseFloat(m4), 3));
        max.add(new BarEntry(Float.parseFloat(m5), 4));
        max1.add(new BarEntry(Float.parseFloat(w1), 0));
        max1.add(new BarEntry(Float.parseFloat(w2), 1));
        max1.add(new BarEntry(Float.parseFloat(w3), 2));
        max1.add(new BarEntry(Float.parseFloat(w4), 3));
        max1.add(new BarEntry(Float.parseFloat(w5), 4));

        RadarDataSet Max= new RadarDataSet(max, "권장량");
        LineDataSet Max1=new LineDataSet(max, "권장량");
        ArrayList<String> labels=new ArrayList<String>();
        ArrayList<String> label1=new ArrayList<String>();
        ArrayList<String> label2=new ArrayList<String>();
        labels.add("칼로리");
        labels.add("나트륨");
        labels.add("탄수화물");
        labels.add("단백질");
        labels.add("지방");

        LineChart lineChart=(LineChart) v.findViewById(R.id.chart2);
        LineChart lineChart1=(LineChart) v.findViewById(R.id.chart3);

        XAxis xAxis=lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        XAxis xAxis1=lineChart1.getXAxis();
        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);



        long now=System.currentTimeMillis();
        Date mDate = new Date(now);
        /*SimpleDateFormat simpleMonth = new SimpleDateFormat("MM");*/
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd");
        SimpleDateFormat simpleTime = new SimpleDateFormat("HH");

        String cTime = simpleTime.format(mDate);
        String cDate= simpleDate.format(mDate);

        ArrayList<Entry> days=new ArrayList<>();
        ArrayList<Entry> day1=new ArrayList<>();
        ArrayList<Entry> mon=new ArrayList<>();
        /*ArrayList<String> day3=new ArrayList<String>();
        ArrayList<String> day4=new ArrayList<String>();
        ArrayList<String> day5=new ArrayList<String>();*/

        Cursor cursor = FM.select("kcal", "2000.0");
        Cursor cursor_w=FM.select("kcal","2000");
        Cursor cursor_m = FM.select("kcal", "2000.0");
        float k=0;
        float n1=0;
        float n2=0;
        float n3=0;
        float n4=0;
        float k1=0;
        float m1=0;
        float m2=0;
        float m3=0;
        float m4=0;
        float k2=0;
        float l1=0;
        float l2=0;
        float l3=0;
        float l4=0;

        if(cursor != null){
            while(cursor.moveToNext()){
                if(cursor.getString(2).compareTo(cDate)==0){
                    k=k+Float.parseFloat(cursor.getString(4));
                    n1=n1+Float.parseFloat(cursor.getString(5));
                    n2=n2+Float.parseFloat(cursor.getString(6));
                    n3=n3+Float.parseFloat(cursor.getString(7));
                    n4=n4+Float.parseFloat(cursor.getString(8));
                }
            }
        }

        days.add(new BarEntry(k,0));
        days.add(new BarEntry(n1,1));
        days.add(new BarEntry(n2,2));
        days.add(new BarEntry(n3,3));
        days.add(new BarEntry(n4,4));

        if(cursor_w !=null){
            while(cursor_w.moveToNext()){
                if((Float.parseFloat(cDate)-Float.parseFloat(cursor_w.getString(2)))<=30){
                    k2=k2+Float.parseFloat(cursor_w.getString(4));
                    l1=l1+Float.parseFloat(cursor_w.getString(5));
                    l2=l2+Float.parseFloat(cursor_w.getString(6));
                    l3=l3+Float.parseFloat(cursor_w.getString(7));
                    l4=l4+Float.parseFloat(cursor_w.getString(8));
                }
            }
        }

        day1.add(new BarEntry(k1,0));
        day1.add(new BarEntry(m1,1));
        day1.add(new BarEntry(m2,2));
        day1.add(new BarEntry(m3,3));
        day1.add(new BarEntry(m4,4));

        if(cursor_m !=null){
            while(cursor_m.moveToNext()){
                if((Float.parseFloat(cDate)-Float.parseFloat(cursor_m.getString(2)))<=7){
                    k1=k1+Float.parseFloat(cursor_m.getString(4));
                    m1=n1+Float.parseFloat(cursor_m.getString(5));
                    m2=n2+Float.parseFloat(cursor_m.getString(6));
                    m3=n3+Float.parseFloat(cursor_m.getString(7));
                    m4=n4+Float.parseFloat(cursor_m.getString(8));
                }
            }
        }
        mon.add(new BarEntry(k2,0));
        mon.add(new BarEntry(l1,1));
        mon.add(new BarEntry(l2,2));
        mon.add(new BarEntry(l3,3));
        mon.add(new BarEntry(l4,4));

        LineDataSet dataSet1=new LineDataSet(days, "총량");
        LineData chartdata=new LineData(labels, dataSet1);
        chartdata.addDataSet(Max1);
        lineChart.setData(chartdata);

        LineDataSet dataSet2=new LineDataSet(mon, "총량");
        LineData chartdata2=new LineData(labels, dataSet2);
        lineChart1.setData(chartdata2);

        RadarDataSet radarDataSet=new RadarDataSet(days,"총량");
        RadarData radarData=new RadarData(labels, radarDataSet);
        radarData.addDataSet(Max);
        radarChart.setData(radarData);
        return v;
    }
}
