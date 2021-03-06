package org.wp.za.mocks;

import android.content.Context;

import org.wp.za.networking.OAuthAuthenticator;
import org.wp.za.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.za.util.AppLog;
import org.wp.za.util.AppLog.T;

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
