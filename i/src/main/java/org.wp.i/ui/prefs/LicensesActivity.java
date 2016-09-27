package org.wp.i.ui.prefs;

import android.os.Bundle;

import org.wp.i.R;
import org.wp.i.ui.WebViewActivity;

/**
 * Display open source licenses for the application.
 */
public class LicensesActivity extends WebViewActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getText(R.string.open_source_licenses));
    }

    @Override
    protected void loadContent() {
        loadUrl("file:///android_asset/licenses.html");
    }
}
