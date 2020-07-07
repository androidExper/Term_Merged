package com.example.termproject_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context myContext  = null;
    LayoutInflater myLayoutInflater = null;
    ArrayList<ListData> listData;

    public MyAdapter(Context context,ArrayList<ListData> data){
        myContext= context;
        listData = data;
        myLayoutInflater = LayoutInflater.from(myContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = myLayoutInflater.inflate(R.layout.listview_home,null);
        TextView foodName = (TextView)view.findViewById(R.id.nutname);
        TextView nut1 = view.findViewById(R.id.nut1);
        TextView nut2 = view.findViewById(R.id.nut2);
        TextView nut3 = view.findViewById(R.id.nut3);
        TextView nut4 = view.findViewById(R.id.nut4);

        foodName.setText(listData.get(position).getName());

        return view;
    }
}