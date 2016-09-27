package org.xmlrpc.fd;

import java.net.URI;

public interface XMLRPCFactoryAbstract {
    public XMLRPCClientInterface make(URI uri, String httpUser, String httpPassword);
}