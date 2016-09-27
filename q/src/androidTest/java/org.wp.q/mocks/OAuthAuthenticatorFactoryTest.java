package org.wp.q.mocks;

import android.content.Context;

import org.wp.q.networking.OAuthAuthenticator;
import org.wp.q.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.q.util.AppLog;
import org.wp.q.util.AppLog.T;

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
