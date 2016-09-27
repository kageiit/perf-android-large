package org.wp.p.mocks;

import org.wp.p.models.AccountHelper;
import org.wp.p.networking.AuthenticatorRequest;
import org.wp.p.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
