package org.wp.h.mocks;

import android.content.Context;

import org.wp.h.networking.OAuthAuthenticator;
import org.wp.h.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.h.util.AppLog;
import org.wp.h.util.AppLog.T;

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
