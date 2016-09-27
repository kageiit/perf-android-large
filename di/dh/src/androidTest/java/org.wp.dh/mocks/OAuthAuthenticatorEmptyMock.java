package org.wp.dh.mocks;

import org.wp.dh.models.AccountHelper;
import org.wp.dh.networking.AuthenticatorRequest;
import org.wp.dh.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
