
package com.tenzhao.common.criterion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * @author chenxj
 *
 */
public class Criteria implements ICriteria,Serializable {

	private static final long serialVersionUID = 1L;
	private List<Criterion> criteria = new ArrayList<Criterion>();
	
	private Criteria() {}
	public List<Criterion> getCriteria() {
		return criteria;
	}
	@Override
	public ICriteria add(Criterion restrictions) {
		if(!Objects.isNull(restrictions) && !Objects.isNull(restrictions.getValue())) {
			criteria.add( restrictions);
		}
		return this;
	}
	
	protected static Criteria getInstance(){
		return new Criteria();
	}
}
