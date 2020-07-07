package com.example.termproject_1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AccountManagementActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRevoke, btnLogout;
    private FirebaseAuth mAuth ;
    private SharedPreferences appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_account);

        Log.d("test", "계정관리 액티비티 oncreate 진입 성공!!!");

        appData = getSharedPreferences("appData", MODE_PRIVATE);

        btnLogout = (Button)findViewById(R.id.btn_logout);
        btnRevoke = (Button)findViewById(R.id.btn_revoke);

        mAuth = FirebaseAuth.getInstance();

        btnLogout.setOnClickListener(this);
        btnRevoke.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        SharedPreferences.Editor editor = appData.edit();

        switch (v.getId()) {
            case R.id.btn_logout:
                signOut();
                editor.putBoolean("IS_LOGIN", false);
                Log.d("test", "로그아웃 직후  isSave값 : " + appData.getBoolean("IS_SAVE", false));
                Log.d("test", "로그아웃 직후  isLogin값 : " + appData.getBoolean("IS_LOGIN", false));
                editor.commit();
                finishAffinity();
                break;
            case R.id.btn_revoke:
                revokeAccess();
                editor.putBoolean("IS_LOGIN",false);
                editor.putBoolean("IS_SAVE", false);

                Log.d("test", "회원탈퇴 직후  isSave값 : " + appData.getBoolean("IS_SAVE", false));
                Log.d("test", "회원탈퇴 직후  isLogin값 : " + appData.getBoolean("IS_LOGIN", false));

                editor.commit();
                finishAffinity();
                break;
        }
    }

    private void signOut() {
        Log.d("test","signout 실행중");
        FirebaseAuth.getInstance().signOut();
    }

    private void revokeAccess() {
        mAuth.getCurrentUser().delete();
    }
}