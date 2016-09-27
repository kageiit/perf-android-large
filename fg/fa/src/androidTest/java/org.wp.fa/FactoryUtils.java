package org.wp.fa;

import org.wp.fa.mocks.OAuthAuthenticatorFactoryTest;
import org.wp.fa.mocks.RestClientFactoryTest;
import org.wp.fa.mocks.SystemServiceFactoryTest;
import org.wp.fa.mocks.XMLRPCFactoryTest;
import org.wp.fa.networking.OAuthAuthenticatorFactory;
import org.wp.fa.networking.RestClientFactory;
import org.wp.fa.util.AppLog;
import org.wp.fa.util.AppLog.T;
import org.wp.fa.util.SystemServiceFactory;
import org.xmlrpc.fa.XMLRPCFactory;

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
