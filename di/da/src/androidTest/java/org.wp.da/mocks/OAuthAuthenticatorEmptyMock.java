package org.wp.da.mocks;

import org.wp.da.models.AccountHelper;
import org.wp.da.networking.AuthenticatorRequest;
import org.wp.da.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
