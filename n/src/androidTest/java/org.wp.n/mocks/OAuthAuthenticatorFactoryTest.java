package org.wp.n.mocks;

import android.content.Context;

import org.wp.n.networking.OAuthAuthenticator;
import org.wp.n.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.n.util.AppLog;
import org.wp.n.util.AppLog.T;

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
