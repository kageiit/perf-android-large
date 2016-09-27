package org.wp.eg.mocks;

import org.wp.eg.models.AccountHelper;
import org.wp.eg.networking.AuthenticatorRequest;
import org.wp.eg.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
