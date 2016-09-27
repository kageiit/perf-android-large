package org.wp.b.mocks;

import org.wp.b.models.AccountHelper;
import org.wp.b.networking.AuthenticatorRequest;
import org.wp.b.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
