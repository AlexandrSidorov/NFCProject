package ru.avsidorov.sampleprojectmaterial;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.octo.android.robospice.SpiceManager;

import ru.avsidorov.sampleprojectmaterial.Service.Service;


public class BaseActivity extends ActionBarActivity {
    private SpiceManager spiceManager = new SpiceManager(Service.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    public SpiceManager getSpiceManager() {
        
        return spiceManager;
    }
}
