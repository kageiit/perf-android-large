package org.wp.c.mocks;

import org.wp.c.models.AccountHelper;
import org.wp.c.networking.AuthenticatorRequest;
import org.wp.c.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
