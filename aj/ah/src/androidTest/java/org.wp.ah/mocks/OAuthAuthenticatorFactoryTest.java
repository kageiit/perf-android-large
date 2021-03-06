package org.wp.ah.mocks;

import android.content.Context;

import org.wp.ah.networking.OAuthAuthenticator;
import org.wp.ah.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.ah.util.AppLog;
import org.wp.ah.util.AppLog.T;

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
