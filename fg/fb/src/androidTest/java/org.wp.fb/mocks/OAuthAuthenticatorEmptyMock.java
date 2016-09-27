package org.wp.fb.mocks;

import org.wp.fb.models.AccountHelper;
import org.wp.fb.networking.AuthenticatorRequest;
import org.wp.fb.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
