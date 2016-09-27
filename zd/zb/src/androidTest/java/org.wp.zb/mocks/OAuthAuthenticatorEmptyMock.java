package org.wp.zb.mocks;

import org.wp.zb.models.AccountHelper;
import org.wp.zb.networking.AuthenticatorRequest;
import org.wp.zb.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
