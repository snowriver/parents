package com.tenzhao.common.util;

import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

public final class RandomUtils {
	private static final Random RANDOM = new Random(); 
    public static String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getRandomUUID(boolean dash) {
        return dash ? getRandomUUID() : getRandomUUID().replace("-", "");
    }
    /**方法来自Struts TokenHelper类*/
    public static String generateGUID() {  
		return new BigInteger(165, RANDOM).toString(36);  
	}
    public static String getRandomString(String acceptChars, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(getRandomCharacter(acceptChars));
        }
        return sb.toString();
    }

    public static char getRandomCharacter(String acceptChars) {
        int index = new Random().nextInt(acceptChars.length());
        return acceptChars.charAt(index);
    }

    private RandomUtils() {
    }
}
