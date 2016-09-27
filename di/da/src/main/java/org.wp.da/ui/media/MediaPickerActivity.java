package org.wp.da.ui.media;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import org.wp.da.R;
import org.wp.da.ui.RequestCodes;
import org.wp.da.util.MediaUtils;
import org.wp.da.widgets.WPViewPager;

import java.io.File;

/**
 * Allows users to select a variety of videos and images from any source.
 *
 * Title can be set either by defining a string resource, R.string.media_picker_title, or passing
 * a String extra in the {@link android.content.Intent} with the key ACTIVITY_TITLE_KEY.
 *
 * Accepts Image and Video sources as arguments and displays each in a tab.
 */

public class MediaPickerActivity extends AppCompatActivity {
    /**
     * Request code for the {@link android.content.Intent} to start media selection.
     */
    public static final int ACTIVITY_REQUEST_CODE_MEDIA_SELECTION = 6000;
    /**
     * Result code signaling that media has been selected.
     */
    public static final int ACTIVITY_RESULT_CODE_MEDIA_SELECTED   = 6001;
    /**
     * Result code signaling that a gallery should be created with the results.
     */
    public static final int ACTIVITY_RESULT_CODE_GALLERY_CREATED  = 6002;

    /**
     * Pass a {@link String} with this key in the {@link android.content.Intent} to set the title.
     */
    public static final String ACTIVITY_TITLE_KEY           = "activity-title";


    public static final String DEVICE_IMAGE_MEDIA_SOURCES_KEY = "device-image-media-sources";


    public static final String DEVICE_VIDEO_MEDIA_SOURCES_KEY = "device- video=media-sources";
    public static final String BLOG_IMAGE_MEDIA_SOURCES_KEY = "blog-image-media-sources";
    public static final String BLOG_VIDEO_MEDIA_SOURCES_KEY = "blog-video-media-sources";
    public static final String SELECTED_CONTENT_RESULTS_KEY = "selected-content";

    private static final String CAPTURE_PATH_KEY = "capture-path";

    private static final long   TAB_ANIMATION_DURATION_MS = 250L;

//    private ArrayList<MediaSource>[] mMediaSources;
    private TabLayout              mTabLayout;
    private WPViewPager            mViewPager;
    private ActionMode             mActionMode;
    private String                 mCapturePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lockRotation();
        initializeContentView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.media_picker, menu);

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(CAPTURE_PATH_KEY, mCapturePath);
    }

    @Override
    public void onRestoreInstanceState( Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey(CAPTURE_PATH_KEY)) {
            mCapturePath = savedInstanceState.getString(CAPTURE_PATH_KEY);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.capture_image) {
            WordPressMediaUtils.launchCamera(this, new WordPressMediaUtils.LaunchCameraCallback() {
                @Override
                public void onMediaCapturePathReady(String mediaCapturePath) {
                    mCapturePath = mediaCapturePath;
                }
            });
            return true;
        } else if (item.getItemId() == R.id.capture_video) {
            WordPressMediaUtils.launchVideoCamera(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RequestCodes.TAKE_PHOTO:
                File file = new File(mCapturePath);
                Uri imageUri = Uri.fromFile(file);

                if (file.exists() && MediaUtils.isValidImage(imageUri.toString())) {
                    // Notify MediaStore of new content
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageUri));


                }
                break;
            case RequestCodes.TAKE_VIDEO:
                Uri videoUri = data != null ? data.getData() : null;

                if (videoUri != null) {
                    // Notify MediaStore of new content
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, videoUri));


                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mActionMode != null) {
            mActionMode.finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        super.onActionModeStarted(mode);

        mViewPager.setPagingEnabled(false);
        mActionMode = mode;

        animateTabGone();
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);

        mViewPager.setPagingEnabled(true);
        mActionMode = null;

        animateTabAppear();
    }


    /**
     * Helper method; locks device orientation to its current state while media is being selected
     */
    private void lockRotation() {
        switch (getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case Surface.ROTATION_90:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case Surface.ROTATION_180:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                break;
            case Surface.ROTATION_270:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                break;
        }
    }

    /**
     * Helper method; sets up the tab bar, media adapter, and ViewPager for displaying media content
     */
    private void initializeContentView() {
        setContentView(R.layout.media_picker_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.show();
        }

//        mMediaPickerAdapter = null;
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (WPViewPager) findViewById(R.id.media_picker_pager);

        if (mViewPager != null) {
            mViewPager.setPagingEnabled(true);



            if (mTabLayout != null) {
                int normalColor = getResources().getColor(R.color.blue_light);
                int selectedColor = getResources().getColor(R.color.white);
                mTabLayout.setTabTextColors(normalColor, selectedColor);
                mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                mTabLayout.setupWithViewPager(mViewPager);
            }
        }
    }

    /**
     * Helper method; animates the tab bar and ViewPager in when ActionMode ends
     */
    private void animateTabAppear() {
        TranslateAnimation tabAppearAnimation = new TranslateAnimation(0, 0, -mTabLayout.getHeight(), 0);
        TranslateAnimation pagerAppearAnimation = new TranslateAnimation(0, 0, -mTabLayout.getHeight(), 0);

        tabAppearAnimation.setDuration(TAB_ANIMATION_DURATION_MS);
        pagerAppearAnimation.setDuration(TAB_ANIMATION_DURATION_MS);
        pagerAppearAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mViewPager.getWidth(), mViewPager.getHeight() - mTabLayout.getHeight());
                mViewPager.setLayoutParams(params);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        mTabLayout.setVisibility(View.VISIBLE);
        mViewPager.startAnimation(pagerAppearAnimation);
        mTabLayout.startAnimation(tabAppearAnimation);
    }

    /**
     * Helper method; animates the tab bar and ViewPager out when ActionMode begins
     */
    private void animateTabGone() {
        TranslateAnimation tabGoneAnimation = new TranslateAnimation(0, 0, 0, -mTabLayout.getHeight());
        TranslateAnimation pagerGoneAnimation = new TranslateAnimation(0, 0, 0, -mTabLayout.getHeight());
        tabGoneAnimation.setDuration(TAB_ANIMATION_DURATION_MS);
        pagerGoneAnimation.setDuration(TAB_ANIMATION_DURATION_MS);
        tabGoneAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTabLayout.setVisibility(View.GONE);
                mTabLayout.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        pagerGoneAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                LinearLayout.LayoutParams newParams = new LinearLayout.LayoutParams(mViewPager.getWidth(), mViewPager.getHeight() + mTabLayout.getHeight());
                mViewPager.setLayoutParams(newParams);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mViewPager.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        mTabLayout.startAnimation(tabGoneAnimation);
        mViewPager.startAnimation(pagerGoneAnimation);
    }

}
