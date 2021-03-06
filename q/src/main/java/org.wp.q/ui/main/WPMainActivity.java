package org.wp.q.ui.main;

import android.animation.ObjectAnimator;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.wp.q.GCMMessageService;
import org.wp.q.GCMRegistrationIntentService;
import org.wp.q.R;
import org.wp.q.WordPress;
import org.wp.q.models.AccountHelper;
import org.wp.q.models.Blog;
import org.wp.q.ui.ActivityId;
import org.wp.q.ui.ActivityLauncher;
import org.wp.q.ui.RequestCodes;
import org.wp.q.ui.accounts.login.MagicLinkSignInActivity;
import org.wp.q.ui.notifications.NotificationEvents;
import org.wp.q.ui.notifications.NotificationsListFragment;
import org.wp.q.ui.notifications.utils.NotificationsUtils;
import org.wp.q.ui.posts.PromoDialog;
import org.wp.q.ui.prefs.AppPrefs;
import org.wp.q.ui.prefs.AppSettingsFragment;
import org.wp.q.ui.prefs.SiteSettingsFragment;
import org.wp.q.ui.reader.ReaderPostListFragment;
import org.wp.q.util.AniUtils;
import org.wp.q.util.AppLog;
import org.wp.q.util.AppLog.T;
import org.wp.q.util.AuthenticationDialogUtils;
import org.wp.q.util.CoreEvents;
import org.wp.q.util.CoreEvents.MainViewPagerScrolled;
import org.wp.q.util.CoreEvents.UserSignedOutCompletely;
import org.wp.q.util.CoreEvents.UserSignedOutWordPressCom;
import org.wp.q.util.NetworkUtils;
import org.wp.q.util.ProfilingUtils;
import org.wp.q.util.ToastUtils;
import org.wp.q.widgets.WPViewPager;

import de.greenrobot.event.EventBus;

/**
 * Main activity which hosts sites, reader, me and notifications tabs
 */
public class WPMainActivity extends AppCompatActivity {

    private WPViewPager mViewPager;
    private WPMainTabLayout mTabLayout;
    private WPMainTabAdapter mTabAdapter;
    private TextView mConnectionBar;
    private int  mAppBarElevation;

    public static final String ARG_OPENED_FROM_PUSH = "opened_from_push";

    /*
     * tab fragments implement this if their contents can be scrolled, called when user
     * requests to scroll to the top
     */
    public interface OnScrollToTopListener {
        void onScrollToTop();
    }

