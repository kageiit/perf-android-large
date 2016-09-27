package org.wp.f.mocks;

import android.content.Context;

import org.wp.f.networking.OAuthAuthenticator;
import org.wp.f.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.f.util.AppLog;
import org.wp.f.util.AppLog.T;

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
