package org.wp.ef.mocks;

import org.wp.ef.models.AccountHelper;
import org.wp.ef.networking.AuthenticatorRequest;
import org.wp.ef.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
