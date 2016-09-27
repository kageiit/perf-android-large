package org.wp.fd.mocks;

import org.wp.fd.models.AccountHelper;
import org.wp.fd.networking.AuthenticatorRequest;
import org.wp.fd.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
