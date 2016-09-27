package org.wp.p.mocks;

import android.content.Context;

import org.wp.p.networking.OAuthAuthenticator;
import org.wp.p.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.p.util.AppLog;
import org.wp.p.util.AppLog.T;

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
