package org.wp.j.mocks;

import android.content.Context;

import org.wp.j.networking.OAuthAuthenticator;
import org.wp.j.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.j.util.AppLog;
import org.wp.j.util.AppLog.T;

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
