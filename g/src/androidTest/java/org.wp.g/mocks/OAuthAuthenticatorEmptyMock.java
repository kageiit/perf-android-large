package org.wp.g.mocks;

import org.wp.g.models.AccountHelper;
import org.wp.g.networking.AuthenticatorRequest;
import org.wp.g.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
