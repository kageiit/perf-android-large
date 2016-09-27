package org.wp.ah.ui.notifications;

import android.test.AndroidTestCase;
import android.text.SpannableStringBuilder;

import org.wp.ah.ui.notifications.utils.NotificationsUtils;

public class NotificationsUtilsTest extends AndroidTestCase {
    public void testSpannableHasCharacterAtIndex() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("This is only a test.");

        assertTrue(NotificationsUtils.spannableHasCharacterAtIndex(spannableStringBuilder, 's', 3));
        assertFalse(NotificationsUtils.spannableHasCharacterAtIndex(spannableStringBuilder, 's', 4));

        // Test with bogus params
        assertFalse(NotificationsUtils.spannableHasCharacterAtIndex(null, 'b', -1));
    }

}
