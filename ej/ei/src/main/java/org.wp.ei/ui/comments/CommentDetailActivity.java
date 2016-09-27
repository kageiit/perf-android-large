package org.wp.ei.ui.comments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import org.wp.ei.R;
import org.wp.ei.models.Note;
import org.wp.ei.ui.ActivityId;
import org.wp.ei.util.ToastUtils;

// simple wrapper activity for CommentDetailFragment
public class CommentDetailActivity extends AppCompatActivity {

    private static final String KEY_COMMENT_DETAIL_LOCAL_TABLE_BLOG_ID = "local_table_blog_id";
    private static final String KEY_COMMENT_DETAIL_COMMENT_ID = "comment_detail_comment_id";
    private static final String KEY_COMMENT_DETAIL_NOTE_ID = "comment_detail_note_id";
    private static final String KEY_COMMENT_DETAIL_IS_REMOTE = "comment_detail_is_remote";

    private static final String TAG_COMMENT_DETAIL_FRAGMENT = "tag_comment_detail_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.comment_activity_detail);

        setTitle(R.string.comment);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            CommentDetailFragment commentDetailFragment = null;
            if (intent.getStringExtra(KEY_COMMENT_DETAIL_NOTE_ID) != null) {
                    Note note = null;

                    if (intent.hasExtra(KEY_COMMENT_DETAIL_IS_REMOTE)) {
                        commentDetailFragment = CommentDetailFragment.newInstanceForRemoteNoteComment(note.getId());
                    } else {
                        commentDetailFragment = CommentDetailFragment.newInstance(note.getId());
                    }

            } else if (intent.getIntExtra(KEY_COMMENT_DETAIL_LOCAL_TABLE_BLOG_ID, 0) > 0
                    && intent.getLongExtra(KEY_COMMENT_DETAIL_COMMENT_ID, 0) > 0) {
                commentDetailFragment = CommentDetailFragment.newInstance(
                        intent.getIntExtra(KEY_COMMENT_DETAIL_LOCAL_TABLE_BLOG_ID, 0),
                        intent.getLongExtra(KEY_COMMENT_DETAIL_COMMENT_ID, 0)
                );
            }

            if (commentDetailFragment != null) {
                commentDetailFragment.setRetainInstance(true);
                getFragmentManager().beginTransaction()
                        .add(R.id.comment_detail_container, commentDetailFragment, TAG_COMMENT_DETAIL_FRAGMENT)
                        .commit();
            } else {
                ToastUtils.showToast(this, R.string.error_load_comment);
                finish();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityId.trackLastActivity(ActivityId.COMMENT_DETAIL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
