package org.wp.h.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.widget.Toast;
import org.wp.h.R;
import org.wp.h.WordPress;
import org.wp.h.models.Blog;
import org.wp.h.models.Post;
import org.wp.h.ui.accounts.HelpActivity;
import org.wp.h.ui.accounts.NewBlogActivity;
import org.wp.h.ui.accounts.SignInActivity;
import org.wp.h.ui.accounts.login.MagicLinkSignInActivity;
import org.wp.h.ui.comments.CommentsActivity;
import org.wp.h.ui.main.SitePickerActivity;
import org.wp.h.ui.media.MediaBrowserActivity;
import org.wp.h.ui.media.WordPressMediaUtils;
import org.wp.h.ui.people.PeopleManagementActivity;
import org.wp.h.ui.plans.PlansActivity;
import org.wp.h.ui.posts.PostPreviewActivity;
import org.wp.h.ui.posts.PostsListActivity;
import org.wp.h.ui.prefs.AccountSettingsActivity;
import org.wp.h.ui.prefs.AppSettingsActivity;
import org.wp.h.ui.prefs.BlogPreferencesActivity;
import org.wp.h.ui.prefs.MyProfileActivity;
import org.wp.h.ui.prefs.SiteSettingsInterface;
import org.wp.h.ui.prefs.notifications.NotificationsSettingsActivity;
import org.wp.h.ui.stats.StatsActivity;
import org.wp.h.ui.stats.StatsConstants;
import org.wp.h.ui.stats.StatsSingleItemDetailsActivity;
import org.wp.h.ui.stats.models.PostModel;
import org.wp.h.ui.themes.ThemeBrowserActivity;
import org.wp.h.util.HelpshiftHelper;
import org.wp.h.util.HelpshiftHelper.Tag;
import org.wp.h.util.UrlUtils;
import org.wp.h.util.WPActivityUtils;

public class ActivityLauncher {
    public static void showSitePickerForResult(Activity activity, int blogLocalTableId) {
        Intent intent = new Intent(activity, SitePickerActivity.class);
        intent.putExtra(SitePickerActivity.KEY_LOCAL_ID, blogLocalTableId);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(
                activity,
                R.anim.activity_slide_in_from_left,
                R.anim.do_nothing);
        ActivityCompat.startActivityForResult(activity, intent, RequestCodes.SITE_PICKER, options.toBundle());
    }

    public static void viewBlogStats(Context context, int blogLocalTableId) {
        if (blogLocalTableId == 0) return;

        Intent intent = new Intent(context, StatsActivity.class);
        intent.putExtra(StatsActivity.ARG_LOCAL_TABLE_BLOG_ID, blogLocalTableId);
        context.startActivity(intent);
    }

    public static void viewBlogPlans(Context context, int blogLocalTableId) {
        Intent intent = new Intent(context, PlansActivity.class);
        intent.putExtra(PlansActivity.ARG_LOCAL_TABLE_BLOG_ID, blogLocalTableId);
        context.startActivity(intent);
    }

    public static void viewCurrentBlogPosts(Context context) {
        Intent intent = new Intent(context, PostsListActivity.class);
        context.startActivity(intent);
//
    }

    public static void viewCurrentBlogMedia(Context context) {
        Intent intent = new Intent(context, MediaBrowserActivity.class);
        context.startActivity(intent);
//
    }

    public static void viewCurrentBlogPages(Context context) {
        Intent intent = new Intent(context, PostsListActivity.class);
        intent.putExtra(PostsListActivity.EXTRA_VIEW_PAGES, true);
        context.startActivity(intent);
//
    }

    public static void viewCurrentBlogComments(Context context) {
        Intent intent = new Intent(context, CommentsActivity.class);
        context.startActivity(intent);
//
    }

    public static void viewCurrentBlogThemes(Context context) {
        if (ThemeBrowserActivity.isAccessible()) {
            Intent intent = new Intent(context, ThemeBrowserActivity.class);
            context.startActivity(intent);
        }
    }

    public static void viewCurrentBlogPeople(Context context) {
        Intent intent = new Intent(context, PeopleManagementActivity.class);
        context.startActivity(intent);
//
    }

    public static void viewBlogSettingsForResult(Activity activity, Blog blog) {
        if (blog == null) return;

        Intent intent = new Intent(activity, BlogPreferencesActivity.class);
        intent.putExtra(BlogPreferencesActivity.ARG_LOCAL_BLOG_ID, blog.getLocalTableBlogId());
        activity.startActivityForResult(intent, RequestCodes.BLOG_SETTINGS);
//
    }

    public static void viewCurrentSite(Context context, Blog blog) {
        if (blog == null) {
            Toast.makeText(context, context.getText(R.string.blog_not_found), Toast.LENGTH_SHORT).show();
            return;
        }

        String siteUrl = blog.getAlternativeHomeUrl();
        Uri uri = Uri.parse(siteUrl);

//

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        context.startActivity(intent);
//        AppLockManager.getInstance().setExtendedTimeout();
    }

    public static void viewBlogAdmin(Context context, Blog blog) {
        if (blog == null) {
            Toast.makeText(context, context.getText(R.string.blog_not_found), Toast.LENGTH_SHORT).show();
            return;
        }

        String adminUrl = blog.getAdminUrl();
        Uri uri = Uri.parse(adminUrl);

//

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        context.startActivity(intent);
//        AppLockManager.getInstance().setExtendedTimeout();
    }

