package org.wp.ca.util;

public class CrashlyticsUtils {
    final private static String EXCEPTION_KEY = "exception";
    final private static String TAG_KEY = "tag";
    final private static String MESSAGE_KEY = "message";
    public enum ExceptionType {USUAL, SPECIFIC}
    public enum ExtraKey {IMAGE_ANGLE, IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_RESIZE_SCALE, NOTE_HTMLDATA, ENTERED_URL}

    public static void logException(Throwable tr, ExceptionType exceptionType, AppLog.T tag, String message) {

    }

    public static void logException(Throwable tr, ExceptionType exceptionType, AppLog.T tag) {
        logException(tr, exceptionType, tag, null);
    }

    public static void logException(Throwable tr, ExceptionType exceptionType) {
        logException(tr, exceptionType, null, null);
    }

    // Utility functions to force us to use and reuse a limited set of keys

    public static void setInt(ExtraKey key, int value) {

    }

    public static void setFloat(ExtraKey key, float value) {

    }

    public static void setString(ExtraKey key, String value) {

    }

    public static void setBool(ExtraKey key, boolean value) {
    }
}
