package org.wp.o.mocks;

import android.content.Context;

import org.wp.o.networking.OAuthAuthenticator;
import org.wp.o.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.o.util.AppLog;
import org.wp.o.util.AppLog.T;

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
