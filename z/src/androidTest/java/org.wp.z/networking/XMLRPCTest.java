package org.wp.z.networking;

import org.wp.z.DefaultMocksInstrumentationTestCase;
import org.wp.z.mocks.XMLRPCFactoryTest;
import org.xmlrpc.z.ApiHelper.Method;
import org.xmlrpc.z.XMLRPCClientInterface;
import org.xmlrpc.z.XMLRPCFactory;

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
