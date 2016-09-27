package org.wp.fc.mocks;

import org.wp.fc.models.AccountHelper;
import org.wp.fc.networking.AuthenticatorRequest;
import org.wp.fc.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
