package org.wp.ff.mocks;

import org.wp.ff.models.AccountHelper;
import org.wp.ff.networking.AuthenticatorRequest;
import org.wp.ff.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
