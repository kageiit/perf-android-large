package org.wp.ai.mocks;

import org.wp.ai.models.AccountHelper;
import org.wp.ai.networking.AuthenticatorRequest;
import org.wp.ai.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
