package org.wp.za.mocks;

import org.wp.za.models.AccountHelper;
import org.wp.za.networking.AuthenticatorRequest;
import org.wp.za.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
