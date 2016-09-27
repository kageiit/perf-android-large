package org.wp.ad.networking;

import org.wp.ad.DefaultMocksInstrumentationTestCase;
import org.wp.ad.mocks.XMLRPCFactoryTest;
import org.xmlrpc.ad.ApiHelper.Method;
import org.xmlrpc.ad.XMLRPCClientInterface;
import org.xmlrpc.ad.XMLRPCFactory;

import java.net.URI;

public class XMLRPCTest extends DefaultMocksInstrumentationTestCase {
    public void testNumberExceptionWithInvalidDouble() throws Exception {
        XMLRPCFactoryTest.setPrefixAllInstances("invalid-double-xmlrpc");
        XMLRPCClientInterface xmlrpcClientInterface = XMLRPCFactory.instantiate(URI.create("http://test.com/ast"), "",
                "");
        try {
            xmlrpcClientInterface.call(Method.GET_MEDIA_LIBRARY, null);
        } catch (NumberFormatException e) {
            return;
        }
        assertTrue("invalid double format should trigger a NumberException", false);
    }

    public void testNumberExceptionWithInvalidInteger() throws Exception {
        XMLRPCFactoryTest.setPrefixAllInstances("invalid-integer-xmlrpc");
        XMLRPCClientInterface xmlrpcClientInterface = XMLRPCFactory.instantiate(URI.create("http://test.com/ast"), "",
                "");
        try {
            xmlrpcClientInterface.call(Method.GET_MEDIA_LIBRARY, null);
        } catch (NumberFormatException e) {
            return;
        }
        assertTrue("invalid double format should trigger a NumberException", false);
    }
}
