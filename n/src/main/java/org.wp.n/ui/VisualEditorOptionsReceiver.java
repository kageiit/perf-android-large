package org.wp.n.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.wp.n.R;
import org.wp.n.ui.prefs.AppPrefs;
import org.wp.n.util.AppLog;
import org.wp.n.util.AppLog.T;
import org.wp.n.util.ToastUtils;

public class VisualEditorOptionsReceiver extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getIntent().getAction();
        Uri uri = getIntent().getData();

        if (Intent.ACTION_VIEW.equals(action) && uri != null) {
            String available = uri.getQueryParameter("available");
            String enabled = uri.getQueryParameter("enabled");
            // Note: doesn't allow to deactivate visual editor
            if ("1".equals(available)) {
                AppLog.i(T.EDITOR, "Visual Editor is now Available");
                AppPrefs.setVisualEditorAvailable(true);
                ToastUtils.showToast(this, R.string.visual_editor_enabled);
            }

            if ("1".equals(enabled)) {
                AppLog.i(T.EDITOR, "Visual Editor Enabled");
                AppPrefs.setVisualEditorEnabled(true);
            } else if ("0".equals(enabled)) {
                AppLog.i(T.EDITOR, "Visual Editor Disabled");
                AppPrefs.setVisualEditorEnabled(false);
            }
        }

        Intent intent = new Intent(this, WPLaunchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
