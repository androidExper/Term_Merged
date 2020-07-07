package com.example.termproject_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {
    Button btnLogout, btnRevoke;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_account,container,false);
        mAuth = FirebaseAuth.getInstance();

        btnLogout = rootView.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

                Intent intent = new Intent(getActivity(), AccountManagementActivity.class);
                startActivity(intent);
            }
        });

        btnRevoke = rootView.findViewById(R.id.btn_revoke);
        btnRevoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revokeAccess();

                Intent intent = new Intent(getActivity(), AccountManagementActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
    private void revokeAccess() {
        mAuth.getCurrentUser().delete();
    }
}