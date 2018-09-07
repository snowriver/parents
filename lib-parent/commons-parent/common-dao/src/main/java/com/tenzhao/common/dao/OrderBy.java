package com.tenzhao.common.dao;

import com.tenzhao.common.dao.DAOUtils;
import com.tenzhao.common.dao.OrderBy;

public class OrderBy {
    private StringBuilder sb = new StringBuilder();

    public OrderBy add(String column) {
        return add(column, true);
    }

    public OrderBy add(String column, boolean ascend) {
       // DAOUtils.checkColumn(column);

        if (sb.length() > 0) {
            sb.append(", ");
        }
        sb.append(column).append(ascend ? " ASC" : " DESC");

        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }

	public OrderBy asc(String propertyName) {
		if (sb.length() > 0) {
			sb.append(", ");
		}
		sb.append(propertyName).append(" ASC");
		return this;
	}
	
	public OrderBy desc(String propertyName) {
		if (sb.length() > 0) {
			sb.append(", ");
		}
		sb.append(propertyName).append(" DESC");
		return this;
	}
}
