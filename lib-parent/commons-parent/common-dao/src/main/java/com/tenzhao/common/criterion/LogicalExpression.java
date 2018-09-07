package com.tenzhao.common.criterion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Superclass of binary logical expressions
 *
 */
public class LogicalExpression implements Criterion {
	private final String op;
	private final List<Criterion> criteria = new ArrayList<Criterion>();
	protected LogicalExpression(String op,Criterion...criterions) {
		Collections.addAll( criteria, criterions );
		this.op = op;
	}

	public String getOp() {
		return op;
	}

	@Override
	public String toString() {
		return '(' + join( ' ' + getOp() + ' ', criteria.iterator() ) + ')';
	}
	public static String join(String seperator, Iterator<Criterion> objects) {
		StringBuilder buf = new StringBuilder();
		if ( objects.hasNext() ) {
			buf.append( objects.next() );
		}
		while ( objects.hasNext() ) {
			buf.append( seperator ).append( objects.next() );
		}
		return buf.toString();
	}
	
	@Override
	public String getKeyword() {
		return "logical";
	}
	@Override
	public String getPropertyName() {
		return "";
	}

	@Override
	public Object getValue() {
		return toString();
	}
}
