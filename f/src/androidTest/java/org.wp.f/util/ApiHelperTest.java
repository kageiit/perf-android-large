package org.wp.f.util;

import android.content.Context;
import android.os.AsyncTask;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import org.wp.f.FactoryUtils;
import org.wp.f.TestUtils;
import org.wp.f.mocks.RestClientFactoryTest;
import org.wp.f.mocks.XMLRPCFactoryTest;
import org.wp.f.models.Blog;
import org.wp.f.models.Comment;
import org.wp.f.models.CommentStatus;
import org.wp.f.util.AppLog.T;
import org.xmlrpc.f.ApiHelper;
import org.xmlrpc.f.ApiHelper.ErrorType;
import org.xmlrpc.f.ApiHelper.GenericCallback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ApiHelperTest extends InstrumentationTestCase {
    protected Context mTargetContext;

    @Override
    protected void setUp() {
        FactoryUtils.initWithTestFactories();

        // Clean application state
        mTargetContext = new RenamingDelegatingContext(getInstrumentation().getTargetContext(), "test_");
        TestUtils.clearApplicationState(mTargetContext);

        // Init contexts
        XMLRPCFactoryTest.sContext = getInstrumentation().getContext();
        RestClientFactoryTest.sContext = getInstrumentation().getContext();
        AppLog.v(T.TESTS, "Contexts set");

        // Set mode to Customizable
        XMLRPCFactoryTest.sMode = XMLRPCFactoryTest.Mode.CUSTOMIZABLE_JSON;
        RestClientFactoryTest.sMode = RestClientFactoryTest.Mode.CUSTOMIZABLE;
        AppLog.v(T.TESTS, "Modes set to customizable");
    }

    @Override
    protected void tearDown() {
        FactoryUtils.clearFactories();
    }

    private void countDownAfterOtherAsyncTasks(final CountDownLatch countDownLatch) {
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                countDownLatch.countDown();
            }
        });
    }

    // This test failed before #773 was fixed
    public void testRefreshBlogContent() throws InterruptedException {
        XMLRPCFactoryTest.setPrefixAllInstances("malformed-software-version");
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Blog dummyBlog = new Blog("", "", "");
        new ApiHelper.RefreshBlogContentTask(dummyBlog, new GenericCallback() {
            @Override
            public void onSuccess() {
                assertTrue(true);
                // countDown() after the serially invoked (nested) AsyncTask in RefreshBlogContentTask.
                countDownAfterOtherAsyncTasks(countDownLatch);
            }

            @Override
            public void onFailure(ErrorType errorType, String errorMessage, Throwable throwable) {
                assertTrue(false);
                // countDown() after the serially invoked (nested) AsyncTask in RefreshBlogContentTask.
                countDownAfterOtherAsyncTasks(countDownLatch);
            }
        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, false);
        countDownLatch.await(5000, TimeUnit.SECONDS);
    }

    // This test failed before #799 was fixed
    public void testRefreshBlogContentEmptyResponse() throws InterruptedException {
        XMLRPCFactoryTest.setPrefixAllInstances("empty");
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Blog dummyBlog = new Blog("", "", "");
        new ApiHelper.RefreshBlogContentTask(dummyBlog, new GenericCallback() {
            @Override
            public void onSuccess() {
                assertTrue(false);
                // countDown() after the serially invoked (nested) AsyncTask in RefreshBlogContentTask.
                countDownAfterOtherAsyncTasks(countDownLatch);
            }

            @Override
            public void onFailure(ErrorType errorType, String errorMessage, Throwable throwable) {
                assertTrue(true);
                // countDown() after the serially invoked (nested) AsyncTask in RefreshBlogContentTask.
                countDownAfterOtherAsyncTasks(countDownLatch);
            }
        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, false);
        countDownLatch.await(5000, TimeUnit.SECONDS);
    }

    public void testSpamSpammedComment() {
        XMLRPCFactoryTest.sMode = XMLRPCFactoryTest.Mode.CUSTOMIZABLE_XML;
        XMLRPCFactoryTest.setPrefixAllInstances("comment-already-spammed");
        Blog dummyBlog = new Blog("", "", "");
        // contrstust a dummy (albeit invalid) comment object to pass the comment id
        Comment comment = new Comment(1, 2, null, null, null, null, null, null, null, null);

        assertTrue(ApiHelper.editComment(dummyBlog, comment, CommentStatus.SPAM));
    }

    public void testGetSpammedCommentStatus() {
        XMLRPCFactoryTest.sMode = XMLRPCFactoryTest.Mode.CUSTOMIZABLE_XML;
        XMLRPCFactoryTest.setPrefixAllInstances("comment-already-spammed");
        Blog dummyBlog = new Blog("", "", "");
        // contrstust a dummy (albeit invalid) comment object to pass the comment id
        Comment comment = new Comment(1, 2, null, null, null, null, null, null, null, null);

        assertEquals(CommentStatus.SPAM, ApiHelper.getCommentStatus(dummyBlog, comment));
    }
}
