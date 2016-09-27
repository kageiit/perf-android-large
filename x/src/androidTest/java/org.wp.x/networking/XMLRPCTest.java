package org.wp.x.networking;

import org.wp.x.DefaultMocksInstrumentationTestCase;
import org.wp.x.mocks.XMLRPCFactoryTest;
import org.xmlrpc.x.ApiHelper.Method;
import org.xmlrpc.x.XMLRPCClientInterface;
import org.xmlrpc.x.XMLRPCFactory;

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
