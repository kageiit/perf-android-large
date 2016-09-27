package org.wp.fb.models;

import android.text.TextUtils;
import android.text.format.DateUtils;

import org.wp.fb.WordPress;
import org.wp.fb.ui.posts.services.PostUploadService;
import org.wp.fb.util.DateTimeUtils;
import org.wp.fb.util.HtmlUtils;
import org.wp.fb.util.StringUtils;

import java.text.BreakIterator;
import java.util.Date;

/**
 * Barebones post/page as listed in PostsListFragment
 */
public class PostsListPost {
    private static final int MAX_EXCERPT_LEN = 150;

    private final long postId;
    private final long blogId;
    private long dateCreatedGmt;
    private final long featuredImageId;

    private final String title;
    private String excerpt;
    private final String description;
    private final String status;

    private final boolean isLocalDraft;
    private final boolean hasLocalChanges;
    private final boolean isUploading;

    // featuredImageUrl is generated by the adapter on the fly
    private transient String featuredImageUrl;

    public PostsListPost(Post post) {
        postId = post.getLocalTablePostId();
        blogId = post.getLocalTableBlogId();
        featuredImageId = post.getFeaturedImageId();

        title = post.getTitle();
        description = post.getDescription();
        excerpt = post.getPostExcerpt();

        status = post.getPostStatus();
        isLocalDraft = post.isLocalDraft();
        hasLocalChanges = post.isLocalChange();
        isUploading = PostUploadService.isPostUploading(postId);

        setDateCreatedGmt(post.getDate_created_gmt());

        // if the post doesn't have an excerpt, generate one from the description
        if (!hasExcerpt() && hasDescription()) {
            excerpt = makeExcerpt(description);
        }
    }

    public long getPostId() {
        return postId;
    }

    public long getBlogId() {
        return blogId;
    }

    public String getTitle() {
        return StringUtils.notNullStr(title);
    }
    public boolean hasTitle() {
        return !TextUtils.isEmpty(title);
    }

    public String getDescription() {
        return StringUtils.notNullStr(description);
    }
    public boolean hasDescription() {
        return !TextUtils.isEmpty(description);
    }

    public String getExcerpt() {
        return StringUtils.notNullStr(excerpt);
    }
    public boolean hasExcerpt() {
        return !TextUtils.isEmpty(excerpt);
    }

    /*
     * java's string.trim() doesn't handle non-breaking space chars (#160), which may appear at the
     * end of post content - work around this by converting them to standard spaces before trimming
     */
    private static final String NBSP = String.valueOf((char) 160);
    private static String trimEx(final String s) {
        return s.replace(NBSP, " ").trim();
    }

    private static String makeExcerpt(String description) {
        if (TextUtils.isEmpty(description)) {
            return null;
        }

        String s = HtmlUtils.fastStripHtml(description);
        if (s.length() < MAX_EXCERPT_LEN) {
            return trimEx(s);
        }

        StringBuilder result = new StringBuilder();
        BreakIterator wordIterator = BreakIterator.getWordInstance();
        wordIterator.setText(s);
        int start = wordIterator.first();
        int end = wordIterator.next();
        int totalLen = 0;
        while (end != BreakIterator.DONE) {
            String word = s.substring(start, end);
            result.append(word);
            totalLen += word.length();
            if (totalLen >= MAX_EXCERPT_LEN) {
                break;
            }
            start = end;
            end = wordIterator.next();
        }

        if (totalLen == 0) {
            return null;
        }
        return trimEx(result.toString()) + "...";
    }

    public long getFeaturedImageId() {
        return featuredImageId;
    }
    public boolean hasFeaturedImageId() {
        return featuredImageId != 0;
    }

    public String getFeaturedImageUrl() {
        return StringUtils.notNullStr(featuredImageUrl);
    }
    public void setFeaturedImageUrl(String url) {
        this.featuredImageUrl = StringUtils.notNullStr(url);
    }
    public boolean hasFeaturedImageUrl() {
        return !TextUtils.isEmpty(featuredImageUrl);
    }

    public long getDateCreatedGmt() {
        return dateCreatedGmt;
    }
    private void setDateCreatedGmt(long dateCreatedGmt) {
        this.dateCreatedGmt = dateCreatedGmt;
    }

    public String getOriginalStatus() {
        return StringUtils.notNullStr(status);
    }

    public PostStatus getStatusEnum() {
        return PostStatus.fromPostsListPost(this);
    }

    public String getFormattedDate() {
        if (getStatusEnum() == PostStatus.SCHEDULED) {
            return DateUtils.formatDateTime(WordPress.getContext(), dateCreatedGmt, DateUtils.FORMAT_ABBREV_ALL);
        } else {
            return DateTimeUtils.javaDateToTimeSpan(new Date(dateCreatedGmt));
        }
    }

    public boolean isLocalDraft() {
        return isLocalDraft;
    }

    public boolean hasLocalChanges() {
        return hasLocalChanges;
    }

    public boolean isUploading() {
        return isUploading;
    }

}
