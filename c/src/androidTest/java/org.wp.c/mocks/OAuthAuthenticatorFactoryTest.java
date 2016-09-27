package org.wp.c.mocks;

import android.content.Context;

import org.wp.c.networking.OAuthAuthenticator;
import org.wp.c.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.c.util.AppLog;
import org.wp.c.util.AppLog.T;

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
