package org.wp.x.mocks;

import android.content.Context;

import org.wp.x.networking.OAuthAuthenticator;
import org.wp.x.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.x.util.AppLog;
import org.wp.x.util.AppLog.T;

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
