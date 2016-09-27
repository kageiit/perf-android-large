package org.wp.h.mocks;

import org.wp.h.models.AccountHelper;
import org.wp.h.networking.AuthenticatorRequest;
import org.wp.h.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
