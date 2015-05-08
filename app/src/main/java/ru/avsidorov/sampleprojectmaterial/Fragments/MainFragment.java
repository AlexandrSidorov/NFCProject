package ru.avsidorov.sampleprojectmaterial.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.avsidorov.sampleprojectmaterial.Models.CurrentUser;
import ru.avsidorov.sampleprojectmaterial.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends android.support.v4.app.Fragment {
    CurrentUser mCurrentUser;
    SharedPreferences mSharedPreferences;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mSharedPreferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        View inflate = inflater.inflate(R.layout.fragment_blank, container, false);
        mCurrentUser= CurrentUser.getInstance();
        mCurrentUser.loadFromSharedPreferences(getActivity());
        if (mCurrentUser.getToken() == null) {
            mCurrentUser.authorizationDialog(getActivity());
        }
        ;

        return inflate;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
