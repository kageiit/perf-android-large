package org.wp.fa.mocks;

import android.content.Context;

import org.wp.fa.networking.OAuthAuthenticator;
import org.wp.fa.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.fa.util.AppLog;
import org.wp.fa.util.AppLog.T;

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
