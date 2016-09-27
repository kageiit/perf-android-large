package org.wp.za.mocks;

import android.content.Context;

import org.xmlrpc.za.XMLRPCClientInterface;

public abstract class XMLRPCClientCustomizableMockAbstract implements XMLRPCClientInterface {
    protected Context mContext;
    protected String mPrefix;

    public void setContextAndPrefix(Context context, String prefix) {
        mContext = context;
        mPrefix = prefix;
    }

    public void setPrefix(String prefix) {
        mPrefix = prefix;
    }

    public void setContext(Context context) {
        mContext = context;
    }
}
