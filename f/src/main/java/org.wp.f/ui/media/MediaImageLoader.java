package org.wp.f.ui.media;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.wp.f.WordPress;
import org.wp.f.models.Blog;
import org.wp.f.util.AppLog;
import org.wp.f.util.VolleyUtils;

/**
 * provides the ImageLoader and backing RequestQueue for media image requests - necessary because
 * images in protected blogs need to be authenticated, which requires a separate RequestQueue
 */
class MediaImageLoader {
    private MediaImageLoader() {
        throw new AssertionError();
    }

    static ImageLoader getInstance() {
        return getInstance(WordPress.getCurrentBlog());
    }

    static ImageLoader getInstance(Blog blog) {
        if (blog != null && VolleyUtils.isCustomHTTPClientStackNeeded(blog)) {
            // use ImageLoader with authenticating request queue for protected blogs
            AppLog.d(AppLog.T.MEDIA, "using custom imageLoader");
            Context context = WordPress.getContext();
            RequestQueue authRequestQueue = Volley.newRequestQueue(context, VolleyUtils.getHTTPClientStack(context, blog));
            ImageLoader imageLoader = new ImageLoader(authRequestQueue, WordPress.getBitmapCache());
            imageLoader.setBatchedResponseDelay(0);
            return imageLoader;
        } else {
            // use default ImageLoader for all others
            AppLog.d(AppLog.T.MEDIA, "using default imageLoader");
            return WordPress.imageLoader;
        }
    }
}
