package org.wp.eg.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.test.RenamingDelegatingContext;
import android.test.ServiceTestCase;

import org.wp.eg.FactoryUtils;
import org.wp.eg.GCMMessageService;
import org.wp.eg.TestUtils;
import org.wp.eg.models.AccountHelper;

public class GCMIntentServiceTest extends ServiceTestCase<GCMMessageService> {
    protected Context mTargetContext;

    public GCMIntentServiceTest() {
        super(GCMMessageService.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        FactoryUtils.initWithTestFactories();

        mTargetContext = new RenamingDelegatingContext(getContext(), "test_");
        TestUtils.clearApplicationState(mTargetContext);

        setupService();
    }

    @Override
    protected void tearDown() throws Exception {
        FactoryUtils.clearFactories();
        super.tearDown();
    }

    public void testShouldCircularizeNoteIcon() {
        GCMMessageService intentService = new GCMMessageService();

        String type = "c";
        assertTrue(intentService.shouldCircularizeNoteIcon(type));

        assertFalse(intentService.shouldCircularizeNoteIcon(null));

        type = "invalidType";
        assertFalse(intentService.shouldCircularizeNoteIcon(type));
    }

    public void testOnMessageReceived() throws InterruptedException {
        org.wp.eg.models.Account account = AccountHelper.getDefaultAccount();
        account.setAccessToken("secret token");
        account.setUserId(1);
        final Bundle bundle = new Bundle();
        bundle.putString("user", "1");
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getService().onMessageReceived("from", bundle);
                }
            }).start();
        }

        Thread.sleep(10000);
    }
}
