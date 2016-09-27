package org.wp.m.mocks;

import org.wp.m.models.AccountHelper;
import org.wp.m.networking.AuthenticatorRequest;
import org.wp.m.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
