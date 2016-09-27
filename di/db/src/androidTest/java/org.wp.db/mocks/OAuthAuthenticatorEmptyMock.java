package org.wp.db.mocks;

import org.wp.db.models.AccountHelper;
import org.wp.db.networking.AuthenticatorRequest;
import org.wp.db.networking.OAuthAuthenticator;

public class OAuthAuthenticatorEmptyMock extends OAuthAuthenticator {
    public void authenticate(AuthenticatorRequest request) {
        AccountHelper.getDefaultAccount().setAccessToken("dead-parrot");
    }
}
