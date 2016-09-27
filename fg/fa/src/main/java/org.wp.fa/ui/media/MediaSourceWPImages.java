package org.wp.fa.ui.media;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import org.wp.fa.R;
import org.wp.fa.WordPress;
import org.wp.fa.WordPressDB;
import org.wp.fa.models.Blog;

import java.util.ArrayList;
import java.util.List;

public class MediaSourceWPImages  {
    private final List<Object> mVerifiedItems = new ArrayList<>();
    private final List<Object> mMediaItems = new ArrayList<>();

    public MediaSourceWPImages() {
    }

//    @Override
    public void gather(Context context) {
        mMediaItems.clear();

        Blog blog = WordPress.getCurrentBlog();

        if (blog != null) {
            Cursor imageCursor = WordPressMediaUtils.getWordPressMediaImages(String.valueOf(blog.getLocalTableBlogId()));

            if (imageCursor != null) {
                addWordPressImagesFromCursor(imageCursor);
                imageCursor.close();
            }
        }
    }

    private Drawable placeholderDrawable(Context context) {
        if (context != null && context.getResources() != null) {
            return context.getResources().getDrawable(R.drawable.media_item_placeholder);
        }

        return null;
    }

    private void addWordPressImagesFromCursor(Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                int attachmentIdColumnIndex = cursor.getColumnIndex(WordPressDB.COLUMN_NAME_MEDIA_ID);
                int fileUrlColumnIndex = cursor.getColumnIndex(WordPressDB.COLUMN_NAME_FILE_URL);
                int filePathColumnIndex = cursor.getColumnIndex(WordPressDB.COLUMN_NAME_FILE_PATH);
                int thumbnailColumnIndex = cursor.getColumnIndex(WordPressDB.COLUMN_NAME_THUMBNAIL_URL);

                String id = "";
                if (attachmentIdColumnIndex != -1) {
                    id = String.valueOf(cursor.getInt(attachmentIdColumnIndex));
                }
                if (fileUrlColumnIndex != -1) {
                    String fileUrl = cursor.getString(fileUrlColumnIndex);

                    if (fileUrl != null) {
                    } else if (filePathColumnIndex != -1) {
                        String filePath = cursor.getString(filePathColumnIndex);

                        if (filePath != null) {
                        }
                    }
                }

                if (thumbnailColumnIndex != -1) {
                    String preview = cursor.getString(thumbnailColumnIndex);

                    if (preview != null) {
//                        newContent.setPreviewSource(Uri.parse(preview));
                    }
                }

                mMediaItems.add(new Object());
            } while (cursor.moveToNext());

            removeDeletedEntries();
        }
    }

    private void removeDeletedEntries() {
        final List<Object> existingItems = new ArrayList<>(mMediaItems);
        final List<Object> failedItems = new ArrayList<>();

        for (final Object mediaItem : existingItems) {

        }
    }
}
