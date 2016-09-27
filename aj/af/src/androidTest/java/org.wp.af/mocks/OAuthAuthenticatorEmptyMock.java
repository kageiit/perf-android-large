package org.wp.af.mocks;

import org.wp.af.models.AccountHelper;
import org.wp.af.networking.AuthenticatorRequest;
import org.wp.af.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
