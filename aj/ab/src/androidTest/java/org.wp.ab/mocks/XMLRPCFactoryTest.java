package org.wp.ab.mocks;

import android.content.Context;

import org.wp.ab.util.AppLog;
import org.wp.ab.util.AppLog.T;
import org.xmlrpc.ab.XMLRPCClientInterface;
import org.xmlrpc.ab.XMLRPCFactoryAbstract;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class XMLRPCFactoryTest implements XMLRPCFactoryAbstract {
    public static String sPrefix = "default";
    public static Context sContext;
    public static Mode sMode = Mode.EMPTY;
    public static Set<XMLRPCClientCustomizableMockAbstract> sInstances =
            new HashSet<XMLRPCClientCustomizableMockAbstract>();

    public static void setContextAllInstances(Context context) {
        sContext = context;
        if (sMode != Mode.CUSTOMIZABLE_JSON && sMode != Mode.CUSTOMIZABLE_XML) {
            AppLog.e(T.TESTS, "You tried to change context on a non-customizable XMLRPCClient mock");
        }
        for (XMLRPCClientCustomizableMockAbstract client : sInstances) {
            client.setContext(context);
        }
    }

    public static void setPrefixAllInstances(String prefix) {
        sPrefix = prefix;
        if (sMode != Mode.CUSTOMIZABLE_JSON && sMode != Mode.CUSTOMIZABLE_XML) {
            AppLog.e(T.TESTS, "You tried to change prefix on a non-customizable XMLRPCClient mock");
        }
        for (XMLRPCClientCustomizableMockAbstract client : sInstances) {
            client.setPrefix(prefix);
        }
    }

    public XMLRPCClientInterface make(URI uri, String httpUser, String httpPassword) {
        switch (sMode) {
            case CUSTOMIZABLE_JSON:
                XMLRPCClientCustomizableJSONMock clientJSONMock = new XMLRPCClientCustomizableJSONMock(uri, httpUser,
                        httpPassword);
                if (sContext != null) {
                    clientJSONMock.setContextAndPrefix(sContext, sPrefix);
                } else {
                    AppLog.e(T.TESTS, "You have to set XMLRPCFactoryTest.sContext field before running tests");
                    throw new IllegalStateException();
                }
                AppLog.v(T.TESTS, "make: XMLRPCClientCustomizableJSONMock");
                sInstances.add(clientJSONMock);
                return clientJSONMock;
            case CUSTOMIZABLE_XML:
                XMLRPCClientCustomizableXMLMock clientXMLMock = new XMLRPCClientCustomizableXMLMock(uri, httpUser,
                        httpPassword);
                if (sContext != null) {
                    clientXMLMock.setContextAndPrefix(sContext, sPrefix);
                } else {
                    AppLog.e(T.TESTS, "You have to set XMLRPCFactoryTest.sContext field before running tests");
                    throw new IllegalStateException();
                }
                AppLog.v(T.TESTS, "make: XMLRPCClientCustomizableXMLMock");
                sInstances.add(clientXMLMock);
                return clientXMLMock;
            case EMPTY:
            default:
                AppLog.v(T.TESTS, "make: XMLRPCClientEmptyMock");
                return new XMLRPCClientEmptyMock(uri, httpUser, httpPassword);
        }
    }

    public enum Mode {EMPTY, CUSTOMIZABLE_JSON, CUSTOMIZABLE_XML}
}
