package com.tenzhao.common.criterion;

public class Restrictions {
	/**
	 * 约定属性等于值；
	 * eg:eq("age",18) => age=18
	 *
	 * @param propertyName 属性名称
	 * @param value 用于比较的值
	 *
	 * @return SimpleExpression
	 * @see SimpleExpression
	 */
	public static SimpleExpression eq(String propertyName, Object value) {
		return new SimpleExpression(propertyName,value,"=");
	}
	
	/**
	 * 约定属性等于值，如果值为null,则属性值使用 is null 替代；
	 * eg: eqOrIsNull("userName","chenxj") => userName='chenxj'
	 * eg: eqOrIsNull("userName",null) => userName is null
	 *
	 * @param propertyName 属性名称
	 * @param value 用于比较的值
	 *
	 * @return The Criterion
	 *
	 * @see #eq
	 * @see #isNull
	 */
	public static Criterion eqOrIsNull(String propertyName, Object value) {
		return value == null
				? isNull( propertyName )
				: eq( propertyName, value );
	}

	/**
	 * 不等于
	 * eg: ne("userName","chenxj") => userName<>'chenxj'
	 *
	 * @param propertyName 
	 * @param value 
	 *
	 * @return SimpleExpression

	 * @see SimpleExpression
	 */
	public static SimpleExpression ne(String propertyName, Object value) {
		return new SimpleExpression( propertyName, value, "<>" );
	}

	/**
	 * 约定属性不等于比较值，如果比较值等于null,使用"is not null"替代
	 * eg: neOrIsNotNull("userName","chenxj") => userName<>'chenxj'
	 * eg: neOrIsNotNull("userName",null) => userName is not null
	 *
	 * @param propertyName 属性名称
	 * @param value 比较值
	 *
	 * @return The Criterion
	 * @see #ne
	 * @see #isNotNull
	 */
	public static Criterion neOrIsNotNull(String propertyName, Object value) {
		return value == null
				? isNotNull( propertyName )
				: ne( propertyName, value );
	}

	/**
	 * Apply a "like" constraint to the named property
	 * 示例： Restrictions.like("name", "'Fritz%'")  结果：name like 'Fritz%'
	 * @param propertyName The name of the property
	 * @param value The value to use in comparison
	 *
	 * @return The Criterion
	 * @see SimpleExpression
	 * 
	 */
	public static LikeExpression like(String propertyName, Object value,LikeMatchMode likeMatchMode) {
		return new LikeExpression( propertyName, value, " like ",likeMatchMode );
	}
	
	/**
	 * not like
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static LikeExpression notLike(String propertyName, Object value,LikeMatchMode likeMode) {
		return new LikeExpression( propertyName, value, " not like ",likeMode );
	}

	/**
	 * Apply a "greater than" constraint to the named property
	 *
	 * @param propertyName The name of the property
	 * @param value The value to use in comparison
	 *
	 * @return The Criterion
	 *
	 * @see SimpleExpression
	 */
	public static SimpleExpression gt(String propertyName, Object value) {
		return new SimpleExpression( propertyName, value, ">" );
	}

	/**
	 * Apply a "less than" constraint to the named property
	 *
	 * @param propertyName The name of the property
	 * @param value The value to use in comparison
	 *
	 * @return The Criterion
	 *
	 * @see SimpleExpression
	 */
	public static SimpleExpression lt(String propertyName, Object value) {
		return new SimpleExpression( propertyName, value, "<" );
	}

	/**
	 * Apply a "less than or equal" constraint to the named property
	 *
	 * @param propertyName The name of the property
	 * @param value The value to use in comparison
	 *
	 * @return The Criterion
	 *
	 * @see SimpleExpression
	 */
	public static SimpleExpression le(String propertyName, Object value) {
		return new SimpleExpression( propertyName, value, "<=" );
	}
	/**
	 * Apply a "greater than or equal" constraint to the named property
	 *
	 * @param propertyName The name of the property
	 * @param value The value to use in comparison
	 *
	 * @return The Criterion
	 *
	 * @see SimpleExpression
	 */
	public static SimpleExpression ge(String propertyName, Object value) {
		return new SimpleExpression( propertyName, value, ">=" );
	}

	/**
	 * Apply a "between" constraint to the named property
	 *
	 * @param propertyName The name of the property
	 * @param lo The low value
	 * @param hi The high value
	 *
	 * @return The Criterion
	 *
	 * @see BetweenExpression
	 */
	public static BetweenExpression between(String propertyName, Object lo, Object hi) {
		return new BetweenExpression( propertyName, lo, hi," between");
	}
	
	public static BetweenExpression notBetween(String propertyName, Object lo, Object hi) {
		return new BetweenExpression( propertyName, lo, hi," not between");
	}

	/**
	 * Apply an "in" constraint to the named property.
	 *
	 * @param propertyName The name of the property
	 * @param values The literal values to use in the IN restriction
	 *
	 * @return The Criterion
	 *
	 * @see InExpression
	 */
	public static Criterion in(String propertyName, Object...values) {
		return new InExpression( propertyName, values," in" );
	}

