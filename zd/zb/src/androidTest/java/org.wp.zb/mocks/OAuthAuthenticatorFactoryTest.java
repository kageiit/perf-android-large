package org.wp.zb.mocks;

import android.content.Context;

import org.wp.zb.networking.OAuthAuthenticator;
import org.wp.zb.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.zb.util.AppLog;
import org.wp.zb.util.AppLog.T;

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
