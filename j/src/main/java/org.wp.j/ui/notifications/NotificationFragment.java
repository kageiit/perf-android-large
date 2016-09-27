/**
 * Provides a list view and list adapter to display a note. It will have a header view to show
 * the avatar and other details for the post.
 *
 * More specialized note adapters will need to be made to provide the correct views for the type
 * of note/note template it has.
 */
package org.wp.j.ui.notifications;

import org.wp.j.models.Note;

public interface NotificationFragment {
    public static interface OnPostClickListener {
        public void onPostClicked(Note note, int remoteBlogId, int postId);
    }

    public Note getNote();
    public void setNote(Note note);
}
