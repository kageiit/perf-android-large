package org.wp.f.mocks;

import org.wp.f.models.AccountHelper;
import org.wp.f.networking.AuthenticatorRequest;
import org.wp.f.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
