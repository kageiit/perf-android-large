package org.wp.n.mocks;

import org.wp.n.models.AccountHelper;
import org.wp.n.networking.AuthenticatorRequest;
import org.wp.n.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
