package org.wp.ac.mocks;

import android.content.Context;

import org.wp.ac.networking.OAuthAuthenticator;
import org.wp.ac.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.ac.util.AppLog;
import org.wp.ac.util.AppLog.T;

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
