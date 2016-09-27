package org.wp.c.mocks;

import org.xmlrpc.c.XMLRPCCallback;
import org.xmlrpc.c.XMLRPCClientInterface;
import org.xmlrpc.c.XMLRPCException;

import java.io.File;
import java.net.URI;

public class XMLRPCClientEmptyMock implements XMLRPCClientInterface {
    public XMLRPCClientEmptyMock(URI uri, String httpUser, String httpPassword) {
    }

    public void addQuickPostHeader(String type) {
    }

    public void setAuthorizationHeader(String authToken) {
    }

    public Object call(String method, Object[] params) throws XMLRPCException {
        return null;
    }

    public Object call(String method) throws XMLRPCException {
        return null;
    }

    public Object call(String method, Object[] params, File tempFile) throws XMLRPCException {
        return null;
    }

    public long callAsync(XMLRPCCallback listener, String methodName, Object[] params) {
        return 0;
    }

    public long callAsync(XMLRPCCallback listener, String methodName, Object[] params, File tempFile) {
        return 0;
    }

    public String getResponse() {
        return null;
    }
}
