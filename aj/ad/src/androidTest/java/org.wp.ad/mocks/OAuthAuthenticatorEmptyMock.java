package org.wp.ad.mocks;

import org.wp.ad.models.AccountHelper;
import org.wp.ad.networking.AuthenticatorRequest;
import org.wp.ad.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
