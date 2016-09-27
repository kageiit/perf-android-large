package org.wp.ag.mocks;

import org.wp.ag.models.AccountHelper;
import org.wp.ag.networking.AuthenticatorRequest;
import org.wp.ag.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
