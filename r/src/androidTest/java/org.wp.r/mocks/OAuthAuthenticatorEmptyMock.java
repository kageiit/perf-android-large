package org.wp.r.mocks;

import org.wp.r.models.AccountHelper;
import org.wp.r.networking.AuthenticatorRequest;
import org.wp.r.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
