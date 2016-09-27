package org.wp.ah.mocks;

import org.wp.ah.models.AccountHelper;
import org.wp.ah.networking.AuthenticatorRequest;
import org.wp.ah.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