	/**
	 * Apply an "in" constraint to the named property.
	 *
	 * @param propertyName The name of the property
	 * @param values The literal values to use in the IN restriction
	 *
	 * @return The Criterion
	 *
	 * @see InExpression
	 */
	public static Criterion notIn(String propertyName, Object...values) {
		return new InExpression( propertyName, values," not in" );
	}

	/**
	 * Apply an "is null" constraint to the named property
	 *
	 * @param propertyName The name of the property
	 *
	 * @return Criterion
	 *
	 * @see NullExpression
	 */
	public static Criterion isNull(String propertyName) {
		return new NullExpression( propertyName);
	}

	/**
	 * Apply an "is not null" constraint to the named property
	 *
	 * @param propertyName The property name
	 *
	 * @return The Criterion
	 *
	 * @see NotNullExpression
	 */
	public static Criterion isNotNull(String propertyName) {
		return new NotNullExpression( propertyName );
	}

	/**
	 * Apply an "equal" constraint to two properties
	 *
	 * @param propertyName One property name
	 * @param otherPropertyName The other property name
	 *
	 * @return The Criterion
	 *
	 * @see PropertyExpression
	 */
	public static PropertyExpression eqProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression( propertyName, otherPropertyName, "=" );
	}

	/**
	 * Apply a "not equal" constraint to two properties
	 *
	 * @param propertyName One property name
	 * @param otherPropertyName The other property name
	 *
	 * @return The Criterion
	 *
	 * @see PropertyExpression
	 */
	public static PropertyExpression neProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression( propertyName, otherPropertyName, "<>" );
	}

	/**
	 * Apply a "less than" constraint to two properties
	 *
	 * @param propertyName One property name
	 * @param otherPropertyName The other property name
	 *
	 * @return The Criterion
	 *
	 * @see PropertyExpression
	 */
	public static PropertyExpression ltProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression( propertyName, otherPropertyName, "<" );
	}

	/**
	 * Apply a "less than or equal" constraint to two properties
	 *
	 * @param propertyName One property name
	 * @param otherPropertyName The other property name
	 *
	 * @return The Criterion
	 *
	 * @see PropertyExpression
	 */
	public static PropertyExpression leProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression( propertyName, otherPropertyName, "<=" );
	}

	/**
	 * Apply a "greater than" constraint to two properties
	 *
	 * @param propertyName One property name
	 * @param otherPropertyName The other property name
	 *
	 * @return The Criterion
	 *
	 * @see PropertyExpression
	 */
	public static PropertyExpression gtProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression( propertyName, otherPropertyName, ">" );
	}

	/**
	 * Apply a "greater than or equal" constraint to two properties
	 *
	 * @param propertyName One property name
	 * @param otherPropertyName The other property name
	 *
	 * @return The Criterion
	 *
	 * @see PropertyExpression
	 */
	public static PropertyExpression geProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression( propertyName, otherPropertyName, ">=" );
	}

	/**
	 * Return the conjuction of two expressions
	 *
	 * @param lhs One expression
	 * @param rhs The other expression
	 *
	 * @return The Criterion
	 * @see #and(Criterion...)
	 */
//	public static LogicalExpression and(Criterion...criterions) {
//		return new LogicalExpression(" and",criterions);
//	}

	/**
	 * Return the disjuction of two expressions
	 *
	 * @param lhs One expression
	 * @param rhs The other expression
	 *
	 * @return The Criterion
	 * @see #or(Criterion...)
	 */
//	public static LogicalExpression or(Criterion... predicates) {
//		return new LogicalExpression(" or",predicates );
//	}

	/**
	 * Apply a constraint expressed in SQL with no JDBC parameters.  Any occurrences of <tt>{alias}</tt> will be
	 * replaced by the table alias.
	 *
	 * @param sql The SQL restriction
	 *
	 * @return The Criterion
	 *
	 * @see SQLCriterion
	 */
	public static Criterion sqlRestriction(String sql) {
		return new SQLCriterion( sql );
	}


	/**
	 * Constrain a collection valued property to be empty
	 *
	 * @param propertyName The name of the collection property
	 *
	 * @return The Criterion
	 *
	 * @see EmptyExpression
	 */
	public static Criterion isEmpty(String propertyName) {
		return new EmptyExpression( propertyName );
	}

	/**
	 * Constrain a collection valued property to be non-empty
	 *
	 * @param propertyName The name of the collection property
	 *
	 * @return The Criterion
	 *
	 * @see NotEmptyExpression
	 */
	public static Criterion isNotEmpty(String propertyName) {
		return new NotEmptyExpression( propertyName );
	}

	protected Restrictions() {
		// cannot be instantiated, but needs to be protected so Expression can extend it
	}

}
