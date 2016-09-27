package org.wp.r.ui.comments;

import org.wp.r.models.CommentList;
import org.wp.r.models.CommentStatus;

class CommentEvents {

    public static class CommentsBatchModerationFinishedEvent {
        private final CommentList mComments;
        private final boolean mIsDeleted;

        public CommentsBatchModerationFinishedEvent(CommentList comments, boolean isDeleted) {
            mComments = comments;
            mIsDeleted = isDeleted;
        }

        public CommentList getComments() {
            return mComments;
        }

        public boolean isDeleted() {
            return mIsDeleted;
        }

    }

    public static class CommentModerationFinishedEvent {
        private final boolean mIsSuccess;
        private final boolean mIsCommentsRefreshRequired;
        private final long mCommentId;
        private final CommentStatus mNewStatus;

        public CommentModerationFinishedEvent(boolean isSuccess, boolean isCommentsRefreshRequired, long commentId, CommentStatus newStatus) {
            mIsSuccess = isSuccess;
            mIsCommentsRefreshRequired = isCommentsRefreshRequired;
            mCommentId = commentId;
            mNewStatus = newStatus;
        }

        public boolean isSuccess() {
            return mIsSuccess;
        }

        public boolean isCommentsRefreshRequired() {
            return mIsCommentsRefreshRequired;
        }

        public long getCommentId() {
            return mCommentId;
        }

        public CommentStatus getNewStatus() {
            return mNewStatus;
        }
    }
}
