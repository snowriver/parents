package com.tenzhao.common.criterion;

public interface EnhancedProjection extends Projection{
	public String[] getColumnAliases(int position, Criteria criteria);

	public String[] getColumnAliases(String alias, int position, Criteria criteria);
}
