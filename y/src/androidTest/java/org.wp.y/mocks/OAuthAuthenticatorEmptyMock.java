package org.wp.y.mocks;

import org.wp.y.models.AccountHelper;
import org.wp.y.networking.AuthenticatorRequest;
import org.wp.y.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
