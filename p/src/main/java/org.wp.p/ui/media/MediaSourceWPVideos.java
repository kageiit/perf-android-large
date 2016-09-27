package org.wp.p.ui.media;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.volley.toolbox.ImageLoader;
import org.wp.p.R;
import org.wp.p.WordPress;
import org.wp.p.WordPressDB;
import org.wp.p.models.Blog;

public class MediaSourceWPVideos {
    private static final String VIDEO_PRESS_HOST = "https://videos.files.wordpress.com/";
    private static final String VIDEO_PRESS_THUMBNAIL_APPEND = "_hd.thumbnail.jpg";

//    private OnMediaChange mListener;
//    private List<MediaItem> mMediaItems = new ArrayList<>();

    public MediaSourceWPVideos() {
    }

    public void gather(Context context) {
        Blog blog = WordPress.getCurrentBlog();

        if (blog != null) {
            Cursor videoCursor = WordPressMediaUtils.getWordPressMediaVideos(String.valueOf(blog.getLocalTableBlogId()));

            if (videoCursor != null) {
                addWordPressVideosFromCursor(videoCursor);
                videoCursor.close();
            }
        }
    }

    public View getView(int position, View convertView, ViewGroup parent, LayoutInflater inflater, ImageLoader.ImageCache cache) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.media_item_wp_video, parent, false);
        }

        if (convertView != null) {

            ImageView imageView = (ImageView) convertView.findViewById(R.id.wp_video_view_background);
            if (imageView != null) {
                imageView.setTag(null);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setImageResource(R.drawable.video_thumbnail);
            }
        }

        return convertView;
    }

    private Drawable placeholderDrawable(Context context) {
        if (context != null && context.getResources() != null) {
            return context.getResources().getDrawable(R.drawable.media_item_placeholder);
        }

        return null;
    }

    /**
     * Helper method; removes unnecessary characters from videoPressShortcode cursor value
     *
     * @param cursorEntry the cursor value for the videoPressShortcode key
     * @return the VideoPress code
     */
    private String extractVideoPressCode(String cursorEntry) {
        cursorEntry = cursorEntry.replace("[wpvideo ", "");
        cursorEntry = cursorEntry.substring(0, cursorEntry.length() - 1);

        return cursorEntry;
    }

    private void addWordPressVideosFromCursor(Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                int attachmentIdColumnIndex = cursor.getColumnIndex(WordPressDB.COLUMN_NAME_MEDIA_ID);
                int fileUrlColumnIndex = cursor.getColumnIndex(WordPressDB.COLUMN_NAME_FILE_URL);
                int fileNameColumnIndex = cursor.getColumnIndex(WordPressDB.COLUMN_NAME_FILE_NAME);
                int videoPressColumnIndex = cursor.getColumnIndex(WordPressDB.COLUMN_NAME_VIDEO_PRESS_SHORTCODE);

                String id = "";
                if (attachmentIdColumnIndex != -1) {
                    id = String.valueOf(cursor.getInt(attachmentIdColumnIndex));
                }

            } while (cursor.moveToNext());
        }

    }


}
