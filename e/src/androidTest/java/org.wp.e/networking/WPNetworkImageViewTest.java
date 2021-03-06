package org.wp.e.networking;

import android.os.Handler;
import android.test.InstrumentationTestCase;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import org.wp.e.WordPress;
import org.wp.e.util.AppLog;
import org.wp.e.util.AppLog.T;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WPNetworkImageViewTest extends InstrumentationTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    // https://github.com/wordpress-mobile/WordPress-Android/issues/1549
    public void testVolleyImageLoaderGetNullHost() throws InterruptedException {
        Handler mainLooperHandler = new Handler(WordPress.getContext().getMainLooper());
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final boolean success[] = new boolean[1];
        Runnable getImage = new Runnable() {
            @Override
            public void run() {
                try {
                    // This call crash on old volley versions
                    WordPress.imageLoader.get("http;///hello/null/host", new ImageListener() {
                        @Override
                        public void onResponse(ImageContainer imageContainer, boolean b) {}

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {}
                    }, 1, 1);
                    success[0] = true;
                } catch (Exception e) {
                    AppLog.e(T.TESTS, e);
                    success[0] = false;
                } finally {
                    countDownLatch.countDown();
                }
            }
        };
        mainLooperHandler.post(getImage);
        countDownLatch.await(1, TimeUnit.SECONDS);
        assertTrue("Invalid Volley library version", success[0]);
    }
}