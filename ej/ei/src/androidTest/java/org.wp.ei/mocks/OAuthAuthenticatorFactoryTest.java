package org.wp.ei.mocks;

import android.content.Context;

import org.wp.ei.networking.OAuthAuthenticator;
import org.wp.ei.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.ei.util.AppLog;
import org.wp.ei.util.AppLog.T;

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
