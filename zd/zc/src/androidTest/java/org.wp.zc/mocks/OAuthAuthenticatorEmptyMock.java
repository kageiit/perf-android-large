package org.wp.zc.mocks;

import org.wp.zc.models.AccountHelper;
import org.wp.zc.networking.AuthenticatorRequest;
import org.wp.zc.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
