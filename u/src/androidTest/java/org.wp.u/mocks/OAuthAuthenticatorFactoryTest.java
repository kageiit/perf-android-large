package org.wp.u.mocks;

import android.content.Context;

import org.wp.u.networking.OAuthAuthenticator;
import org.wp.u.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.u.util.AppLog;
import org.wp.u.util.AppLog.T;

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
