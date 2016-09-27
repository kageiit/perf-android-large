package org.wp.u.mocks;

import org.wp.u.models.AccountHelper;
import org.wp.u.networking.AuthenticatorRequest;
import org.wp.u.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
