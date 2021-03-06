package com.example.termproject_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    public FirebaseAuth mAuth = null;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton signInButton;

    private boolean isSave;
    private boolean isLogin;
    private SharedPreferences appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInButton = findViewById(R.id.signInButton);

        mAuth = FirebaseAuth.getInstance();
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load_Save();
        if (mAuth.getCurrentUser() != null) { // 기존 가입자인 경우
            if(isSave) {        // 가입도 했고, 정보 o

                Log.d("testsetst", "가입도 했고 정보도 제공한 경우 수행!!!");

                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                //finish();
            } else {            // 가입은 했지만, 정보 x
                Log.d("test", "가입은 했지만, 정보 x 인 경우; GetPersonalInformationActivity로 intent 전환 직전");
                Intent intent = new Intent(getApplication(), GetPersonalInformationActivity.class);
                startActivity(intent);
                save_Save();
                Log.d("test", "가입은 했지만, 정보 x 인 경우; 정보 제공하고 난 직후");

                finish();
            }
        }

        // 1. 최초 앱 설치 후 가입하는 경우 (정보제공 안했음)
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1027197876060-9tgbbutpv28bhv2vft3ru16o9j0i4ss6.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

                // 최초 가입자이므로 getPersonalInformationActivity 로 전환
                Intent intent = new Intent(getApplication(), GetPersonalInformationActivity.class);
                startActivity(intent);
            }
            catch (ApiException e) {}
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(findViewById(R.id.layout_main), "Authentication Successed.", Snackbar.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Snackbar.make(findViewById(R.id.layout_main), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) { //update ui code here
        if(user != null) {
            Intent intent = new Intent(getApplication(), MainActivity.class);

            save_Save();
            save_Login();

            startActivity(intent);
            finish();
        }
    }

    public void save_Login() {
        SharedPreferences.Editor editor = appData.edit();

        editor.putBoolean("IS_LOGIN", true);
        editor.commit();
    }
    public void load_Login() {
        isLogin = appData.getBoolean("IS_LOGIN", false);
    }
    public void save_Save() {
        SharedPreferences.Editor editor = appData.edit();

        editor.putBoolean("IS_SAVE", true);
        editor.commit();
    }
    public void load_Save() {
        isSave = appData.getBoolean("IS_SAVE", false);
    }
}