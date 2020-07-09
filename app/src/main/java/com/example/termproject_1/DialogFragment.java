package com.example.termproject_1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFragment extends androidx.fragment.app.DialogFragment {
    private Fragment fragment;

    public DialogFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog,container,false);
        Bundle args = getArguments();
        String value = args.getString("key");

        fragment = getActivity().getSupportFragmentManager().findFragmentByTag("tag");

        if(fragment != null){
            DialogFragment dialogFragment = (DialogFragment)fragment;
            dialogFragment.dismiss();
        }

        return view;
    }
}

