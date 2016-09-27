package org.wp.i.mocks;

import android.content.Context;

import org.wp.i.networking.OAuthAuthenticator;
import org.wp.i.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.i.util.AppLog;
import org.wp.i.util.AppLog.T;

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
