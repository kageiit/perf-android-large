package org.wp.l.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;


public class WPEditText extends EditText {
    public WPEditText(Context context) {
        super(context, null);
        TypefaceCache.setCustomTypeface(context, this, null);
    }

    public WPEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypefaceCache.setCustomTypeface(context, this, attrs);
    }

    public WPEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypefaceCache.setCustomTypeface(context, this, attrs);
    }
}
