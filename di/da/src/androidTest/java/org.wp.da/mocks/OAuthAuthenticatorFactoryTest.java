package org.wp.da.mocks;

import android.content.Context;

import org.wp.da.networking.OAuthAuthenticator;
import org.wp.da.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.da.util.AppLog;
import org.wp.da.util.AppLog.T;

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
