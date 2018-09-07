package com.tenzhao.common.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BaseDataObject implements Serializable {
    private static final long serialVersionUID = 1L;

    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
