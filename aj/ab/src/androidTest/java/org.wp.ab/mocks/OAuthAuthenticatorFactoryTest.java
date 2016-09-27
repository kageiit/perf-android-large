package org.wp.ab.mocks;

import android.content.Context;

import org.wp.ab.networking.OAuthAuthenticator;
import org.wp.ab.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.ab.util.AppLog;
import org.wp.ab.util.AppLog.T;

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
