package org.wp.ag.mocks;

import android.content.Context;

import org.wp.ag.networking.OAuthAuthenticator;
import org.wp.ag.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.ag.util.AppLog;
import org.wp.ag.util.AppLog.T;

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
