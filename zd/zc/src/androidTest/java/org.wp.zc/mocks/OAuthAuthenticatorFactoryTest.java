package org.wp.zc.mocks;

import android.content.Context;

import org.wp.zc.networking.OAuthAuthenticator;
import org.wp.zc.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.zc.util.AppLog;
import org.wp.zc.util.AppLog.T;

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
