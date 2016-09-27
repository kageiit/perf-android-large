package org.wp.j.mocks;

import org.wp.j.models.AccountHelper;
import org.wp.j.networking.AuthenticatorRequest;
import org.wp.j.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
