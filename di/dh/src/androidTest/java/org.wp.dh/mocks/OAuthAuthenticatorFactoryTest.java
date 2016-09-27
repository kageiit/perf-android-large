package org.wp.dh.mocks;

import android.content.Context;

import org.wp.dh.networking.OAuthAuthenticator;
import org.wp.dh.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.dh.util.AppLog;
import org.wp.dh.util.AppLog.T;

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
