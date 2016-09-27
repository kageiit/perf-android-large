package org.wp.l.mocks;

import android.content.Context;

import org.wp.l.networking.OAuthAuthenticator;
import org.wp.l.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.l.util.AppLog;
import org.wp.l.util.AppLog.T;

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
