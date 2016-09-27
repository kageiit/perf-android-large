package org.wp.ze.mocks;

import org.wp.ze.models.AccountHelper;
import org.wp.ze.networking.AuthenticatorRequest;
import org.wp.ze.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
