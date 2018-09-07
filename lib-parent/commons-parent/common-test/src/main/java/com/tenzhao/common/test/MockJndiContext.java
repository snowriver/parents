package com.tenzhao.common.test;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

import org.springframework.beans.factory.InitializingBean;

public class MockJndiContext implements InitializingBean {
    private Map<String, Object> jndiObjects = new HashMap<String, Object>();

    public void afterPropertiesSet() throws IllegalArgumentException, NamingException {
        if (jndiObjects != null) {
            for (String key : jndiObjects.keySet()) {
                JndiMockUtils.bind(key, jndiObjects.get(key));
            }
        }
    }

    public Map<String, Object> getJndiObjects() {
        return jndiObjects;
    }

    public void setJndiObjects(Map<String, Object> jndiObjects) {
        this.jndiObjects = jndiObjects;
    }
}
