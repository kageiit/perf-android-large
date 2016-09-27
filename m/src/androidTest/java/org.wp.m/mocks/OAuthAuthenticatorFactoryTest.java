package org.wp.m.mocks;

import android.content.Context;

import org.wp.m.networking.OAuthAuthenticator;
import org.wp.m.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.m.util.AppLog;
import org.wp.m.util.AppLog.T;

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
