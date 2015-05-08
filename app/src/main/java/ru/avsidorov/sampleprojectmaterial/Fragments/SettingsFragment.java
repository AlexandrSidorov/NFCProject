package ru.avsidorov.sampleprojectmaterial.Fragments;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.avsidorov.sampleprojectmaterial.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends android.support.v4.app.Fragment {
    Button mSignInBtn;
    EditText mLogin, mPassword;
    SharedPreferences mSharedPreferences;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        //Добавить проверку на залогиненного пользователя

        return view;
    }


}
