package org.wp.x.mocks;

import org.wp.x.models.AccountHelper;
import org.wp.x.networking.AuthenticatorRequest;
import org.wp.x.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
