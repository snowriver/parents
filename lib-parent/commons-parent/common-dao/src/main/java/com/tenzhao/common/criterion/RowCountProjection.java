package com.tenzhao.common.criterion;

import java.util.List;


/**
 * A row count
 *
 * @author Gavin King
 */
public class RowCountProjection extends SimpleProjection {
	private static final List ARGS = java.util.Collections.singletonList( "*" );


	@Override
	public String toString() {
		return "count(*)";
	}


	@Override
	public String[] getColumnAliases(int position, Criteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String[] getColumnAliases(String alias, int position, Criteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}
}
