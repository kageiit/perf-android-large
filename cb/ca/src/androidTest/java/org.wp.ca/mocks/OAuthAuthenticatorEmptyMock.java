package org.wp.ca.mocks;

import org.wp.ca.models.AccountHelper;
import org.wp.ca.networking.AuthenticatorRequest;
import org.wp.ca.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
