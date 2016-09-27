package org.wp.ac.mocks;

import org.wp.ac.models.AccountHelper;
import org.wp.ac.networking.AuthenticatorRequest;
import org.wp.ac.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
