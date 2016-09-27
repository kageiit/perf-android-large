package org.wp.x.ui.suggestion.util;

import android.content.Context;

import org.wp.x.WordPress;
import org.wp.x.datasets.SuggestionTable;
import org.wp.x.models.Blog;
import org.wp.x.models.Suggestion;
import org.wp.x.models.Tag;
import org.wp.x.ui.suggestion.adapters.SuggestionAdapter;
import org.wp.x.ui.suggestion.adapters.TagSuggestionAdapter;

import java.util.List;

public class SuggestionUtils {

    public static SuggestionAdapter setupSuggestions(final int remoteBlogId, Context context, SuggestionServiceConnectionManager serviceConnectionManager) {
        Blog blog = WordPress.wpDB.getBlogForDotComBlogId(Integer.toString(remoteBlogId));
        boolean isDotComFlag = (blog != null && blog.isDotcomFlag());

        return SuggestionUtils.setupSuggestions(remoteBlogId, context, serviceConnectionManager, isDotComFlag);
    }

    public static SuggestionAdapter setupSuggestions(final int remoteBlogId, Context context, SuggestionServiceConnectionManager serviceConnectionManager, boolean isDotcomFlag) {
        if (!isDotcomFlag) {
            return null;
        }

        SuggestionAdapter suggestionAdapter = new SuggestionAdapter(context);

        List<Suggestion> suggestions = SuggestionTable.getSuggestionsForSite(remoteBlogId);
        // if the suggestions are not stored yet, we want to trigger an update for it
        if (suggestions.isEmpty()) {
            serviceConnectionManager.bindToService();
        }
        suggestionAdapter.setSuggestionList(suggestions);
        return suggestionAdapter;
    }

    public static TagSuggestionAdapter setupTagSuggestions(final int remoteBlogId, Context context, SuggestionServiceConnectionManager serviceConnectionManager) {
        Blog blog = WordPress.wpDB.getBlogForDotComBlogId(Integer.toString(remoteBlogId));
        boolean isDotComFlag = (blog != null && blog.isDotcomFlag());

        return SuggestionUtils.setupTagSuggestions(remoteBlogId, context, serviceConnectionManager, isDotComFlag);
    }

    public static TagSuggestionAdapter setupTagSuggestions(final int remoteBlogId, Context context, SuggestionServiceConnectionManager serviceConnectionManager, boolean isDotcomFlag) {
        if (!isDotcomFlag) {
            return null;
        }

        TagSuggestionAdapter tagSuggestionAdapter = new TagSuggestionAdapter(context);

        List<Tag> tags = SuggestionTable.getTagsForSite(remoteBlogId);
        // if the tags are not stored yet, we want to trigger an update for it
        if (tags.isEmpty()) {
            serviceConnectionManager.bindToService();
        }
        tagSuggestionAdapter.setTagList(tags);
        return tagSuggestionAdapter;
    }
}
