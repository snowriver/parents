package com.tenzhao.common.util;

import java.util.regex.Pattern;

public final class TextUtils {
    private static final String HTML_REGEX = "</?[a-zA-Z][a-zA-Z0-9]*[^<>]*>";

    public static String stripHtml(String text) {
        return Pattern.compile(HTML_REGEX, Pattern.CASE_INSENSITIVE).matcher(text).replaceAll("");
    }

    private TextUtils() {
    }
}
