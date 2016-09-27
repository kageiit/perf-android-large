package org.wp.eg.mocks;

import android.content.Context;

import org.wp.eg.networking.OAuthAuthenticator;
import org.wp.eg.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.eg.util.AppLog;
import org.wp.eg.util.AppLog.T;

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
