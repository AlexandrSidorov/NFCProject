package ru.avsidorov.sampleprojectmaterial;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.flurry.android.FlurryAgent;

import ru.avsidorov.sampleprojectmaterial.Fragments.MainFragment;
import ru.avsidorov.sampleprojectmaterial.Fragments.SettingsFragment;
import ru.avsidorov.sampleprojectmaterial.Models.CurrentUser;


public class MainActivity extends BaseActivity {
    private static final String BACKSTACK = "backstack";
    private MainFragment mMainFragment;
    private SettingsFragment mSettingsFragment;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlurryAgent.init(this, "9ZYY7HBQ4ZGRVRCZQ5H9");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSharedPreferences = getSharedPreferences("settings",MODE_PRIVATE);
        if (savedInstanceState == null) {
            if (mMainFragment == null){
                mMainFragment = new MainFragment();
            }
            startMainFragment();
        }

    }

    private void startMainFragment() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top,R.anim.abc_slide_out_top,R.anim.abc_slide_in_bottom)
                .add(R.id.fragment_container, mMainFragment)
                .addToBackStack(BACKSTACK)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id){
            case R.id.action_settings:
                startSettings();
                return true;

            case R.id.action_clear:
                CurrentUser.getInstance().clear(mSharedPreferences);
                CurrentUser.getInstance().authorizationDialog(this);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void startSettings() {
        if (mSettingsFragment == null){
            mSettingsFragment = new SettingsFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top,R.anim.abc_slide_in_top,R.anim.abc_slide_out_bottom)
                .replace(R.id.fragment_container, mSettingsFragment)
                .addToBackStack(BACKSTACK)

                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else super.onBackPressed();
    }
}
