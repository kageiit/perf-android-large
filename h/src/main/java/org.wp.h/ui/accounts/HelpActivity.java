package org.wp.h.ui.accounts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import org.wp.h.R;
import org.wp.h.WordPress;
import org.wp.h.ui.ActivityId;
import org.wp.h.ui.ActivityLauncher;
import org.wp.h.ui.AppLogViewerActivity;
import org.wp.h.util.HelpshiftHelper;
import org.wp.h.util.HelpshiftHelper.MetadataKey;
import org.wp.h.util.HelpshiftHelper.Tag;
import org.wp.h.widgets.WPTextView;

public class HelpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHelpshiftLayout();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0); //remove shadow
        }

        // Init common elements
        WPTextView version = (WPTextView) findViewById(R.id.nux_help_version);
        version.setText(getString(R.string.version) + " " + WordPress.versionName);

        WPTextView applogButton = (WPTextView) findViewById(R.id.applog_button);
        applogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), AppLogViewerActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityId.trackLastActivity(ActivityId.HELP_SCREEN);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initHelpshiftLayout() {
        setContentView(R.layout.help_activity_with_helpshift);

        WPTextView version = (WPTextView) findViewById(R.id.nux_help_version);
        version.setText(getString(R.string.version) + " " + WordPress.versionName);
        WPTextView contactUsButton = (WPTextView) findViewById(R.id.contact_us_button);
        contactUsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                Tag origin = Tag.ORIGIN_UNKNOWN;
                if (extras != null) {
                    // This could be moved to WelcomeFragmentSignIn directly, but better to have all Helpshift
                    // related code at the same place (Note: value can be null).
                    HelpshiftHelper.getInstance().addMetaData(MetadataKey.USER_ENTERED_URL, extras.getString(
                            SignInFragment.ENTERED_URL_KEY));
                    HelpshiftHelper.getInstance().addMetaData(MetadataKey.USER_ENTERED_USERNAME, extras.getString(
                            SignInFragment.ENTERED_USERNAME_KEY));
                    origin = (Tag) extras.get(HelpshiftHelper.ORIGIN_KEY);
                }
                HelpshiftHelper.getInstance().showConversation(HelpActivity.this, origin);
            }
        });

        WPTextView faqbutton = (WPTextView) findViewById(R.id.faq_button);
        faqbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                Tag origin = Tag.ORIGIN_UNKNOWN;
                if (extras != null) {
                    origin = (Tag) extras.get(HelpshiftHelper.ORIGIN_KEY);
                }
                HelpshiftHelper.getInstance().showFAQ(HelpActivity.this, origin);
            }
        });
    }
}
