package org.wp.ei.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.wp.ei.R;
import org.wp.ei.WordPress;
import org.wp.ei.ui.main.WPMainActivity;
import org.wp.ei.util.ProfilingUtils;
import org.wp.ei.util.ToastUtils;

public class WPLaunchActivity extends AppCompatActivity {

    /*
     * this the main (default) activity, which does nothing more than launch the
     * previously active activity on startup - note that it's defined in the
     * manifest to have no UI
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProfilingUtils.split("WPLaunchActivity.onCreate");

        if (WordPress.wpDB == null) {
            ToastUtils.showToast(this, R.string.fatal_db_error, ToastUtils.Duration.LONG);
            finish();
            return;
        }

        Intent intent = new Intent(this, WPMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
