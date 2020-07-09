package com.example.termproject_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    HomeFragment homeFragment;
    SearchFragment searchFragment;
    AnalysisFragment analysisFragment;
    AccountFragment accountFragment;

    ArrayList<Food> foodList = new ArrayList<Food>();
    // db connect
    FoodDataBaseManager foodDataBaseManager;

    // 정보 제공 여부 check9
    private boolean isLogin;
    //private SharedPreferences appData;

    /*

        1. 최초 앱 설치 후 가입하는 경우 (정보제공 안했음)
	        google login
	        getPersonalInformationActivity
	        MainMainActivity

        2. 기존 가입자이나, personal information 제공하지 않은 경우 (정보제공 안했음)
	        getPersonalInformationActivity
	        MainMainActivity

        3. 기존 가입자이고, personal information 제공한 경우 (정보제공 했음)
            MainMainActivity
         */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<Integer> Test = new ArrayList<Integer>(10);
        this.foodDataBaseManager =  FoodDataBaseManager.getInstance(this);

        //appData = getSharedPreferences("appData", MODE_PRIVATE);
        /*
        load_Login();         // 로그인 여부 check
        if(!isLogin) {
            Intent intent = new Intent(getApplication(), LoginActivity.class);
            startActivity(intent);
            save_Login();
        }
        */
        // 하단 탭 Fragment
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        analysisFragment = new AnalysisFragment();
        accountFragment = new AccountFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.tabhome:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                                return true;

                            case R.id.tabsearch:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,searchFragment).commit();
                                return true;

                            case R.id.tabanlaysis:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,analysisFragment).commit();
                                return true;

                            case R.id.tabaccount:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container,accountFragment).commit();
                                return true;
                        }
                        return false;
                    }
                }
        );
    }

    // ListItem Touch 되었을 경우 , 액티비티 전환
    public void ItemSelected(String itemName){
        Toast.makeText(this,itemName+" Clicked",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),ListItemInfo.class);
        intent.putExtra("itemName",itemName);
        startActivityForResult(intent,101);
    }

    public FoodDataBaseManager getFoodDBManager() {
        return this.foodDataBaseManager;
    }

    /*
    public void save_Login() {
        SharedPreferences.Editor editor = appData.edit();

        editor.putBoolean("IS_LOGIN", true);
        editor.commit();
    }
    public void load_Login() {
        isLogin = appData.getBoolean("IS_LOGIN", false);
    }

     */
}