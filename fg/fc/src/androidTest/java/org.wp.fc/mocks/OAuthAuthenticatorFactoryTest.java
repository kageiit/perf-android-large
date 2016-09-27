package org.wp.fc.mocks;

import android.content.Context;

import org.wp.fc.networking.OAuthAuthenticator;
import org.wp.fc.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.fc.util.AppLog;
import org.wp.fc.util.AppLog.T;

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
