package org.wp.ef.mocks;

import android.content.Context;

import org.wp.ef.networking.OAuthAuthenticator;
import org.wp.ef.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.ef.util.AppLog;
import org.wp.ef.util.AppLog.T;

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
