package org.wordpress.android.ui.prefs;

import android.app.FragmentManager;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class AppSettingsActivity extends AppCompatActivity {
    private static final String KEY_APP_SETTINGS_FRAGMENT = "app-settings-fragment";
    private static final String KEY_PASSCODE_FRAGMENT = "passcode-fragment";

    private AppSettingsFragment mAppSettingsFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fragmentManager = getFragmentManager();
        mAppSettingsFragment = (AppSettingsFragment) fragmentManager.findFragmentByTag(KEY_APP_SETTINGS_FRAGMENT);
        if (mAppSettingsFragment == null ) {
            Bundle passcodeArgs = new Bundle();
            mAppSettingsFragment = new AppSettingsFragment();

            fragmentManager.beginTransaction()
                    .replace(android.R.id.content, null, KEY_PASSCODE_FRAGMENT)
                    .add(android.R.id.content, mAppSettingsFragment, KEY_APP_SETTINGS_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Preference togglePref =
                mAppSettingsFragment.findPreference("foo");
        Preference changePref =
                mAppSettingsFragment.findPreference("foo");

        if (togglePref != null && changePref != null) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
