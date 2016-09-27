package org.wp.e.mocks;

import org.wp.e.models.AccountHelper;
import org.wp.e.networking.AuthenticatorRequest;
import org.wp.e.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
