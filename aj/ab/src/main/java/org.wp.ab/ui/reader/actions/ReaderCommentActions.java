package org.wp.ab.ui.reader.actions;

import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.wordpress.rest.RestRequest;

import org.json.JSONObject;
import org.wp.ab.WordPress;
import org.wp.ab.datasets.ReaderCommentTable;
import org.wp.ab.datasets.ReaderLikeTable;
import org.wp.ab.datasets.ReaderUserTable;
import org.wp.ab.models.ReaderComment;
import org.wp.ab.models.ReaderPost;
import org.wp.ab.models.ReaderUser;
import org.wp.ab.util.AppLog;
import org.wp.ab.util.AppLog.T;
import org.wp.ab.util.DateTimeUtils;
import org.wp.ab.util.JSONUtils;
import org.wp.ab.util.VolleyUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReaderCommentActions {
    /*
     * used by post detail to generate a temporary "fake" comment id (see below)
     */
    public static long generateFakeCommentId() {
        return System.currentTimeMillis();
    }

    /*
     * add the passed comment text to the passed post - caller must pass a unique "fake" comment id
     * to give the comment that's generated locally
     */
    public static ReaderComment submitPostComment(final ReaderPost post,
                                                  final long fakeCommentId,
                                                  final String commentText,
                                                  final long replyToCommentId,
                                                  final ReaderActions.CommentActionListener actionListener) {
        if (post == null || TextUtils.isEmpty(commentText)) {
            return null;
        }

        // determine which page this new comment should be assigned to
        final int pageNumber;
        if (replyToCommentId != 0) {
            pageNumber = ReaderCommentTable.getPageNumberForComment(post.blogId, post.postId, replyToCommentId);
        } else {
            pageNumber = ReaderCommentTable.getLastPageNumberForPost(post.blogId, post.postId);
        }

        // create a "fake" comment that's added to the db so it can be shown right away - will be
        // replaced with actual comment if it succeeds to be posted, or deleted if comment fails
        // to be posted
        ReaderComment newComment = new ReaderComment();
        newComment.commentId = fakeCommentId;
        newComment.postId = post.postId;
        newComment.blogId = post.blogId;
        newComment.parentId = replyToCommentId;
        newComment.pageNumber = pageNumber;
        newComment.setText(commentText);

        Date dtPublished = DateTimeUtils.nowUTC();
        newComment.setPublished(DateTimeUtils.javaDateToIso8601(dtPublished));
        newComment.timestamp = dtPublished.getTime();

        ReaderUser currentUser = ReaderUserTable.getCurrentUser();
        if (currentUser != null) {
            newComment.setAuthorAvatar(currentUser.getAvatarUrl());
            newComment.setAuthorName(currentUser.getDisplayName());
        }

        ReaderCommentTable.addOrUpdateComment(newComment);

        // different endpoint depending on whether the new comment is a reply to another comment
        final String path;
        if (replyToCommentId == 0) {
            path = "sites/" + post.blogId + "/posts/" + post.postId + "/replies/new";
        } else {
            path = "sites/" + post.blogId + "/comments/" + Long.toString(replyToCommentId) + "/replies/new";
        }

        Map<String, String> params = new HashMap<>();
        params.put("content", commentText);

        RestRequest.Listener listener = new RestRequest.Listener() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                ReaderCommentTable.deleteComment(post, fakeCommentId);
                AppLog.i(T.READER, "comment succeeded");
                ReaderComment newComment = ReaderComment.fromJson(jsonObject, post.blogId);
                newComment.pageNumber = pageNumber;
                ReaderCommentTable.addOrUpdateComment(newComment);
                if (actionListener != null) {
                    actionListener.onActionResult(true, newComment);
                }
            }
        };
        RestRequest.ErrorListener errorListener = new RestRequest.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ReaderCommentTable.deleteComment(post, fakeCommentId);
                AppLog.w(T.READER, "comment failed");
                AppLog.e(T.READER, volleyError);
                if (actionListener != null) {
                    actionListener.onActionResult(false, null);
                }
            }
        };

        AppLog.i(T.READER, "submitting comment");
        WordPress.getRestClientUtilsV1_1().post(path, params, null, listener, errorListener);

        return newComment;
    }

    /*
     * like or unlike the passed comment
     */
    public static boolean performLikeAction(final ReaderComment comment, boolean isAskingToLike) {
        if (comment == null) {
            return false;
        }

        // make sure like status is changing
        boolean isCurrentlyLiked = ReaderCommentTable.isCommentLikedByCurrentUser(comment);
        if (isCurrentlyLiked == isAskingToLike) {
            AppLog.w(T.READER, "comment like unchanged");
            return false;
        }

        // update like status and like count in local db
        int newNumLikes = (isAskingToLike ? comment.numLikes + 1 : comment.numLikes - 1);
        ReaderCommentTable.setLikesForComment(comment, newNumLikes, isAskingToLike);
        ReaderLikeTable.setCurrentUserLikesComment(comment, isAskingToLike);

        // sites/$site/comments/$comment_ID/likes/new
        final String actionName = isAskingToLike ? "like" : "unlike";
        String path = "sites/" + comment.blogId + "/comments/" + comment.commentId + "/likes/";
        if (isAskingToLike) {
            path += "new";
        } else {
            path += "mine/delete";
        }

        RestRequest.Listener listener = new RestRequest.Listener() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                boolean success = (jsonObject != null && JSONUtils.getBool(jsonObject, "success"));
                if (success) {
                    AppLog.d(T.READER, String.format("comment %s succeeded", actionName));
                } else {
                    AppLog.w(T.READER, String.format("comment %s failed", actionName));
                    ReaderCommentTable.setLikesForComment(comment, comment.numLikes, comment.isLikedByCurrentUser);
                    ReaderLikeTable.setCurrentUserLikesComment(comment, comment.isLikedByCurrentUser);
                }
            }
        };

        RestRequest.ErrorListener errorListener = new RestRequest.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String error = VolleyUtils.errStringFromVolleyError(volleyError);
                if (TextUtils.isEmpty(error)) {
                    AppLog.w(T.READER, String.format("comment %s failed", actionName));
                } else {
                    AppLog.w(T.READER, String.format("comment %s failed (%s)", actionName, error));
                }
                AppLog.e(T.READER, volleyError);
                ReaderCommentTable.setLikesForComment(comment, comment.numLikes, comment.isLikedByCurrentUser);
                ReaderLikeTable.setCurrentUserLikesComment(comment, comment.isLikedByCurrentUser);
            }
        };

        WordPress.getRestClientUtilsV1_1().post(path, listener, errorListener);
        return true;
    }
}
