package org.xmlrpc.j;

import java.net.URI;

public class XMLRPCFactoryDefault implements XMLRPCFactoryAbstract {
    public XMLRPCClientInterface make(URI uri, String httpUser, String httpPassword) {
        return new XMLRPCClient(uri, httpUser, httpPassword);
    }
}
