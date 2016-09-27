package org.wp.db;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import org.wp.db.mocks.RestClientFactoryTest;
import org.wp.db.mocks.XMLRPCFactoryTest;
import org.wp.db.util.AppLog;

public class DefaultMocksInstrumentationTestCase extends InstrumentationTestCase {
    protected Context mTargetContext;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        FactoryUtils.initWithTestFactories();

        mTargetContext = new RenamingDelegatingContext(getInstrumentation().getTargetContext(), "test_");
        TestUtils.clearApplicationState(mTargetContext);

        // Init contexts
        XMLRPCFactoryTest.sContext = getInstrumentation().getContext();
        RestClientFactoryTest.sContext = getInstrumentation().getContext();
        AppLog.v(AppLog.T.TESTS, "Contexts set");

        // Set mode to Customizable
        XMLRPCFactoryTest.sMode = XMLRPCFactoryTest.Mode.CUSTOMIZABLE_XML;
        RestClientFactoryTest.sMode = RestClientFactoryTest.Mode.CUSTOMIZABLE;
        AppLog.v(AppLog.T.TESTS, "Modes set to customizable");

        // Set default variant
        RestClientFactoryTest.setPrefixAllInstances("default");
        XMLRPCFactoryTest.setPrefixAllInstances("default");
    }

    @Override
    protected void tearDown() throws Exception {
        FactoryUtils.clearFactories();
        super.tearDown();
    }
}