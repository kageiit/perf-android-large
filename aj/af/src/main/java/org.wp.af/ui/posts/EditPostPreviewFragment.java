package org.wp.af.ui.posts;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.TextView;
import org.wp.af.R;
import org.wp.af.models.Post;
import org.wp.af.util.StringUtils;
import org.wp.af.util.WPHtml;

public class EditPostPreviewFragment extends Fragment {
    // TODO: remove mActivity and rely on getActivity()
//    private EditPostActivity mActivity;
    private WebView mWebView;
    private TextView mTextView;
    private LoadPostPreviewTask mLoadTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        mActivity = (EditPostActivity)getActivity();

        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.edit_post_preview_fragment, container, false);
        mWebView = (WebView) rootView.findViewById(R.id.post_preview_webview);
        mTextView = (TextView) rootView.findViewById(R.id.post_preview_textview);
        mTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                mTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadPost();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mLoadTask != null) {
            mLoadTask.cancel(true);
            mLoadTask = null;
        }
    }

    public void loadPost() {
        if (mLoadTask == null) {
            mLoadTask = new LoadPostPreviewTask();
            mLoadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    // Load post content in the background
    private class LoadPostPreviewTask extends AsyncTask<Void, Void, Spanned> {
        @Override
        protected Spanned doInBackground(Void... params) {
            Spanned contentSpannable;


            Post post = null;

            String postTitle = "<h1>" + post.getTitle() + "</h1>";
            String postContent = postTitle + post.getDescription() + "\n\n" + post.getMoreText();

            if (post.isLocalDraft()) {
                contentSpannable = WPHtml.fromHtml(
                        postContent.replaceAll("\uFFFC", ""),
                        null,
                        post,
                        Math.min(mTextView.getWidth(), mTextView.getHeight())
                );
            } else {
                String htmlText = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"webview.css\" /></head><body><div id=\"container\">%s</div></body></html>";
                htmlText = String.format(htmlText, StringUtils.addPTags(postContent));
                contentSpannable = new SpannableString(htmlText);
            }

            return contentSpannable;
        }

        @Override
        protected void onPostExecute(Spanned spanned) {

            mTextView.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
            mTextView.setText(spanned);


            mLoadTask = null;
        }
    }
}
