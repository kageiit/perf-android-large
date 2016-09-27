package org.wp.l.mocks;

import org.wp.l.models.AccountHelper;
import org.wp.l.networking.AuthenticatorRequest;
import org.wp.l.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
