package org.wp.zb.ui.reader;



import org.wp.zb.models.ReaderPost;
import org.wp.zb.models.ReaderPostList;
import org.wp.zb.models.ReaderTag;
import org.wp.zb.ui.reader.actions.ReaderActions;
import org.wp.zb.ui.reader.models.ReaderRelatedPostList;
import org.wp.zb.ui.reader.services.ReaderPostService;
import org.wp.zb.util.StringUtils;

/**
 * Reader-related EventBus event classes
 */
public class ReaderEvents {

    private ReaderEvents() {
        throw new AssertionError();
    }

    public static class FollowedTagsChanged {}
    public static class RecommendedTagsChanged{}

    public static class TagAdded {
        private final String mTagName;
        public TagAdded(String tagName) {
            mTagName = tagName;
        }
        public String getTagName() {
            return StringUtils.notNullStr(mTagName);
        }
    }

    public static class FollowedBlogsChanged {}
    public static class RecommendedBlogsChanged {}

    public static class UpdatePostsStarted {
        private final ReaderPostService.UpdateAction mAction;
        public UpdatePostsStarted(ReaderPostService.UpdateAction action) {
            mAction = action;
        }
        public ReaderPostService.UpdateAction getAction() {
            return mAction;
        }
    }

    public static class UpdatePostsEnded {
        private final ReaderTag mReaderTag;
        private final ReaderActions.UpdateResult mResult;
        private final ReaderPostService.UpdateAction mAction;
        public UpdatePostsEnded(ReaderActions.UpdateResult result,
                                ReaderPostService.UpdateAction action) {
            mResult = result;
            mAction = action;
            mReaderTag = null;
        }
        public UpdatePostsEnded(ReaderTag readerTag,
                                ReaderActions.UpdateResult result,
                                ReaderPostService.UpdateAction action) {
            mReaderTag = readerTag;
            mResult = result;
            mAction = action;
        }
        public ReaderTag getReaderTag() {
            return mReaderTag;
        }
        public ReaderActions.UpdateResult getResult() {
            return mResult;
        }
        public ReaderPostService.UpdateAction getAction() {
            return mAction;
        }
    }

    public static class SearchPostsStarted {
        private final String mQuery;
        private final int mOffset;
        public SearchPostsStarted( String query, int offset) {
            mQuery = query;
            mOffset = offset;
        }
        public String getQuery() {
            return mQuery;
        }
        public int getOffset() {
            return mOffset;
        }
    }
    public static class SearchPostsEnded {
        private final String mQuery;
        private final boolean mDidSucceed;
        private final int mOffset;
        public SearchPostsEnded( String query, int offset, boolean didSucceed) {
            mQuery = query;
            mOffset = offset;
            mDidSucceed = didSucceed;
        }
        public boolean didSucceed() {
            return mDidSucceed;
        }
        public String getQuery() {
            return mQuery;
        }
        public int getOffset() {
            return mOffset;
        }
    }

    public static class UpdateCommentsStarted {}
    public static class UpdateCommentsEnded {
        private final ReaderActions.UpdateResult mResult;
        public UpdateCommentsEnded(ReaderActions.UpdateResult result) {
            mResult = result;
        }
        public ReaderActions.UpdateResult getResult() {
            return mResult;
        }
    }

    public static class RelatedPostsUpdated {
        private final ReaderPost mSourcePost;
        private final ReaderRelatedPostList mRelatedPosts;
        public RelatedPostsUpdated( ReaderPost sourcePost,  ReaderPostList relatedPosts) {
            mSourcePost = sourcePost;
            mRelatedPosts = new ReaderRelatedPostList(relatedPosts);
        }
        public ReaderPost getSourcePost() {
            return mSourcePost;
        }
        public ReaderRelatedPostList getRelatedPosts() {
            return mRelatedPosts;
        }
    }
}