    /*
     * tab fragments implement this and return true if the fragment handles the back button
     * and doesn't want the activity to handle it as well
     */
    public interface OnActivityBackPressedListener {
        boolean onActivityBackPressed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ProfilingUtils.split("WPMainActivity.onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mViewPager = (WPViewPager) findViewById(R.id.viewpager_main);
        mViewPager.setOffscreenPageLimit(WPMainTabAdapter.NUM_TABS - 1);

        mTabAdapter = new WPMainTabAdapter(getFragmentManager());
        mViewPager.setAdapter(null);

        mConnectionBar = (TextView) findViewById(R.id.connection_bar);
        mConnectionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // slide out the bar on click, then re-check connection after a brief delay
                AniUtils.animateBottomBar(mConnectionBar, false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinishing()) {
                            checkConnection();
                        }
                    }
                }, 2000);
            }
        });
        mTabLayout = (WPMainTabLayout) findViewById(R.id.tab_layout);
        mTabLayout.createTabs();

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //  nop
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //scroll the active fragment's contents to the top when user taps the current tab
                Fragment fragment = mTabAdapter.getFragment(tab.getPosition());
                if (fragment instanceof OnScrollToTopListener) {
                    ((OnScrollToTopListener) fragment).onScrollToTop();
                }
            }
        });

        mAppBarElevation = getResources().getDimensionPixelSize(R.dimen.appbar_elevation);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                AppPrefs.setMainTabIndex(position);

                switch (position) {
                    case WPMainTabAdapter.TAB_MY_SITE:
                        setTabLayoutElevation(mAppBarElevation);
                        break;
                    case WPMainTabAdapter.TAB_READER:
                        setTabLayoutElevation(0);
                    break;
                    case WPMainTabAdapter.TAB_ME:
                        setTabLayoutElevation(mAppBarElevation);
                    break;
                    case WPMainTabAdapter.TAB_NOTIFS:
                        setTabLayoutElevation(mAppBarElevation);
                        new UpdateLastSeenTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        break;
                }

                trackLastVisibleTab(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // noop
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // fire event if the "My Site" page is being scrolled so the fragment can
                // animate its fab to match
                if (position == WPMainTabAdapter.TAB_MY_SITE) {
                    EventBus.getDefault().post(new MainViewPagerScrolled(positionOffset));
                }
            }
        });

        if (savedInstanceState == null) {
            if (AccountHelper.isSignedIn()) {
                // open note detail if activity called from a push, otherwise return to the tab
                // that was showing last time
                boolean openedFromPush = (getIntent() != null && getIntent().getBooleanExtra(ARG_OPENED_FROM_PUSH,
                        false));
                if (openedFromPush) {
                    getIntent().putExtra(ARG_OPENED_FROM_PUSH, false);
                    launchWithNoteId();
                } else {
                    int position = AppPrefs.getMainTabIndex();
                    if (mTabAdapter.isValidPosition(position) && position != mViewPager.getCurrentItem()) {
                        mViewPager.setCurrentItem(position);
                    }
                    checkMagicLinkSignIn();
                }
            } else {
                ActivityLauncher.showSignInForResult(this);
            }
        }
    }

    private void setTabLayoutElevation(float newElevation){
        if (mTabLayout == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float oldElevation = mTabLayout.getElevation();
            if (oldElevation != newElevation) {
                ObjectAnimator.ofFloat(mTabLayout, "elevation", oldElevation, newElevation)
                        .setDuration(1000L)
                        .start();
            }
        }
    }

    private void showVisualEditorPromoDialogIfNeeded() {
        if (AppPrefs.isVisualEditorPromoRequired() && AppPrefs.isVisualEditorEnabled()) {
            DialogFragment newFragment = PromoDialog.newInstance(R.drawable.new_editor_promo_header,
                    R.string.new_editor_promo_title, R.string.new_editor_promo_desc,
                    R.string.new_editor_promo_button_label);
            newFragment.show(getFragmentManager(), "visual-editor-promo");
            AppPrefs.setVisualEditorPromoRequired(false);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        AppLog.i(T.MAIN, "main activity > new intent");
        if (intent.hasExtra(NotificationsListFragment.NOTE_ID_EXTRA)) {
            launchWithNoteId();
        }
    }

    /*
     * called when app is launched from a push notification, switches to the notification tab
     * and opens the desired note detail
     */
    private void launchWithNoteId() {
        if (isFinishing() || getIntent() == null) return;

        // Check for push authorization request
        if (getIntent().hasExtra(NotificationsUtils.ARG_PUSH_AUTH_TOKEN)) {
            Bundle extras = getIntent().getExtras();
            String token = extras.getString(NotificationsUtils.ARG_PUSH_AUTH_TOKEN, "");
            String title = extras.getString(NotificationsUtils.ARG_PUSH_AUTH_TITLE, "");
            String message = extras.getString(NotificationsUtils.ARG_PUSH_AUTH_MESSAGE, "");
            long expires = extras.getLong(NotificationsUtils.ARG_PUSH_AUTH_EXPIRES, 0);

            long now = System.currentTimeMillis() / 1000;
            if (expires > 0 && now > expires) {
                // Show a toast if the user took too long to open the notification
                ToastUtils.showToast(this, R.string.push_auth_expired, ToastUtils.Duration.LONG);
//
            } else {
                NotificationsUtils.showPushAuthAlert(this, token, title, message);
            }
        }

        mViewPager.setCurrentItem(WPMainTabAdapter.TAB_NOTIFS);

        boolean shouldShowKeyboard = getIntent().getBooleanExtra(NotificationsListFragment.NOTE_INSTANT_REPLY_EXTRA, false);
        if (GCMMessageService.getNotificationsCount() == 1) {
            String noteId = getIntent().getStringExtra(NotificationsListFragment.NOTE_ID_EXTRA);
            if (!TextUtils.isEmpty(noteId)) {
                GCMMessageService.bumpPushNotificationsTappedAnalytics(noteId);
                NotificationsListFragment.openNote(this, noteId, shouldShowKeyboard);
            }
        } else {
          // mark all tapped here
            GCMMessageService.bumpPushNotificationsTappedAllAnalytics();
        }

        GCMMessageService.clearNotifications();
    }

    @Override
    protected void onPause() {


        super.onPause();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Start listening to Simperium Note bucket

        mTabLayout.checkNoteBadge();

        // We need to track the current item on the screen when this activity is resumed.
        // Ex: Notifications -> notifications detail -> back to notifications
        trackLastVisibleTab(mViewPager.getCurrentItem(), false);

        checkConnection();

        ProfilingUtils.split("WPMainActivity.onResume");
        ProfilingUtils.dump();
        ProfilingUtils.stop();
    }

    @Override
    public void onBackPressed() {
        // let the fragment handle the back button if it implements our OnParentBackPressedListener
        Fragment fragment = getActiveFragment();
        if (fragment instanceof OnActivityBackPressedListener) {
            boolean handled = ((OnActivityBackPressedListener) fragment).onActivityBackPressed();
            if (handled) {
                return;
            }
        }
        super.onBackPressed();
    }

    private Fragment getActiveFragment() {
        return mTabAdapter.getFragment(mViewPager.getCurrentItem());
    }

    private void checkMagicLinkSignIn() {
        if (getIntent() !=  null) {
            if (getIntent().getBooleanExtra(MagicLinkSignInActivity.MAGIC_LOGIN, false)) {
//
                startWithNewAccount();
            }
        }
    }

    private void trackLastVisibleTab(int position, boolean trackAnalytics) {
        if (position ==  WPMainTabAdapter.TAB_MY_SITE) {
            showVisualEditorPromoDialogIfNeeded();
        }
        switch (position) {
            case WPMainTabAdapter.TAB_MY_SITE:
                ActivityId.trackLastActivity(ActivityId.MY_SITE);
                if (trackAnalytics) {
//
                }
                break;
            case WPMainTabAdapter.TAB_READER:
                ActivityId.trackLastActivity(ActivityId.READER);
                if (trackAnalytics) {
//
                }
                break;
            case WPMainTabAdapter.TAB_ME:
                ActivityId.trackLastActivity(ActivityId.ME);
                if (trackAnalytics) {
//
                }
                break;
            case WPMainTabAdapter.TAB_NOTIFS:
                ActivityId.trackLastActivity(ActivityId.NOTIFICATIONS);
                if (trackAnalytics) {
//
                }
                break;
            default:
                break;
        }
    }

    public void setReaderTabActive() {
        if (isFinishing() || mTabLayout == null) return;

        mTabLayout.setSelectedTabPosition(WPMainTabAdapter.TAB_READER);
    }

    /*
     * re-create the fragment adapter so all its fragments are also re-created - used when
     * user signs in/out so the fragments reflect the active account
     */
    private void resetFragments() {
        AppLog.i(AppLog.T.MAIN, "main activity > reset fragments");

        // reset the timestamp that determines when followed tags/blogs are updated so they're
        // updated when the fragment is recreated (necessary after signin/disconnect)
        ReaderPostListFragment.resetLastUpdateDate();

        // remember the current tab position, then recreate the adapter so new fragments are created
        int position = mViewPager.getCurrentItem();
        mTabAdapter = new WPMainTabAdapter(getFragmentManager());
//        mViewPager.setAdapter(mTabAdapter);

        // restore previous position
        if (mTabAdapter.isValidPosition(position)) {
            mViewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RequestCodes.EDIT_POST:
            case RequestCodes.CREATE_BLOG:
                if (resultCode == RESULT_OK) {
                    MySiteFragment mySiteFragment = getMySiteFragment();
                    if (mySiteFragment != null) {
                        mySiteFragment.onActivityResult(requestCode, resultCode, data);
                    }
                }
                break;
            case RequestCodes.ADD_ACCOUNT:
                if (resultCode == RESULT_OK) {
                    // Register for Cloud messaging
                    startWithNewAccount();
                } else if (!AccountHelper.isSignedIn()) {
                    // can't do anything if user isn't signed in (either to wp.com or self-hosted)
                    finish();
                }
                break;
            case RequestCodes.REAUTHENTICATE:
                if (resultCode == RESULT_CANCELED) {
                    ActivityLauncher.showSignInForResult(this);
                } else {
                    // Register for Cloud messaging
                    startService(new Intent(this, GCMRegistrationIntentService.class));
                }
                break;
            case RequestCodes.NOTE_DETAIL:
                if (resultCode == RESULT_OK && data != null) {
//                    moderateCommentOnActivityResult(data);
                }
                break;
            case RequestCodes.SITE_PICKER:
                if (getMySiteFragment() != null) {
                    getMySiteFragment().onActivityResult(requestCode, resultCode, data);
                }
                break;
            case RequestCodes.BLOG_SETTINGS:
                if (resultCode == SiteSettingsFragment.RESULT_BLOG_REMOVED) {
                    handleBlogRemoved();
                }
                break;
            case RequestCodes.APP_SETTINGS:
                if (resultCode == AppSettingsFragment.LANGUAGE_CHANGED) {
                    resetFragments();
                }
                break;
        }
    }

    private void startWithNewAccount() {
        startService(new Intent(this, GCMRegistrationIntentService.class));
        resetFragments();
    }

    /*
     * returns the my site fragment from the sites tab
     */
    private MySiteFragment getMySiteFragment() {
        Fragment fragment = mTabAdapter.getFragment(WPMainTabAdapter.TAB_MY_SITE);
        if (fragment instanceof MySiteFragment) {
            return (MySiteFragment) fragment;
        }
        return null;
    }

    // Updates `last_seen` notifications flag in Simperium and removes tab indicator
    private class UpdateLastSeenTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            return true;
        }

        @Override
        protected void onPostExecute(Boolean lastSeenTimeUpdated) {
            if (isFinishing()) return;

            if (lastSeenTimeUpdated) {
                mTabLayout.showNoteBadge(false);
            }
        }
    }

    // Events

    @SuppressWarnings("unused")
    public void onEventMainThread(UserSignedOutWordPressCom event) {
        resetFragments();
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(UserSignedOutCompletely event) {
        ActivityLauncher.showSignInForResult(this);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(CoreEvents.InvalidCredentialsDetected event) {
        AuthenticationDialogUtils.showAuthErrorView(this);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(CoreEvents.RestApiUnauthorized event) {
        AuthenticationDialogUtils.showAuthErrorView(this);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(CoreEvents.TwoFactorAuthenticationDetected event) {
        AuthenticationDialogUtils.showAuthErrorView(this);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(CoreEvents.InvalidSslCertificateDetected event) {
//        SelfSignedSSLCertsManager.askForSslTrust(this, null);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(CoreEvents.LoginLimitDetected event) {
        ToastUtils.showToast(this, R.string.limit_reached, ToastUtils.Duration.LONG);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(NotificationEvents.NotificationsChanged event) {
        mTabLayout.checkNoteBadge();
    }

    @SuppressWarnings("unused")
//    public void onEventMainThread(ConnectionChangeReceiver.ConnectionChangeEvent event) {
//        updateConnectionBar(event.isConnected());
//    }

    private void checkConnection() {
        updateConnectionBar(NetworkUtils.isNetworkAvailable(this));
    }

    private void updateConnectionBar(boolean isConnected) {
        if (isConnected && mConnectionBar.getVisibility() == View.VISIBLE) {
            AniUtils.animateBottomBar(mConnectionBar, false);
        } else if (!isConnected && mConnectionBar.getVisibility() != View.VISIBLE) {
            AniUtils.animateBottomBar(mConnectionBar, true);
        }
    }

    private void handleBlogRemoved() {
        if (!AccountHelper.isSignedIn()) {
            ActivityLauncher.showSignInForResult(this);
        } else {
            Blog blog = WordPress.getCurrentBlog();
            MySiteFragment mySiteFragment = getMySiteFragment();
            if (mySiteFragment != null) {
                mySiteFragment.setBlog(blog);
            }

            if (blog != null) {
                int blogId = blog.getLocalTableBlogId();
                ActivityLauncher.showSitePickerForResult(this, blogId);
            }
        }
    }

    /*
     * Simperium Note bucket listeners
     */
//    @Override
//    public void onNetworkChange(Bucket<Note> noteBucket, Bucket.ChangeType changeType, String s) {
//        if (changeType == Bucket.ChangeType.INSERT || changeType == Bucket.ChangeType.MODIFY) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (isFinishing()) return;
//
//                    if (isViewingNotificationsTab()) {
//                        new UpdateLastSeenTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                    } else {
//                        mTabLayout.checkNoteBadge();
//                    }
//                }
//            });
//        }
//    }

    private boolean isViewingNotificationsTab() {
        return mViewPager.getCurrentItem() == WPMainTabAdapter.TAB_NOTIFS;
    }

//    @Override
//    public void onBeforeUpdateObject(Bucket<Note> noteBucket, Note note) {
//        // noop
//    }
//
//    @Override
//    public void onDeleteObject(Bucket<Note> noteBucket, Note note) {
//        // noop
//    }
//
//    @Override
//    public void onSaveObject(Bucket<Note> noteBucket, Note note) {
//        // noop
//    }
}
