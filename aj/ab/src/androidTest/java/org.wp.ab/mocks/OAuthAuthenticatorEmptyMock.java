package org.wp.ab.mocks;

import org.wp.ab.models.AccountHelper;
import org.wp.ab.networking.AuthenticatorRequest;
import org.wp.ab.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
