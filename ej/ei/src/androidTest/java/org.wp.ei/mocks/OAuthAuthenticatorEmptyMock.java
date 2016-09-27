package org.wp.ei.mocks;

import org.wp.ei.models.AccountHelper;
import org.wp.ei.networking.AuthenticatorRequest;
import org.wp.ei.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
