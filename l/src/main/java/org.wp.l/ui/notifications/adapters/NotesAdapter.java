package org.wp.l.ui.notifications.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.wp.l.R;
import org.wp.l.ui.notifications.NotificationsListFragment;
import org.wp.l.util.StringUtils;
import org.wp.l.widgets.NoticonTextView;
import org.wp.l.widgets.WPNetworkImageView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends CursorRecyclerViewAdapter<NotesAdapter.NoteViewHolder> {

    private final int mAvatarSz;
    private final int mColorRead;
    private final int mColorUnread;
    private final int mTextIndentSize;
    private final List<String> mHiddenNoteIds = new ArrayList<>();
    private final List<String> mModeratingNoteIds = new ArrayList<>();

    private NotificationsListFragment.OnNoteClickListener mOnNoteClickListener;

    public NotesAdapter(Context context) {
        super(context, null);

        setHasStableIds(true);


        mAvatarSz = (int) context.getResources().getDimension(R.dimen.notifications_avatar_sz);
        mColorRead = context.getResources().getColor(R.color.white);
        mColorUnread = context.getResources().getColor(R.color.grey_light);
        mTextIndentSize = context.getResources().getDimensionPixelSize(R.dimen.notifications_text_indent_sz);
    }

    public void closeCursor() {
        Cursor cursor = getCursor();
        if (cursor != null) {
            cursor.close();
        }
    }


    public void queryNotes() {
    }

    public void queryNotes(String columnName, Object value) {
    }

    public void addHiddenNoteId(String noteId) {
        mHiddenNoteIds.add(noteId);
        notifyDataSetChanged();
    }

    public void removeHiddenNoteId(String noteId) {
        mHiddenNoteIds.remove(noteId);
        notifyDataSetChanged();
    }

    public void addModeratingNoteId(String noteId) {
        mModeratingNoteIds.add(noteId);
        notifyDataSetChanged();
    }

    public void removeModeratingNoteId(String noteId) {
        mModeratingNoteIds.remove(noteId);
        notifyDataSetChanged();
    }

    private String getStringForColumnName(Cursor cursor, String columnName) {
        if (columnName == null || cursor == null || cursor.getColumnIndex(columnName) == -1) {
            return "";
        }

        return StringUtils.notNullStr(cursor.getString(cursor.getColumnIndex(columnName)));
    }

    private int getIntForColumnName(Cursor cursor, String columnName) {
        if (columnName == null || cursor == null || cursor.getColumnIndex(columnName) == -1) {
            return -1;
        }

        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    private long getLongForColumnName(Cursor cursor, String columnName) {
        if (columnName == null || cursor == null || cursor.getColumnIndex(columnName) == -1) {
            return -1;
        }

        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public int getCount() {
        if (getCursor() != null) {
            return getCursor().getCount();
        }

        return 0;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_list_item, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder noteViewHolder, Cursor cursor) {


    }

    public int getPositionForNote(String noteId) {

        return RecyclerView.NO_POSITION;
    }

    public void setOnNoteClickListener(NotificationsListFragment.OnNoteClickListener mNoteClickListener) {
        mOnNoteClickListener = mNoteClickListener;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final View headerView;
        private final View contentView;
        private final TextView headerText;

        private final TextView txtSubject;
        private final TextView txtSubjectNoticon;
        private final TextView txtDetail;
        private final WPNetworkImageView imgAvatar;
        private final NoticonTextView noteIcon;
        private final View progressBar;

        public NoteViewHolder(View view) {
            super(view);
            headerView = view.findViewById(R.id.time_header);
            contentView = view.findViewById(R.id.note_content_container);
            headerText = (TextView)view.findViewById(R.id.header_date_text);
            txtSubject = (TextView) view.findViewById(R.id.note_subject);
            txtSubjectNoticon = (TextView) view.findViewById(R.id.note_subject_noticon);
            txtDetail = (TextView) view.findViewById(R.id.note_detail);
            imgAvatar = (WPNetworkImageView) view.findViewById(R.id.note_avatar);
            noteIcon = (NoticonTextView) view.findViewById(R.id.note_icon);
            progressBar = view.findViewById(R.id.moderate_progress);

            itemView.setOnClickListener(mOnClickListener);
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnNoteClickListener != null && v.getTag() instanceof String) {
                mOnNoteClickListener.onClickNote((String)v.getTag());
            }
        }
    };
}
