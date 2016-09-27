package org.wp.fe.mocks;

import org.wp.fe.models.AccountHelper;
import org.wp.fe.networking.AuthenticatorRequest;
import org.wp.fe.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
