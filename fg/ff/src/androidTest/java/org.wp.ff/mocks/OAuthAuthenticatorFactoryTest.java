package org.wp.ff.mocks;

import android.content.Context;

import org.wp.ff.networking.OAuthAuthenticator;
import org.wp.ff.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.ff.util.AppLog;
import org.wp.ff.util.AppLog.T;

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
