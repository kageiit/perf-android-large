package org.wp.fb.mocks;

import android.content.Context;

import org.wp.fb.networking.OAuthAuthenticator;
import org.wp.fb.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.fb.util.AppLog;
import org.wp.fb.util.AppLog.T;

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
