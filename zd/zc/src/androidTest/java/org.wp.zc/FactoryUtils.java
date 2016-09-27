package org.wp.zc;

import org.wp.zc.mocks.OAuthAuthenticatorFactoryTest;
import org.wp.zc.mocks.RestClientFactoryTest;
import org.wp.zc.mocks.SystemServiceFactoryTest;
import org.wp.zc.mocks.XMLRPCFactoryTest;
import org.wp.zc.networking.OAuthAuthenticatorFactory;
import org.wp.zc.networking.RestClientFactory;
import org.wp.zc.util.AppLog;
import org.wp.zc.util.AppLog.T;
import org.wp.zc.util.SystemServiceFactory;
import org.xmlrpc.zc.XMLRPCFactory;

import java.lang.reflect.Field;

public class FactoryUtils {
    public static void clearFactories() {
        // clear factories
        forceFactoryInjection(XMLRPCFactory.class, null);
        forceFactoryInjection(RestClientFactory.class, null);
        forceFactoryInjection(OAuthAuthenticatorFactory.class, null);
        forceFactoryInjection(SystemServiceFactory.class, null);
        AppLog.v(T.TESTS, "Null factories set");
    }

    public static void initWithTestFactories() {
        // create test factories
        forceFactoryInjection(XMLRPCFactory.class, new XMLRPCFactoryTest());
        forceFactoryInjection(RestClientFactory.class, new RestClientFactoryTest());
        forceFactoryInjection(OAuthAuthenticatorFactory.class, new OAuthAuthenticatorFactoryTest());
        forceFactoryInjection(SystemServiceFactory.class, new SystemServiceFactoryTest());
        AppLog.v(T.TESTS, "Mocks factories instantiated");
    }

    private static void forceFactoryInjection(Class klass, Object factory) {
        try {
            Field field = klass.getDeclaredField("sFactory");
            field.setAccessible(true);
            field.set(null, factory);
            AppLog.v(T.TESTS, "Factory " + klass + " injected");
        } catch (Exception e) {
            AppLog.e(T.TESTS, "Can't inject test factory " + klass);
        }
    }
}
