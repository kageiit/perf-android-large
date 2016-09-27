package org.wp.fa.mocks;

import org.wp.fa.models.AccountHelper;
import org.wp.fa.networking.AuthenticatorRequest;
import org.wp.fa.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
