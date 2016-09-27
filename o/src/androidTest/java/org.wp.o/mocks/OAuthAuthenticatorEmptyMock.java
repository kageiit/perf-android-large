package org.wp.o.mocks;

import org.wp.o.models.AccountHelper;
import org.wp.o.networking.AuthenticatorRequest;
import org.wp.o.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
