package org.wp.y.mocks;

import android.content.Context;

import org.wp.y.networking.OAuthAuthenticator;
import org.wp.y.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.y.util.AppLog;
import org.wp.y.util.AppLog.T;

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
