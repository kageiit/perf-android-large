package org.wp.b.mocks;

import android.content.Context;

import org.wp.b.networking.OAuthAuthenticator;
import org.wp.b.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.b.util.AppLog;
import org.wp.b.util.AppLog.T;

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
