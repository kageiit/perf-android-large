package org.wp.i.mocks;

import org.wp.i.models.AccountHelper;
import org.wp.i.networking.AuthenticatorRequest;
import org.wp.i.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
