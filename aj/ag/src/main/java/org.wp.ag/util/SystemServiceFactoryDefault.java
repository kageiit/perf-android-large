package org.wp.ag.util;

import android.content.Context;

public class SystemServiceFactoryDefault implements SystemServiceFactoryAbstract {
    public Object get(Context context, String name) {
        return context.getSystemService(name);
    }
}