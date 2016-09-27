package org.wp.ca.mocks;

import android.content.Context;

import org.wp.ca.networking.OAuthAuthenticator;
import org.wp.ca.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.ca.util.AppLog;
import org.wp.ca.util.AppLog.T;

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
