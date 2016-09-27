package org.wp.s.mocks;

import org.wp.s.models.AccountHelper;
import org.wp.s.networking.AuthenticatorRequest;
import org.wp.s.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
