package org.wp.e.mocks;

import android.content.Context;

import org.wp.e.networking.OAuthAuthenticator;
import org.wp.e.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.e.util.AppLog;
import org.wp.e.util.AppLog.T;

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
