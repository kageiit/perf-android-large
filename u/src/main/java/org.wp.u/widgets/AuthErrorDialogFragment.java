package org.wp.u.widgets;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import org.wp.u.R;
import org.wp.u.WordPress;
import org.wp.u.ui.ActivityLauncher;

/**
 * An alert dialog fragment for XML-RPC authentication failures
 */
public class AuthErrorDialogFragment extends DialogFragment {
    public static int DEFAULT_RESOURCE_ID = -1;

    private int mMessageId = R.string.incorrect_credentials;
    private int mTitleId = R.string.connection_error;

    public void setWPComTitleMessage(int titleResourceId, int messageResourceId) {
        if (titleResourceId != DEFAULT_RESOURCE_ID) {
            mTitleId = titleResourceId;
        } else {
            mTitleId = R.string.connection_error;
        }

        if (messageResourceId != DEFAULT_RESOURCE_ID) {
            mMessageId = messageResourceId;
        } else {
            mMessageId = R.string.incorrect_credentials;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(true);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        setStyle(style, theme);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setTitle(mTitleId);
        b.setMessage(mMessageId);
        b.setCancelable(true);
        b.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityLauncher.viewBlogSettingsForResult(getActivity(), WordPress.getCurrentBlog());
            }
        });
        b.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        return b.create();
    }
}