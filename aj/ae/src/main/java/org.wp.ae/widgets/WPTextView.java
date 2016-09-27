package org.wp.ae.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;

import org.wp.ae.R;

/**
 * custom TextView used in layouts - enables keeping custom typeface handling in one place (so we
 * avoid having to set the typeface for every single TextView in every single activity)
 */
public class WPTextView extends AppCompatTextView {
    protected boolean mFixWidowWordEnabled;

    public WPTextView(Context context) {
        super(context, null);
        TypefaceCache.setCustomTypeface(context, this, null);
    }

    public WPTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypefaceCache.setCustomTypeface(context, this, attrs);
        readCustomAttrs(context, attrs);
    }

    public WPTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypefaceCache.setCustomTypeface(context, this, attrs);
        readCustomAttrs(context, attrs);
    }

    public void setFixWidowWord(boolean enabled) {
        mFixWidowWordEnabled = enabled;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!mFixWidowWordEnabled) {
            super.setText(text, type);
            return;
        }
        Spannable out;
        int lastSpace = text.toString().lastIndexOf(' ');
        if (lastSpace != -1 && lastSpace < text.length() - 1) {
            // Replace last space character by a non breaking space.
            CharSequence tmpText = replaceCharacter(text, lastSpace, "\u00A0");
            out = new SpannableString(tmpText);
            // Restore spans if text is an instance of Spanned
            if (text instanceof Spanned) {
                TextUtils.copySpansFrom((Spanned) text, 0, text.length(), null, out, 0);
            }
        } else {
            out = new SpannableString(text);
        }
        super.setText(out, type);
    }

    private void readCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WPTextView, 0, 0);
        if (array != null) {
            mFixWidowWordEnabled = array.getBoolean(R.styleable.WPTextView_fixWidowWords, false);
            if (mFixWidowWordEnabled) {
                // Force text update
                setText(getText());
            }
        }
    }

    private CharSequence replaceCharacter(CharSequence source, int charIndex, CharSequence replacement) {
        if (charIndex != -1 && charIndex < source.length() - 1) {
            return TextUtils.concat(source.subSequence(0, charIndex), replacement, source.subSequence(charIndex + 1,
                    source.length()));
        }
        return source;
    }
}
