package org.wp.o.util;

import android.content.Context;

public interface SystemServiceFactoryAbstract {
    public Object get(Context context, String name);
}
