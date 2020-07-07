package com.example.termproject_1;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class FirebasePost {
    public String name;
    public String gender;
    public String age;
    public String height;
    public String weight;
    public String goal_weight;
    
    public FirebasePost(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public FirebasePost(String name, String gender, String age, String height, String weight, String goal_weight) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.goal_weight = goal_weight;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("goal_weight", goal_weight);
        result.put("weight", weight);
        result.put("height", height);
        result.put("age", age);
        result.put("gender", gender);
        result.put("name", name);

        return result;
    }
}