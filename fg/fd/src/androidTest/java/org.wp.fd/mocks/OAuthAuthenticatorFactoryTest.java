package org.wp.fd.mocks;

import android.content.Context;

import org.wp.fd.networking.OAuthAuthenticator;
import org.wp.fd.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.fd.util.AppLog;
import org.wp.fd.util.AppLog.T;

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
