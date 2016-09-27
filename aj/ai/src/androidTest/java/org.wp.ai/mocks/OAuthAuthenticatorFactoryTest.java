package org.wp.ai.mocks;

import android.content.Context;

import org.wp.ai.networking.OAuthAuthenticator;
import org.wp.ai.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.ai.util.AppLog;
import org.wp.ai.util.AppLog.T;

public class OAuthAuthenticatorFactoryTest implements OAuthAuthenticatorFactoryAbstract {
    public enum Mode {EMPTY}

    public static Mode sMode = Mode.EMPTY;
    public static Context sContext;

    public OAuthAuthenticator make() {
        switch (sMode) {
            case EMPTY:
            default:
                AppLog.v(T.TESTS, "make: OAuthAuthenticatorEmptyMock");
                return new OAuthAuthenticatorEmptyMock();
        }
    }
}
