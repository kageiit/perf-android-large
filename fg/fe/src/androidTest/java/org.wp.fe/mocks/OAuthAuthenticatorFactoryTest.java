package org.wp.fe.mocks;

import android.content.Context;

import org.wp.fe.networking.OAuthAuthenticator;
import org.wp.fe.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.fe.util.AppLog;
import org.wp.fe.util.AppLog.T;

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
