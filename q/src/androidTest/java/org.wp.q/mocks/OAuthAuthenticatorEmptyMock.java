package org.wp.q.mocks;

import org.wp.q.models.AccountHelper;
import org.wp.q.networking.AuthenticatorRequest;
import org.wp.q.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
