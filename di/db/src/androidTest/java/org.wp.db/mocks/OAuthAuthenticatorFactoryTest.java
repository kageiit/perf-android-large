package org.wp.db.mocks;

import android.content.Context;

import org.wp.db.networking.OAuthAuthenticator;
import org.wp.db.networking.OAuthAuthenticatorFactoryAbstract;
import org.wp.db.util.AppLog;
import org.wp.db.util.AppLog.T;

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
