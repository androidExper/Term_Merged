package com.example.termproject_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.util.Calendar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetPersonalInformationActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mPostReference;

    Button btnSave;

    EditText edit_name;
    RadioGroup check_gender;
    EditText edit_age;
    EditText edit_height;
    EditText edit_weight;
    EditText edit_goal_weight;

    String name;
    String gender;
    String age;
    String height;
    String weight;
    String goal_weight;

    ArrayAdapter<String> arrayAdapter;
    static ArrayList<String> arrayIndex = new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_personal_information);

        Log.d("test", "getInfo_oncreate");

        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        edit_name = (EditText) findViewById(R.id.edit_name);
        check_gender = (RadioGroup) findViewById(R.id.edit_gender);
        edit_age = (EditText) findViewById(R.id.edit_age);
        edit_height = (EditText) findViewById(R.id.edit_height);
        edit_weight = (EditText) findViewById(R.id.edit_weight);
        edit_goal_weight = (EditText) findViewById(R.id.edit_goalWeight);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        getFirebaseDatabase();
    }

    public void postFirebaseDatabase(boolean add) {
        mPostReference = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        Log.d("test","----------!!!!!post firebase 들어왔음!!!!!----------");

        if (add) {
            Log.d("test","----------!!!!!post firebase if(add) 진입 했음!!!!!----------");

            FirebasePost post = new FirebasePost(name, gender, age, height, weight, goal_weight);
            postValues = post.toMap();

            Log.d("test","----------!!!!!post firebase if(add) 나가기 직전!!!!!----------");
        }

        Log.d("test","----------!!!!!post firebase if(add) 나온 직후!!!!!----------");
        Log.d("test","----------!!!!!before child update!!!!!----------");
        childUpdates.put("/name_list/" + name, postValues);
        mPostReference.updateChildren(childUpdates);
        Log.d("test","----------!!!!!after child update!!!!!----------");
    }

    public void getFirebaseDatabase() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("getFirebaseDatabase", "key: " + dataSnapshot.getChildrenCount());
                arrayData.clear();
                arrayIndex.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    FirebasePost get = postSnapshot.getValue(FirebasePost.class);
                    String[] info = {get.name, get.gender, get.age, get.height, get.weight, get.goal_weight};
                    String Result = setTextLength(info[0], 10) + setTextLength(info[1], 10) + setTextLength(info[2], 10) + setTextLength(info[3], 10) + setTextLength(info[4], 10) + setTextLength(info[5], 10);
                    arrayData.add(Result);
                    arrayIndex.add(key);
                    Log.d("getFirebaseDatabase", "key: " + key);
                    Log.d("getFirebaseDatabase", "info: " + info[0] + info[1] + info[2] + info[3] + info[4] + info[5]);
                }
                arrayAdapter.clear();
                arrayAdapter.addAll(arrayData);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(":getFirebaseDatabase", "loadPost:onCancelled", databaseError.toException());
            }
        };
    }

    public String setTextLength(String text, int length) {
        if (text.length() < length) {
            int gap = length - text.length();
            for (int i = 0; i < gap; i++)
                text = text + " ";
        }

        return text;
    }

    @Override
    public void onClick(View v) {

        Log.d("test","----------!!!!!onclick firebase post!!!!!----------");

        Log.d("test","11111111111111111----------");
        name = edit_name.getText().toString();
        Log.d("test","222222222222---------");
        weight = edit_weight.getText().toString();
        Log.d("test","333333333333333---------");
        goal_weight = edit_goal_weight.getText().toString();
        Log.d("test","4444444444444---------");
        height = edit_height.getText().toString();
        Log.d("test","55555555555555---------");
        RadioButton checked_gender = (RadioButton) findViewById(check_gender.getCheckedRadioButtonId());
        Log.d("test","6666666666666---------");
        gender = checked_gender.getText().toString();
        Log.d("test","7777777777777777---------");
        Calendar cal = Calendar.getInstance();
        Log.d("test","888888888888888---------");

        Log.d("test","----------!!!!!onclick firebase post 객체들 생성 완료. postfirebase 진입 직전!!!!!----------");
        postFirebaseDatabase(true);
        //getFirebaseDatabase();
        Log.d("test","----------!!!!!onclick firebase post 객체들 생성 완료. postfirebase 나온 직후!!!!!----------");



        //Intent intent = new Intent(getApplication(), MainActivity.class);
        //startActivity(intent);

        finish();
    }
}