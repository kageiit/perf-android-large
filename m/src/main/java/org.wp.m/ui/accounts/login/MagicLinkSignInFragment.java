package org.wp.m.ui.accounts.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.wordpress.rest.RestRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.wp.m.R;
import org.wp.m.WordPress;
import org.wp.m.models.Account;
import org.wp.m.models.AccountHelper;
import org.wp.m.ui.accounts.SignInFragment;
import org.wp.m.ui.main.WPMainActivity;
import org.wp.m.util.AppLog;
import org.wp.m.util.EditTextUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MagicLinkSignInFragment extends SignInFragment {
    public static final String REASON_ERROR = "error";
    public static final String REASON_ERROR_TAKEN = "taken";
    public static final int MAX_EMAIL_LENGTH = 100;
    public static final String MAGIC_LINK_PROPERTY = "magic_link";

    public interface OnMagicLinkRequestInteraction {
        void onMagicLinkRequestSuccess(String email);
    }

    private OnMagicLinkRequestInteraction mListener;
    private String mToken = "";
    private boolean mShouldShowPassword;
    private boolean mSmartLockEnabled = true;

    public MagicLinkSignInFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMagicLinkRequestInteraction) {
            mListener = (OnMagicLinkRequestInteraction) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnMagicLinkRequestInteraction");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (savedInstanceState == null) {
            configureMagicLinkUI();
        }
        mUsernameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((didPressNextKey(actionId, event) || didPressEnterKey(actionId, event)) && !isEnterPasswordMode()) {
                    signIn();
                    return true;
                } else {
                    return false;
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!mToken.isEmpty()) {
            attemptLoginWithMagicLink();
            mSmartLockEnabled = false;
        } else {
            mSmartLockEnabled = true;
        }
        if (mShouldShowPassword) {
            showPasswordFieldAndFocus();
        }
    }

    public void setToken(String token) {
        mToken = token;
    }

    public void setShouldShowPassword(boolean shouldShowPassword) {
        mShouldShowPassword = shouldShowPassword;
    }

    @Override
    protected void toggleSignInMode(){
        if (mUrlButtonLayout.getVisibility() == View.VISIBLE) {
            configureMagicLinkUI();
            mSelfHosted = false;
        } else {
            showSelfHostedSignInForm();
            mSelfHosted = true;
        }
    }

    @Override
    protected void signIn() {
        if (mSelfHosted || isEnterPasswordMode()) {
            super.signIn();
        } else {
            if (isUsernameEmail()) {
                startProgress(getActivity().getString(R.string.checking_email));
                requestWPComEmailCheck();
            } else {
                showPasswordFieldAndFocus();
            }
        }
    }

    @Override
    protected void finishCurrentActivity(final List<Map<String, Object>> userBlogList) {
        if (!isAdded()) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (userBlogList != null) {
                    Intent intent = new Intent(getActivity(), WPMainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(MagicLinkSignInActivity.MAGIC_LOGIN, true);

                    getActivity().startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void showSelfHostedSignInForm() {
        super.showSelfHostedSignInForm();
        showPasswordField();
    }

    private boolean isEnterPasswordMode() {
        return mPasswordLayout.getVisibility() == View.VISIBLE;
    }

    private void configureMagicLinkUI() {
        showDotComSignInForm();
        mPasswordLayout.setVisibility(View.GONE);
        mForgotPassword.setVisibility(View.GONE);
        mSignInButton.setText(getString(R.string.button_next));
    }

    private void showPasswordFieldAndFocus() {
        if (isAdded()) {
            endProgress();
            showPasswordField();
            mPasswordEditText.requestFocus();
            mSignInButton.setText(getString(R.string.sign_in));
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mPasswordEditText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void showPasswordField() {
        if (isAdded()) {
            mPasswordLayout.setVisibility(View.VISIBLE);
            mForgotPassword.setVisibility(View.VISIBLE);
            if (!mSelfHosted) {
                mPasswordEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
            }
            mSignInButton.setText(R.string.sign_in);
        }
    }

    private void requestWPComEmailCheck() {
        WordPress.getRestClientUtilsV0().isAvailable(mUsername, new RestRequest.Listener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String errorReason = response.getString(REASON_ERROR);
                    if (errorReason != null && errorReason.equals(REASON_ERROR_TAKEN) && mListener != null) {
                        mListener.onMagicLinkRequestSuccess(mUsername);
                    } else {
                        showPasswordFieldAndFocus();
                    }
                } catch (JSONException error) {
                    AppLog.e(AppLog.T.MAIN, error);
                    showPasswordFieldAndFocus();
                }
            }
        }, new RestRequest.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showPasswordFieldAndFocus();
            }
        });
    }

    private boolean isUsernameEmail() {
        mUsername = EditTextUtils.getText(mUsernameEditText).trim();
        Pattern emailRegExPattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = emailRegExPattern.matcher(mUsername);

        return matcher.find() && mUsername.length() <= MAX_EMAIL_LENGTH;
    }

    public void attemptLoginWithMagicLink() {
        saveUsernameAndTokenToAccount();

        WordPress.getContext();

        configureAccountAfterSuccessfulSignIn();
    }

    private void saveUsernameAndTokenToAccount() {
        Account account = AccountHelper.getDefaultAccount();
        account.setAccessToken(mToken);
        account.setUserName(mUsername);
        account.save();
        account.fetchAccountDetails();
    }


    @Override
    public boolean canAutofillUsernameAndPassword() {
        return mSmartLockEnabled && EditTextUtils.getText(mUsernameEditText).isEmpty()
                && EditTextUtils.getText(mPasswordEditText).isEmpty();
    }
}
