package org.wp.h.util;

import android.content.Context;

public interface SystemServiceFactoryAbstract {
    public Object get(Context context, String name);
}
