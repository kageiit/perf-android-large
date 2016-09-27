package org.wp.af.mocks;

import android.content.Context;

import org.wp.af.networking.OAuthAuthenticator;
import org.wp.af.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.af.util.AppLog;
import org.wp.af.util.AppLog.T;

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
