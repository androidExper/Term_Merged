package com.example.termproject_1;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {

    // Person 객체를 담은 arraylist
    ArrayList<Food> items = new ArrayList<Food>();
    Activity activity;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    private OnItemClickListener mlistener ;

    public void setOnItemClickListener(OnItemClickListener listener){
        Log.d("test", "setOnItemClickListener(): " + listener);
        this.mlistener = listener;
    }

    // 뷰 홀더 생성시 호츨, XML 레이아웃을 통해 뷰 객체 생성
    // infaltion 을 위한 Context 객체가 필요하나, 파라미터인 ViewGroup 객체의 getContext() 를 통해
    // 참조 가능.

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView foodname;
        TextView nutr1,nutr2,nutr3,nutr4;
        TextView kcal;

        ViewHolder(View itemView){
            super(itemView);

            foodname = itemView.findViewById(R.id.food_name);
            kcal = itemView.findViewById(R.id.kcal);
            nutr1 = itemView.findViewById(R.id.nutr1);
            nutr2 = itemView.findViewById(R.id.nutr2);
            nutr3 = itemView.findViewById(R.id.nutr3);
            nutr4 = itemView.findViewById(R.id.nutr4);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(mlistener != null){
                            mlistener.onItemClick(position);
                            Log.d("test", "onClick: not null");
                        }
                        else{
                            Log.d("test", "onClick: in OnBindViewHolder : null mListener");
                            Log.d("test", "onClick: in OnBindViewHolder : poisition : "+position);
                        }
                    }
                    else{
                        Log.d("test", "onClick: in OnBindViewHolder : none Position");
                    }
                }
            });
        }

        public void setItem(Food item){
            foodname.setText(item.getFoodname());
            kcal.setText(item.getKcal());

            nutr1.setText(item.getNutr()[0]);
            nutr2.setText(item.getNutr()[1]);
            nutr3.setText(item.getNutr()[2]);
            nutr4.setText(item.getNutr()[3]);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // inflation 을 이용한 뷰 객체 생성
        View itemView = inflater.inflate(R.layout.food_item, parent, false);

        // 뷰 홀더 객체 생성 후 뷰 객체를 전달하고 해당 뷰 홀더 객체를 반환시킴.
        return new ViewHolder(itemView);
    }

    // 뷰 홀더는 재사용됨, 재사용시 호출되는 메소드
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // 인자로 전달받은 position 을 통해 array list 에서 해당 item 객체 꺼냄.
        final Food item = items.get(position);
        holder.setItem(item);

    }

    // 어댑터에서 관리하는 아이템의 개수 반환
    @Override
    public int getItemCount() {
        return items.size();
    }
    // 어댑터가 Food 객체를 list에 넣거나 가져가기 위한 메소드 재정의
    public void addItem(Food item){
        this.items.add(item);
    }
    public void setItems(ArrayList<Food> items){
        this.items = items;
    }
    public Food getItem(int position){
        return items.get(position);
    }
    public void setItem(int position,Food item){
        items.set(position,item);
    }
}
