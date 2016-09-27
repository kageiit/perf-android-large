package org.wp.ae.mocks;

import android.content.Context;

import org.wp.ae.networking.OAuthAuthenticator;
import org.wp.ae.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.ae.util.AppLog;
import org.wp.ae.util.AppLog.T;

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
