package com.tenzhao.common.test;

import javax.naming.NamingException;

import org.springframework.mock.jndi.SimpleNamingContextBuilder;

public class JndiMockUtils {
    private static SimpleNamingContextBuilder contextBuilder = null;

    static {
        try {
            contextBuilder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
        } catch (NamingException e) {
            throw new RuntimeException("", e);
        }
    }

    public static void bind(String jndiName, Object object) {
        contextBuilder.bind(jndiName, object);
    }
}