    public static void viewPostPreviewForResult(Activity activity, Post post, boolean isPage) {
        if (post == null) return;

        Intent intent = new Intent(activity, PostPreviewActivity.class);
        intent.putExtra(PostPreviewActivity.ARG_LOCAL_POST_ID, post.getLocalTablePostId());
        intent.putExtra(PostPreviewActivity.ARG_LOCAL_BLOG_ID, post.getLocalTableBlogId());
        intent.putExtra(PostPreviewActivity.ARG_IS_PAGE, isPage);
        activity.startActivityForResult(intent, RequestCodes.PREVIEW_POST);
    }

    public static void addNewBlogPostOrPageForResult(Activity context, Blog blog, boolean isPage) {
        if (blog == null) return;

        // Create a new post object and assign default settings
        Post newPost = new Post(blog.getLocalTableBlogId(), isPage);
        newPost.setCategories("[" + SiteSettingsInterface.getDefaultCategory(context) +"]");
        newPost.setPostFormat(SiteSettingsInterface.getDefaultFormat(context));
        WordPress.wpDB.savePost(newPost);


    }

    public static void editBlogPostOrPageForResult(Activity activity, long postOrPageId, boolean isPage) {

    }

    /*
     * Load the post preview as an authenticated URL so stats aren't bumped
     */
    public static void browsePostOrPage(Context context, Blog blog, Post post) {
        if (blog == null || post == null || TextUtils.isEmpty(post.getPermaLink())) return;

        // always add the preview parameter to avoid bumping stats when viewing posts
        String url = UrlUtils.appendUrlParameter(post.getPermaLink(), "preview", "true");
        WPWebViewActivity.openUrlByUsingBlogCredentials(context, blog, post, url);
    }

    public static void addMedia(Activity activity) {
        WordPressMediaUtils.launchPictureLibrary(activity);
    }

    public static void viewMyProfile(Context context) {
        Intent intent = new Intent(context, MyProfileActivity.class);
//
        context.startActivity(intent);
    }

    public static void viewAccountSettings(Context context) {
        Intent intent = new Intent(context, AccountSettingsActivity.class);
//
        context.startActivity(intent);
    }

    public static void viewAppSettings(Activity activity) {
        Intent intent = new Intent(activity, AppSettingsActivity.class);
//
        activity.startActivityForResult(intent, RequestCodes.APP_SETTINGS);
    }

    public static void viewNotificationsSettings(Activity activity) {
        Intent intent = new Intent(activity, NotificationsSettingsActivity.class);
        activity.startActivity(intent);
    }

    public static void viewHelpAndSupport(Context context, Tag origin) {
        Intent intent = new Intent(context, HelpActivity.class);
        intent.putExtra(HelpshiftHelper.ORIGIN_KEY, origin);
        context.startActivity(intent);
    }



    public static void newBlogForResult(Activity activity) {
        Intent intent = new Intent(activity, NewBlogActivity.class);
        intent.putExtra(NewBlogActivity.KEY_START_MODE, NewBlogActivity.CREATE_BLOG);
        activity.startActivityForResult(intent, RequestCodes.CREATE_BLOG);
    }

    public static void showSignInForResult(Activity activity) {
        if (shouldShowMagicLinksLogin(activity)) {
            Intent intent = new Intent(activity, MagicLinkSignInActivity.class);
            activity.startActivityForResult(intent, RequestCodes.ADD_ACCOUNT);
        } else {
            Intent intent = new Intent(activity, SignInActivity.class);
            activity.startActivityForResult(intent, RequestCodes.ADD_ACCOUNT);
        }
    }

    public static void viewStatsSinglePostDetails(Context context, Post post, boolean isPage) {
        if (post == null) return;

        int remoteBlogId = WordPress.wpDB.getRemoteBlogIdForLocalTableBlogId(post.getLocalTableBlogId());
        PostModel postModel = new PostModel(
                Integer.toString(remoteBlogId),
                post.getRemotePostId(),
                post.getTitle(),
                post.getLink(),
                isPage ? StatsConstants.ITEM_TYPE_PAGE : StatsConstants.ITEM_TYPE_POST);
        viewStatsSinglePostDetails(context, postModel);
    }

    public static void viewStatsSinglePostDetails(Context context, PostModel post) {
        if (post == null) return;

        Intent statsPostViewIntent = new Intent(context, StatsSingleItemDetailsActivity.class);
        statsPostViewIntent.putExtra(StatsSingleItemDetailsActivity.ARG_REMOTE_BLOG_ID, post.getBlogID());
        statsPostViewIntent.putExtra(StatsSingleItemDetailsActivity.ARG_REMOTE_ITEM_ID, post.getItemID());
        statsPostViewIntent.putExtra(StatsSingleItemDetailsActivity.ARG_REMOTE_ITEM_TYPE, post.getPostType());
        statsPostViewIntent.putExtra(StatsSingleItemDetailsActivity.ARG_ITEM_TITLE, post.getTitle());
        statsPostViewIntent.putExtra(StatsSingleItemDetailsActivity.ARG_ITEM_URL, post.getUrl());
        context.startActivity(statsPostViewIntent);
    }

    public static void addSelfHostedSiteForResult(Activity activity) {
        Intent intent = new Intent(activity, SignInActivity.class);
        intent.putExtra(SignInActivity.EXTRA_START_FRAGMENT, SignInActivity.ADD_SELF_HOSTED_BLOG);
        activity.startActivityForResult(intent, RequestCodes.ADD_ACCOUNT);
    }

    public static boolean shouldShowMagicLinksLogin(Activity activity) {
        boolean isMagicLinksEnabled = false;

        return isMagicLinksEnabled && WPActivityUtils.isEmailClientAvailable(activity);
    }
}
