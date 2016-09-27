package org.wp.z.mocks;

import org.wp.z.models.AccountHelper;
import org.wp.z.networking.AuthenticatorRequest;
import org.wp.z.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
