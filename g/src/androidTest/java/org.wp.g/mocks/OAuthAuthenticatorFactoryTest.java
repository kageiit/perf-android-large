package org.wp.g.mocks;

import android.content.Context;

import org.wp.g.networking.OAuthAuthenticator;
import org.wp.g.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.g.util.AppLog;
import org.wp.g.util.AppLog.T;

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
