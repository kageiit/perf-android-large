package org.wp.ae.mocks;

import org.wp.ae.models.AccountHelper;
import org.wp.ae.networking.AuthenticatorRequest;
import org.wp.ae.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
