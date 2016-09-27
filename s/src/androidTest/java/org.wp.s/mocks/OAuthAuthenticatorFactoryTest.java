package org.wp.s.mocks;

import android.content.Context;

import org.wp.s.networking.OAuthAuthenticator;
import org.wp.s.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.s.util.AppLog;
import org.wp.s.util.AppLog.T;

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
