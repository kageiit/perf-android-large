package org.wp.e.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
public class HelpshiftDeepLinkReceiver extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = getIntent().getAction();
        Uri data = getIntent().getData();

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            String faqid = data.getQueryParameter("faqid");
            String sectionid = data.getQueryParameter("sectionid");

        }
        finish();
    }
}
