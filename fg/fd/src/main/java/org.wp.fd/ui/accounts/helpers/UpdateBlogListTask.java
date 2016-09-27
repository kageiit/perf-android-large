package org.wp.fd.ui.accounts.helpers;

import android.content.Context;
import android.os.AsyncTask;

import org.wp.fd.ui.accounts.BlogUtils;
import org.wp.fd.ui.accounts.helpers.FetchBlogListAbstract.Callback;
import org.wp.fd.models.AccountHelper;
import org.wp.fd.util.AppLog;
import org.wp.fd.util.AppLog.T;
import org.wp.fd.util.CoreEvents;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;

public class UpdateBlogListTask extends AsyncTask<Void, Void, List<Map<String, Object>>> {
    public static final int GET_BLOG_LIST_TIMEOUT = 30000;
    protected int mErrorMsgId;
    protected Context mContext;
    protected boolean mBlogListChanged;
    protected static List<Map<String, Object>> mUserBlogList;

    public UpdateBlogListTask(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected List<Map<String, Object>> doInBackground(Void... args) {
        final String username = AccountHelper.getDefaultAccount().getUserName();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        FetchBlogListWPCom fetchBlogList = new FetchBlogListWPCom(mContext);
        fetchBlogList.execute(new Callback() {
            @Override
            public void onSuccess(List<Map<String, Object>> userBlogList) {
                mUserBlogList = userBlogList;
                if (mUserBlogList != null) {
                    mBlogListChanged = BlogUtils.syncBlogs(mContext, mUserBlogList, username);
                }
                countDownLatch.countDown();
            }

            @Override
            public void onError(int messageId, boolean twoStepCodeRequired, boolean httpAuthRequired, boolean erroneousSslCertificate,
                                String clientResponse) {
                mErrorMsgId = messageId;
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await(GET_BLOG_LIST_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            AppLog.e(T.NUX, e);
        }
        return mUserBlogList;
    }

    public static class GenericUpdateBlogListTask extends UpdateBlogListTask {
        public GenericUpdateBlogListTask(Context context) {
            super(context);
        }

        @Override
        protected void onPostExecute(final List<Map<String, Object>> userBlogList) {
            if (mBlogListChanged) {
                EventBus.getDefault().post(new CoreEvents.BlogListChanged());
            }
        }
    }
}
